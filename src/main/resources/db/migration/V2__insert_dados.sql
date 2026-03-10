-- Active: 1771687117536@@127.0.0.1@5432@estoque_db
INSERT INTO cidades (nome, estado, sede) VALUES
('Belo Horizonte', 'MG', TRUE);

INSERT INTO cidades (nome, estado) VALUES
('São Paulo', 'SP'),
('Rio de Janeiro', 'RJ'),
('Curitiba', 'PR'),
('Porto Alegre', 'RS');


-- senha hash:admin123
INSERT INTO usuarios (nome, email, senha, role, cidade_id) VALUES
('Admin User MG', 'admin@example.com', '$2a$12$xK9Jk8mN2pL5qR7tV3wYuOeHgFdCbAiZnMoWsExQvUyTlPkIjHrG', 'ADMIN', 1),
('Cliente User SP', 'cliente@example.com', '$2a$12$xK9Jk8mN2pL5qR7tV3wYuOeHgFdCbAiZnMoWsExQvUyTlPkIjHrG', 'CLIENTE', 2), 
('Cliente User RJ', 'cliente2@example.com', '$2a$12$xK9Jk8mN2pL5qR7tV3wYuOeHgFdCbAiZnMoWsExQvUyTlPkIjHrG', 'CLIENTE', 3),  
('Cliente User PR', 'cliente3@example.com', '$2a$12$xK9Jk8mN2pL5qR7tV3wYuOeHgFdCbAiZnMoWsExQvUyTlPkIjHrG', 'CLIENTE', 4),  
('Cliente User RS', 'cliente4@example.com', '$2a$12$xK9Jk8mN2pL5qR7tV3wYuOeHgFdCbAiZnMoWsExQvUyTlPkIjHrG', 'CLIENTE', 5);  

INSERT INTO produtos (nome, unidade_medida) VALUES
('Arroz', 'kg'),
('Feijão', 'kg'),
('Açúcar', 'kg'),
('Óleo de Soja', 'litro'),
('Farinha de Trigo', 'kg');

INSERT INTO movimentacoes (tipo_movimentacao, produto_id, usuario_id, cidade_origem_id, cidade_destino_id, quantidade, observacao) VALUES
('ENTRADA', 1, 1, NULL, 1, 100, 'Recebimento inicial de arroz na sede'),
('SAIDA', 1, 2, 2, NULL, 10, 'Saída de arroz em SP'),
('TRANSFERENCIA', 2, 1, 1, 2, 50, 'Transferência de feijão da sede para SP');
