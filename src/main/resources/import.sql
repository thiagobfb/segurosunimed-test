INSERT INTO customer (name, email, gender) VALUES ('Homem Aranha', 'aranha@vingadores.com', 'M');
INSERT INTO customer (name, email, gender) VALUES ('Thor', 'thor@vingadores.com', 'M');
INSERT INTO customer (name, email, gender) VALUES ('Viuva Negra', 'viuva@vingadores.com', 'F');
INSERT INTO customer (name, email, gender) VALUES ('Namor', 'namor@vingadores.com', 'M');
INSERT INTO customer (name, email, gender) VALUES ('Gamora', 'gamora@vingadores.com', 'F');

INSERT INTO address (cep, logradouro, complemento, bairro, localidade, uf, customer_id) VALUES ('22785600', 'Rua Manhuacu, 53', 'Casa 12', 'Vargem Grande', 'Rio de Janeiro', 'RJ', 1);
INSERT INTO address (cep, logradouro, complemento, bairro, localidade, uf, customer_id) VALUES ('54336433', 'Rua 3, 122', NULL, 'Bairro 1', 'Belford Roxo', 'RJ', 1);

INSERT INTO user(USERNAME, username) VALUES ('admin', 'passwd');
INSERT INTO user(username, username) VALUES ('user', 'userpwd');