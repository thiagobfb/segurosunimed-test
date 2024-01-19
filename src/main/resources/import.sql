INSERT INTO customer (id, name, email, gender) VALUES (1, 'Homem Aranha', 'aranha@vingadores.com', 'M');
INSERT INTO customer (id, name, email, gender) VALUES (2, 'Thor', 'thor@vingadores.com', 'M');
INSERT INTO customer (id, name, email, gender) VALUES (3, 'Viuva Negra', 'viuva@vingadores.com', 'F');
INSERT INTO customer (id, name, email, gender) VALUES (4, 'Namor', 'namor@vingadores.com', 'M');
INSERT INTO customer (id, name, email, gender) VALUES (5, 'Gamora', 'gamora@vingadores.com', 'F');

INSERT INTO address (id, cep, logradouro, complemento, bairro, localidade, uf, customer_id) VALUES (1, '22785600', 'Rua Manhuacu, 53', 'Casa 12', 'Vargem Grande', 'Rio de Janeiro', 'RJ', 1);
INSERT INTO address (id, cep, logradouro, complemento, bairro, localidade, uf, customer_id) VALUES (2, '54336433', 'Rua 3, 122', NULL, 'Bairro 1', 'Belford Roxo', 'RJ', 1);