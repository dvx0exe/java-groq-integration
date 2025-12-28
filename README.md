# Java Groq Integration & Library Manager

Este projeto é uma evolução técnica do repositório original para integração com a API da Groq. O sistema agora não apenas consulta a IA, mas também processa as respostas como objetos Java estruturados e permite a persistência em um banco de dados MySQL.

## 🚀 Funcionalidades

* **Conexão Nativa HTTP**: Utiliza `java.net.http.HttpClient` (Java 11+), sem dependências pesadas de clientes HTTP externos.
* **Structured Outputs**: Configura a API para retornar apenas JSON válido através do parâmetro `response_format: { "type": "json_object" }`.
* **Mapeamento de Objetos (ORM para IA)**: Converte a resposta da IA diretamente para as classes internas `Bibliografia` e `Livro` utilizando Jackson.
* **Engenharia de Prompt**: Prompt otimizado para extrair dados bibliográficos rigorosos, incluindo título, ano, editora, gênero e sinopse.

## 🆕 Novas Funcionalidades (Evolução)

* **Persistência em MySQL**: Implementação de uma camada de dados dedicada para salvar as consultas realizadas.
* **Processamento em Lote (JDBC Batch)**: Otimização de performance ao inserir múltiplos registros de livros simultaneamente no banco de dados.
* **Segurança via Variáveis de Ambiente**: Proteção de credenciais sensíveis (Chave de API e Senha do Banco) utilizando `System.getenv`.
* **Interface interativa no Console**: Sistema de decisão (Y/N) que permite ao usuário validar os dados da IA antes de persistir no banco.

## 🛠️ Tecnologias Utilizadas

* **Java 11+**
* **MySQL Server**
* **Jackson Databind**: Para processamento de JSON.
* **Groq API**: Modelo `llama-3.1-8b-instant`.

## 📋 Pré-requisitos e Setup SQL

Antes de rodar a aplicação, crie a estrutura do banco de dados:

```sql
CREATE DATABASE biblioteca_db;
USE biblioteca_db;

CREATE TABLE livros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_do_livro VARCHAR(255),
    ano_publicacao VARCHAR(10),
    editora_classica VARCHAR(255),
    numero_paginas_estimado INT,
    genero_literario VARCHAR(100),
    resumo_sinopse TEXT
);

```

## ⚙️ Configuração do Ambiente

O projeto exige que as seguintes variáveis de ambiente estejam configuradas no seu sistema:

1. `GROQ_API_KEY`: Sua chave de acesso à API da Groq.
2. `DB_PASSWORD`: A senha do seu usuário root do MySQL.