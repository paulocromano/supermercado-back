/* 
			SQL contendo o Script do Projeto do Supermercado  
            
Criado por: Paulo Romano 
Projeto para aprofundar os conhecimentos em SQL, Spring Boot e Angular
*/

CREATE DATABASE IF NOT EXISTS supermercado;
USE supermercado;

CREATE TABLE IF NOT EXISTS setor (
	id INTEGER AUTO_INCREMENT,
    nome VARCHAR(40) NOT NULL,
    
    CONSTRAINT id_setor_PK PRIMARY KEY(id),
    CONSTRAINT nome_UC unique (nome)
);

CREATE TABLE IF NOT EXISTS produto (
    id INTEGER AUTO_INCREMENT,
    nome VARCHAR(40) NOT NULL,
    marca VARCHAR(30) NOT NULL,
    data_validade DATE NOT NULL,
    preco REAL NOT NULL,
    desconto REAL DEFAULT (0),
    estoque INTEGER DEFAULT (0),
    estoque_minimo INTEGER NOT NULL,
    status_produto INTEGER NOT NULL,
    observacoes VARCHAR(100),
    id_setor INTEGER NOT NULL,
    imagem LONGBLOB,
    
    CONSTRAINT id_produto_PK PRIMARY KEY(id),
    CONSTRAINT id_setor_FK FOREIGN KEY(id_setor) REFERENCES setor(id)
);

CREATE TABLE IF NOT EXISTS cliente (
	id INTEGER AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(40) NOT NULL,
    senha VARCHAR(254) NOT NULL,
    data_nascimento DATE,
    sexo VARCHAR(1) CHECK(sexo = 'M' OR sexo = 'F'),
    telefone VARCHAR(15),
    
    CONSTRAINT id_cliente_PK PRIMARY KEY(id),
    CONSTRAINT email_UC UNIQUE(email)
);

CREATE TABLE IF NOT EXISTS perfil (
	cliente_id INTEGER NOT NULL,
    perfis INTEGER DEFAULT(1),
    
    CONSTRAINT cliente_id_perfil_FK FOREIGN KEY(cliente_id) REFERENCES cliente(id)
);

CREATE TABLE IF NOT EXISTS estado (
	id INTEGER AUTO_INCREMENT,
    nome VARCHAR(75) NOT NULL,
    uf VARCHAR(2) NOT NULL,
    
    CONSTRAINT id_estado_PK PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS cidade (
	id INTEGER AUTO_INCREMENT,
    nome VARCHAR(75) NOT NULL,
    estado_id INTEGER NOT NULL,
    
    CONSTRAINT id_cidade_PK PRIMARY KEY(id),
    CONSTRAINT estado_id_FK FOREIGN KEY(estado_id) REFERENCES estado(id)
);

CREATE TABLE IF NOT EXISTS endereco (
	id INTEGER AUTO_INCREMENT,
    logradouro VARCHAR(50) NOT NULL,
	numero VARCHAR(5) NOT NULL,
    complemento VARCHAR(40),
    bairro VARCHAR(20) NOT NULL,
    cep VARCHAR(9) NOT NULL,
    cidade_id INTEGER NOT NULL,
	cliente_id INTEGER NOT NULL,
    
    CONSTRAINT id_endereco_PK PRIMARY KEY(id),
    CONSTRAINT cidade_id_FK FOREIGN KEY(cidade_id) REFERENCES cidade(id),
	CONSTRAINT cliente_id_FK FOREIGN KEY(cliente_id) REFERENCES cliente(id)
);

CREATE TABLE IF NOT EXISTS pedido (
	id INTEGER AUTO_INCREMENT,
    data_hora_pedido VARCHAR(19),
    preco_total REAL NOT NULL,
    status_pedido INTEGER NOT NULL,
	cliente_id INTEGER NOT NULL,
    endereco_entrega_id INTEGER,
    
    CONSTRAINT id_PK PRIMARY KEY(id),
    CONSTRAINT cliente_id FOREIGN KEY(cliente_id) REFERENCES cliente(id),
    CONSTRAINT endereco_entrega_id_FK FOREIGN KEY(endereco_entrega_id) REFERENCES endereco(id) 
);

CREATE TABLE IF NOT EXISTS item_pedido (
	preco REAL NOT NULL,
    quantidade INTEGER NOT NULL,
	pedido_id INTEGER NOT NULL,
    produto_id INTEGER NOT NULL,

    CONSTRAINT id_composto_PK PRIMARY KEY (pedido_id, produto_id),
	CONSTRAINT pedido_id_FK FOREIGN KEY(pedido_id) REFERENCES pedido(id),
    CONSTRAINT produto_id_FK FOREIGN KEY(produto_id) REFERENCES produto(id)
);