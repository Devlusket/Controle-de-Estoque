CREATE TABLE cidades (
    id    BIGSERIAL PRIMARY KEY,
    nome  VARCHAR(100) NOT NULL,
    estado CHAR(2)     NOT NULL,
    sede  BOOLEAN      NOT NULL DEFAULT FALSE
);

CREATE TABLE produtos (
    id             BIGSERIAL PRIMARY KEY,
    nome           VARCHAR(100) NOT NULL UNIQUE,
    unidade_medida VARCHAR(20)  NOT NULL,
    ativo          BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE TABLE usuarios (
    id        BIGSERIAL PRIMARY KEY,
    nome      VARCHAR(100) NOT NULL,
    email     VARCHAR(100) NOT NULL UNIQUE,
    senha     VARCHAR(255) NOT NULL,
    role      VARCHAR      NOT NULL DEFAULT 'CLIENTE' CHECK (role IN ('ADMIN', 'CLIENTE')),
    cidade_id BIGINT,
    FOREIGN KEY (cidade_id) REFERENCES cidades(id)
);

CREATE TABLE movimentacoes (
    id               BIGSERIAL PRIMARY KEY,
    tipo_movimentacao VARCHAR NOT NULL CHECK (tipo_movimentacao IN ('ENTRADA', 'SAIDA', 'TRANSFERENCIA')),
    produto_id        BIGINT  NOT NULL,
    usuario_id        BIGINT  NOT NULL,
    cidade_origem_id  BIGINT,
    cidade_destino_id BIGINT,
    quantidade        NUMERIC NOT NULL,
    data_movimentacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    observacao        TEXT,
    FOREIGN KEY (produto_id)        REFERENCES produtos(id),
    FOREIGN KEY (usuario_id)        REFERENCES usuarios(id),
    FOREIGN KEY (cidade_origem_id)  REFERENCES cidades(id),
    FOREIGN KEY (cidade_destino_id) REFERENCES cidades(id)
);