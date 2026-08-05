CREATE DATABASE db_francaorganization;

USE db_francaorganization;

-- ######################################## Tabelas ERRADAS ######################################## --
CREATE TABLE tb_cargos (
    id_cargo INT AUTO_INCREMENT PRIMARY KEY,
    nome_cargo VARCHAR(100) NOT NULL,
    salario DECIMAL(10, 2) NOT NULL
);

-- Alteração id_carg
ALTER TABLE tb_cargos MODIFY COLUMN id_cargo SMALLINT AUTO_INCREMENT;

CREATE TABLE tb_pessoas (
    id_pessoa INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    data_nascimento DATE,
    fk_cargo INT,
    FOREIGN KEY (fk_cargo) REFERENCES tb_cargos(id_cargo) ON DELETE SET NULL
);

-- Alteração id_pessoa: INT -> SMALLINT (Java: Long id -> Short id)
ALTER TABLE tb_pessoas MODIFY COLUMN id_pessoa SMALLINT AUTO_INCREMENT;

CREATE TABLE tb_enderecos (
    id_endereco INT AUTO_INCREMENT PRIMARY KEY,
    logradouro VARCHAR(150) NOT NULL,
    numero VARCHAR(20),
    bairro VARCHAR(50),
    cidade VARCHAR(50),
    estado CHAR(2),
    cep VARCHAR(8),
    fk_pessoa INT UNIQUE,
    FOREIGN KEY (fk_pessoa) REFERENCES tb_pessoas(id_pessoa) ON DELETE CASCADE
);

-- Alteração id_endereco: INT -> SMALLINT (Java: Long id -> Short id)
ALTER TABLE tb_enderecos MODIFY COLUMN id_endereco SMALLINT AUTO_INCREMENT;

-- ######################################## Alteração 01 ######################################## --
ALTER TABLE tb_enderecos DROP FOREIGN KEY tb_enderecos_ibfk_1;

-- Remove a coluna fk_pessoa
ALTER TABLE tb_enderecos DROP COLUMN fk_pessoa;

-- Nova coluna na tabela de pessoas
ALTER TABLE tb_pessoas ADD COLUMN fk_endereco INT;

-- Vínculo de chave estrangeira apontando para a tabela de endereços
ALTER TABLE tb_pessoas 
ADD CONSTRAINT fk_pessoa_endereco
FOREIGN KEY (fk_endereco) REFERENCES tb_enderecos(id_endereco) 
ON DELETE SET NULL;

-- Cadastrar o endereço da família (Supondo que vai gerar o ID 01)
INSERT INTO tb_enderecos (logradouro, numero, bairro, cidade, estado, cep) 
VALUES ('Avenida Brasil', '500', 'Centro', 'Rio de Janeiro', 'RJ', '20000000');

-- Cadastrar dados do pai vinculado ao endereço 01
INSERT INTO tb_pessoas (nome, cpf, data_nascimento, fk_cargo, fk_endereco) 
VALUES ('Carlos Silva', '11122233344', '1980-01-10', NULL, 1);

-- Cadastrar dados do filho vinculado ao MESMO endereço 1 (>>> o MySQL tem que aceitar normalmente)
INSERT INTO tb_pessoas (nome, cpf, data_nascimento, fk_cargo, fk_endereco) 
VALUES ('Lucas Silva', '55566677788', '2005-06-15', NULL, 1);


-- ######################################## Alteração 02 ######################################## --

DROP TABLE IF EXISTS tb_pessoas;     -- Depende de tb_cargos e tb_enderecos
DROP TABLE IF EXISTS tb_enderecos;   -- Não tem dependente
DROP TABLE IF EXISTS tb_cargos;      -- NNão tem dependente

-- ######################################## Novas Tabelas ######################################## --

-- Cria a tabela de Cargos (Java: Byte id)
CREATE TABLE tb_cargos (
    id_cargo TINYINT AUTO_INCREMENT PRIMARY KEY,
    nome_cargo VARCHAR(100) NOT NULL,
    salario DECIMAL(10, 2) NOT NULL
);

-- Cria a tabela de Endereços (Java: Short id)
-- Ela precisa ser criada ANTES de tb_pessoas porque as pessoas vão apontar para ela
CREATE TABLE tb_enderecos (
    id_endereco SMALLINT AUTO_INCREMENT PRIMARY KEY,
    logradouro VARCHAR(150) NOT NULL,
    numero VARCHAR(20),
    bairro VARCHAR(50),
    cidade VARCHAR(50),
    estado CHAR(2),
    cep VARCHAR(8)
);

-- Cria a tabela de Pessoas (Java: Short id)
CREATE TABLE tb_pessoas (
    id_pessoa SMALLINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    data_nascimento DATE,
    fk_cargo TINYINT,
    fk_endereco SMALLINT, -- Mudou para SMALLINT para dar o match com tb_enderecos
    FOREIGN KEY (fk_cargo) REFERENCES tb_cargos(id_cargo) ON DELETE SET NULL,
    CONSTRAINT fk_pessoa_endereco FOREIGN KEY (fk_endereco) REFERENCES tb_enderecos(id_endereco) ON DELETE SET NULL
);

-- Cadastrar o endereço da família (Supondo que vai gerar o ID 01)
INSERT INTO tb_enderecos (logradouro, numero, bairro, cidade, estado, cep) 
VALUES ('Avenida Brasil', '500', 'Centro', 'Rio de Janeiro', 'RJ', '20000000');

-- Cadastrar dados do pai vinculado ao endereço 01
INSERT INTO tb_pessoas (nome, cpf, data_nascimento, fk_cargo, fk_endereco) 
VALUES ('Carlos Silva', '11122233344', '1980-01-10', NULL, 1);

-- Cadastrar dados do filho vinculado ao MESMO endereço 1 (>>> o MySQL tem que aceitar normalmente)
INSERT INTO tb_pessoas (nome, cpf, data_nascimento, fk_cargo, fk_endereco) 
VALUES ('Lucas Silva', '55566677788', '2005-06-15', NULL, 1);

-- ######################################## Consutas ######################################## --
-- Familiares dividindo o mesmo endereço (INNER JOIN)
SELECT 
    p.nome AS 'Nome da Pessoa',
    p.cpf AS 'CPF',
    e.logradouro AS 'Rua/Avenida',
    e.numero AS 'Número',
    e.bairro AS 'Bairro',
    e.cidade AS 'Cidade'
FROM tb_pessoas p
INNER JOIN tb_enderecos e ON p.fk_endereco = e.id_endereco;

-- Relatório Completo: Pessoa + Endereço + Cargo (LEFT JOIN)
SELECT 
    p.id_pessoa AS 'ID',
    p.nome AS 'Funcionário',
    c.nome_cargo AS 'Cargo/Função',
    c.salario AS 'Salário',
    CONCAT(e.logradouro, ', ', e.numero, ' - ', e.bairro) AS 'Endereço Completo'
FROM tb_pessoas p
LEFT JOIN tb_cargos c ON p.fk_cargo = c.id_cargo
LEFT JOIN tb_enderecos e ON p.fk_endereco = e.id_endereco;

-- Agrupar e contar quantas pessoas moram em cada endereço (GROUP BY)
SELECT 
    e.logradouro AS 'Endereço',
    e.numero AS 'Número',
    COUNT(p.id_pessoa) AS 'Quantidade de Moradores'
FROM tb_enderecos e
INNER JOIN tb_pessoas p ON e.id_endereco = p.fk_endereco
GROUP BY e.id_endereco, e.logradouro, e.numero;





