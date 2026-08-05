# CRUD Relacional com Spring Boot e MySQL

Este é um projeto de API REST em Java utilizando Spring Boot, Spring Data JPA e banco de dados MySQL. O sistema gerencia o relacionamento entre **Pessoas**, **Cargos** e **Endereços**, aplicando otimizações de tipos de dados e boas práticas de segurança.

## 🚀 Tecnologias Utilizadas

- **Java 17** (ou a versão que você estiver usando)
- **Spring Boot 3.x**
- **Spring Data JPA**
- **MySQL Server**
- **Maven**

## 🗄️ Estrutura do Banco de Dados

O banco de dados foi modelado para otimizar o uso de memória e garantir a consistência dos dados (evitando dupla contagem de endereços para a mesma família):
- **Cargos (`tb_cargos`)**: Utiliza `TINYINT` para chaves primárias (mapeado como `Byte` no Java) e `DECIMAL(10,2)` para o salário (mapeado como `BigDecimal` para precisão financeira).
- **Pessoas (`tb_pessoas`)**: Utiliza `SMALLINT` para a chave primária (mapeado como `Short` no Java). Possui chaves estrangeiras para Cargo e Endereço.
- **Endereços (`tb_enderecos`)**: Utiliza `SMALLINT` para a chave primária. O relacionamento permite que **várias pessoas compartilhem o mesmo endereço**.

## 🔒 Segurança nas Rotas (`XQueryString`)

Por questões de segurança e privacidade de dados sensíveis, as operações de busca detalhada, atualização e deleção de pessoas **não expõem o CPF na URL (PathVariable) nem no corpo da requisição (RequestBody)**. 

O CPF deve ser enviado obrigatoriamente através do cabeçalho HTTP customizado chamado **`XQueryString`**.

## 🛣️ Endpoints Principais

### /pessoas

| Método | Endpoint | Cabeçalho (Header) requerido | Descrição |
| :--- | :--- | :--- | :--- |
| **GET** | `/pessoas` | Nenhum | Lista todas as pessoas cadastradas. |
| **GET** | `/pessoas/detalhe` | `XQueryString: <CPF_DA_PESSOA>` | Busca os detalhes de uma pessoa pelo CPF. |
| **POST** | `/pessoas` | Nenhum | Cadastra uma nova pessoa (envio via JSON no Body). |
| **PUT** | `/pessoas` | `XQueryString: <CPF_DA_PESSOA>` | Atualiza os dados de uma pessoa localizada pelo CPF. |
| **DELETE** | `/pessoas` | `XQueryString: <CPF_DA_PESSOA>` | Remove uma pessoa do sistema localizada pelo CPF. |

## 🛠️ Como Executar o Projeto

1. Certifique-se de ter o **Java** e o **Maven** instalados.
2. Configure a conexão com o seu banco de dados MySQL no arquivo `src/main/resources/application.properties`.
3. Execute o script SQL de criação das tabelas no seu banco de dados.
4. Rode a aplicação através da sua IDE ou pelo terminal:
   ```bash
   mvn spring-boot:run
   ```

## 📝 Exemplo de Requisição (POST /pessoas)

**URL:** `http://localhost:8080/pessoas`  
**Body (JSON):**
```json
{
  "nome": "Carlos Silva",
  "cpf": "11122233344",
  "dataNascimento": "1980-01-10",
  "cargo": { "id": 1 },
  "endereco": { "id": 1 }
}
```
