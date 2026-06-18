<p align="center">
  <a href="./docs/README.en.md">
    <img src="https://img.shields.io/badge/Language-English-blue?style=for-the-badge">
  </a>
</p>

# CoreBank

Sistema bancário desenvolvido em Java com arquitetura em camadas, utilizando MySQL para persistência de dados.

O projeto simula funcionalidades de um banco digital, permitindo criação de contas, login, movimentações financeiras, gerenciamento de chaves Pix e registro de transações.

# Tecnologias Utilizadas

* Java
* MySQL
* JDBC
* Maven
* Programação Orientada a Objetos (POO)

# Estrutura do Projeto

```bash
src/
 ├── application/
 │    ├── Main.java
 │    └── controller/
 │
 ├── database/
 │    └── Conexao.java
 │
 ├── model/
 │    ├── Conta.java
 │    ├── ContaCorrente.java
 │    ├── ContaPoupanca.java
 │    ├── Pessoa.java
 │    ├── Pix.java
 │    ├── Transacao.java
 │    └── enums/
 │
 ├── repository/
 │
 └── service/
```

# Funcionalidades

## Conta Bancária

* Criação de conta corrente
* Criação de conta poupança
* Login com senha
* Consulta de saldo
* Depósito
* Saque
* Transferência
* Utilização de limite na conta corrente
* Histórico de transações

## Pix

* Cadastro de chave Pix
* Busca de chave Pix
* Transferência via Pix

## Segurança

* Validação de CPF/CNPJ
* Senha para autenticação do usuário

# Banco de Dados

## Criação do Banco

```sql
CREATE DATABASE db_CoreBank;
USE db_CoreBank;

CREATE TABLE pessoa(
id_pessoa INT PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR(45) NOT NULL,
documento VARCHAR(15) NOT NULL,
tipo_pessoa VARCHAR(15) NOT NULL,
senha VARCHAR(50) NOT NULL
);

CREATE TABLE conta(
id_conta INT PRIMARY KEY AUTO_INCREMENT,
saldo DECIMAL(15, 2) NOT NULL DEFAULT 0.00,
limite DECIMAL(15, 2) NOT NULL DEFAULT 0.00,
tipo_conta VARCHAR(15) NOT NULL,
id_pessoa_fk INT,

FOREIGN KEY(id_pessoa_fk) REFERENCES pessoa(id_pessoa) ON DELETE CASCADE
);

CREATE TABLE pix(
id_pix INT PRIMARY KEY AUTO_INCREMENT,
chave VARCHAR(45) NOT NULL,
tipo_pix VARCHAR(10) NOT NULL,
id_conta_fk INT,

FOREIGN KEY(id_conta_fk) REFERENCES conta(id_conta) ON DELETE CASCADE
);

CREATE TABLE transacao(
id_transacao INT PRIMARY KEY AUTO_INCREMENT,
data_hora DATETIME(0) NOT NULL,
valor DECIMAL(15, 2) NOT NULL,
saldo_conta DECIMAL(15, 2) NOT NULL,
tipo_transacao VARCHAR(45) NOT NULL,
id_conta_fk INT,

FOREIGN KEY(id_conta_fk) REFERENCES conta(id_conta)
);

CREATE TABLE investimento(
id_investimento INT PRIMARY KEY AUTO_INCREMENT,
valor DECIMAL(15, 2) NOT NULL,
data_hora DATETIME(0) NOT NULL,
id_conta_fk INT,

FOREIGN KEY(id_conta_fk) REFERENCES conta(id_conta)
);
```

# Configuração do Projeto

## 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/CoreBank.git
```

## 2. Configure o banco de dados

Execute o script SQL acima no MySQL.

## 3. Configure a conexão com o banco

Arquivo:

```bash
src/database/Conexao.java
```

Exemplo:

```java
String url = "jdbc:mysql://localhost:3306/db_CoreBank";
String user = "root";
String password = "sua_senha";
```

## 4. Execute o projeto

Pelo IntelliJ IDEA ou outro IDE Java.

Classe principal:

```bash
application/Main.java
```

# Conceitos Aplicados

* Programação Orientada a Objetos
* Herança
* Polimorfismo
* Encapsulamento
* Abstração
* Arquitetura em camadas
* DAO/Repository Pattern
* Tratamento de exceções
* Enumerações
* Persistência de dados com JDBC

# Tipos de Conta

## Conta Corrente

* Possui limite
* Permite utilização de saldo negativo
* Pode aplicar taxas sobre utilização do limite

## Conta Poupança

* Não utiliza limite
* Operações limitadas ao saldo disponível

# Melhorias Futuras

* Interface gráfica
* API REST com Spring Boot
* Criptografia de senha
* Testes automatizados
* Autenticação JWT
* Docker
* Deploy em nuvem

# Autor

Desenvolvido por Lucas Carvalho.
