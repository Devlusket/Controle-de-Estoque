# 📦 Estoque B2B — API de Controle de Estoque Regional

API REST desenvolvida com Spring Boot para controle de estoque de uma empresa regional com múltiplas filiais. O sistema permite que cada cidade gerencie suas próprias movimentações de estoque, enquanto a sede (admin) tem visibilidade completa de tudo.

🔗 **API em produção:** https://controle-de-estoque-production-4472.up.railway.app/swagger-ui/index.html

---

## 🚀 Tecnologias

- **Java 21**
- **Spring Boot 3.2.5**
- **Spring Security + JWT (jjwt 0.12.6)**
- **Spring Data JPA + Hibernate**
- **PostgreSQL**
- **Flyway** — migrations de banco de dados
- **MapStruct 1.5.5** — mapeamento Entity ↔ DTO
- **Lombok**
- **SpringDoc OpenAPI (Swagger)**
- **Railway** — deploy em produção

---

## 🏗️ Arquitetura

O projeto segue arquitetura em camadas:

```
controller  →  service  →  repository  →  banco de dados
                ↕
             mapper (MapStruct)
                ↕
             dto (request / response)
```

### Estrutura de pacotes

```
src/main/java/com/controle/estoque/
├── config/           # SwaggerConfig
├── constants/        # MensagensErro
├── controllers/      # AuthController, CidadeController, ProdutoController,
│                     # UsuarioController, MovimentacaoController, GlobalExceptionHandler
├── dtos/
│   ├── request/      # LoginRequest, CidadeRequest, ProdutoRequest,
│   │                 # UsuarioRequest, MovimentacaoRequest
│   └── response/     # LoginResponse, CidadeResponse, ProdutoResponse,
│                     # UsuarioResponse, MovimentacaoResponse, ErrorResponse
├── entities/         # Cidade, Produto, Usuario, Movimentacao
├── enums/            # Role, TipoMovimentacao
├── exceptions/       # ResourceNotFoundException, BusinessException
├── mappers/          # CidadeMapper, ProdutoMapper, UsuarioMapper, MovimentacaoMapper
├── repositories/     # CidadeRepository, ProdutoRepository,
│                     # UsuarioRepository, MovimentacaoRepository
├── security/         # JwtUtil, JwtAuthFilter, UserDetailsServiceImpl, SecurityConfig
└── services/         # AuthService, CidadeService, ProdutoService,
                      # UsuarioService, MovimentacaoService
```

---

## 🗄️ Banco de Dados

### Tabelas

```
cidades         — id, nome, estado, sede
produtos        — id, nome, unidade_medida, ativo
usuarios        — id, nome, email, senha, role, ativo, cidade_id
movimentacoes   — id, tipo_movimentacao, produto_id, usuario_id,
                  cidade_origem_id, cidade_destino_id, quantidade,
                  data_movimentacao, observacao
```

### Migrations Flyway

```
V1__create_tables.sql           — criação das 4 tabelas
V2__insert_dados.sql            — sede, cidades e usuários mock
V3__alter_estado_varchar.sql    — ajuste de tipo da coluna estado
V4__add_ativo_usuarios.sql      — adição da coluna ativo em usuarios
V5__alter_usuarios_senha.sql    — atualização dos hashes BCrypt
```

---

## 🔐 Autenticação e Autorização

O sistema usa JWT stateless. O token é gerado no login e deve ser enviado em toda requisição no header:

```
Authorization: Bearer <token>
```

### Perfis de acesso

| Recurso | ADMIN | CLIENTE |
|---|---|---|
| Cidades — listar/buscar | ✅ | ✅ |
| Cidades — criar/editar/deletar | ✅ | ❌ |
| Produtos — listar/buscar | ✅ | ✅ |
| Produtos — criar/editar/desativar | ✅ | ❌ |
| Usuários — todos os endpoints | ✅ | ❌ |
| Movimentações — registrar | ✅ | ✅ |
| Movimentações — listar | ✅ (todas) | ✅ (só da sua cidade) |

---

## 📋 Regras de Negócio

### Movimentações

- **ENTRADA** — produto chegou de fora do sistema (fornecedor, fábrica). `cidadeOrigem = null`, `cidadeDestino = cidade que recebeu`.
- **SAIDA** — produto saiu do sistema (venda, consumo). `cidadeOrigem = cidade que tinha`, `cidadeDestino = null`.
- **TRANSFERENCIA** — produto se moveu entre cidades do sistema. `cidadeOrigem` e `cidadeDestino` obrigatórias.
  - CLIENTE só pode transferir para a **sede**
  - ADMIN pode transferir para qualquer cidade

### Outras regras

- `cidade_origem` é extraída automaticamente do token JWT — nunca vem no request
- `data_movimentacao` é preenchida automaticamente pelo servidor no fuso `America/Sao_Paulo`
- Produtos nunca são deletados — apenas desativados (`ativo = false`) para preservar histórico
- Usuários nunca são deletados — apenas desativados (`ativo = false`)
- Não há auto-cadastro — usuários são criados apenas pelo ADMIN

---

## ⚙️ Como executar localmente

### Pré-requisitos

- Java 21
- Maven
- PostgreSQL instalado localmente

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/estoque.git
cd estoque
```

### 2. Crie o banco de dados

```bash
psql -h localhost -U postgres
```

```sql
CREATE DATABASE estoque_db;
\q
```

### 3. Configure o `application.properties`

Renomeie o arquivo de exemplo e preencha com suas credenciais locais:

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

```properties
spring.application.name=estoque
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/estoque_db}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:sua_senha}
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
spring.jackson.time-zone=America/Sao_Paulo
app.jwt.secret=${JWT_SECRET:chave-secreta-local-minimo-256-bits}
app.jwt.expiration-ms=86400000
server.forward-headers-strategy=framework
```

### 4. Execute a aplicação

```bash
./mvnw spring-boot:run
```

O Flyway vai criar as tabelas e inserir os dados mock automaticamente.

### 5. Acesse o Swagger

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🧪 Testando

### Usuários mock disponíveis

| Email | Senha | Role |
|---|---|---|
| admin@example.com | admin | ADMIN |
| cliente@example.com | admin | CLIENTE |
| cliente2@example.com | admin | CLIENTE |
| cliente3@example.com | admin | CLIENTE |
| cliente4@example.com | admin | CLIENTE |

### Fluxo básico no Swagger

1. `POST /auth/login` com email e senha
2. Copie o token retornado
3. Clique em **Authorize** (cadeado) e cole o token
4. Teste os endpoints

---

## 🌐 Deploy

A aplicação está hospedada no **Railway** com PostgreSQL gerenciado.

### Variáveis de ambiente necessárias em produção

```
DB_URL=jdbc:postgresql://host:porta/banco
DB_USERNAME=usuario
DB_PASSWORD=senha
JWT_SECRET=chave-secreta-longa-e-segura
```

---

## 📌 Decisões técnicas

- **MapStruct** ao invés de mappers manuais — elimina boilerplate e é o padrão de mercado
- **Flyway** para migrations — garante rastreabilidade e reprodutibilidade do banco
- **JWT stateless** — sem sessão no servidor, escalável horizontalmente
- **Records Java** para DTOs — mais enxuto que classes com Lombok para objetos imutáveis
- **PUT ao invés de PATCH** — escolha de simplicidade para o escopo atual. Em produção real, PATCH seria mais adequado para atualizações parciais
- **Produtos soft delete** — desativação ao invés de deleção para preservar histórico de movimentações
- **`ddl-auto: validate`** — o Hibernate apenas valida o schema, nunca o altera. Toda mudança passa pelo Flyway

---

## 📄 Endpoints

### Autenticação
| Método | Rota | Descrição | Auth |
|---|---|---|---|
| POST | /auth/login | Login e geração de token JWT | Público |

### Cidades
| Método | Rota | Descrição | Role |
|---|---|---|---|
| GET | /cidades | Listar todas | Autenticado |
| GET | /cidades/{id} | Buscar por ID | Autenticado |
| POST | /cidades | Criar cidade | ADMIN |
| PUT | /cidades/{id} | Atualizar cidade | ADMIN |
| DELETE | /cidades/{id} | Deletar cidade | ADMIN |

### Produtos
| Método | Rota | Descrição | Role |
|---|---|---|---|
| GET | /produtos | Listar ativos | Autenticado |
| GET | /produtos/{id} | Buscar por ID | Autenticado |
| POST | /produtos | Criar produto | ADMIN |
| PUT | /produtos/{id} | Atualizar produto | ADMIN |
| DELETE | /produtos/{id} | Desativar produto | ADMIN |

### Usuários
| Método | Rota | Descrição | Role |
|---|---|---|---|
| GET | /usuarios | Listar todos | ADMIN |
| GET | /usuarios/{id} | Buscar por ID | ADMIN |
| POST | /usuarios | Criar usuário | ADMIN |
| PUT | /usuarios/{id} | Atualizar usuário | ADMIN |
| DELETE | /usuarios/{id} | Desativar usuário | ADMIN |

### Movimentações
| Método | Rota | Descrição | Role |
|---|---|---|---|
| GET | /movimentacoes | Listar todas | Autenticado |
| GET | /movimentacoes/{id} | Buscar por ID | Autenticado |
| GET | /movimentacoes/produto/{id} | Filtrar por produto | Autenticado |
| GET | /movimentacoes/cidade/{id} | Filtrar por cidade | Autenticado |
| POST | /movimentacoes | Registrar movimentação | Autenticado |
