# Raízes do Nordeste — API

API REST para gerenciamento operacional da rede de lojas **"Raízes do Nordeste"**. Controla unidades, produtos, cardápios, estoque, funcionários, clientes, pedidos, pagamentos, promoções e auditoria.

---

## Índice

1. [Executar o Projeto](#1-executar-o-projeto)
2. [Testes do Fluxo Principal — Realizar Pedido](#2-testes-do-fluxo-principal--realizar-pedido)
3. [Plano de Testes](#3-plano-de-testes)

---

## 1. Executar o Projeto

### 1.1 Requisitos

Para executar o projeto é necessário ter as seguintes ferramentas instaladas:

- Java JDK 21 ou superior
- PostgreSQL 14 ou superior

> O projeto usa o **Gradle Wrapper** (`gradlew`). Não é necessário instalar o Gradle separadamente.

> É necessário executar um banco de dados PostgreSQL localmente e criar um banco vazio para a aplicação. As credenciais de acesso devem ser configuradas no arquivo `.env` (veja seções [1.2](#12-variáveis-de-ambiente) e [1.4](#14-banco-de-dados)).
---

### 1.2 Variáveis de Ambiente

O projeto usa a biblioteca [spring-dotenv](https://github.com/paulschwarz/spring-dotenv) para ler um arquivo `.env` na raiz do projeto.

#### 1.2.1 Criar o arquivo `.env`

Crie um arquivo chamado `.env` na raiz do projeto copiando o exemplo abaixo:

```bash
cp .env.example .env
```

#### 1.2.2 Conteúdo do `.env.example`

```dotenv
# ─── Banco de Dados ───────────────────────────────────────────────────────────
# URL de conexão JDBC com o PostgreSQL
# Exemplo: jdbc:postgresql://localhost:5432/raizesdonordeste
PROJECT_DB_URL=jdbc:postgresql://localhost:5432/raizesdonordeste

# Usuário do banco de dados
PROJECT_DB_USERNAME=postgres

# Senha do banco de dados
PROJECT_DB_PASSWORD=sua_senha_aqui

# ─── Autenticação JWT ─────────────────────────────────────────────────────────
# Chave secreta usada para assinar os tokens JWT (mínimo 32 caracteres)
# Gere uma chave em: https://packtypebot.com.br/gerador/jwt.php
PROJECT_SECRET_JWT=substitua_por_uma_chave_secreta_segura_com_32_ou_mais_chars

# Tempo de expiração do token JWT em milissegundos
# 86400000 = 24 horas | 3600000 = 1 hora
PROJECT_SECRET_JWT_EXPIRATION=86400000

# ─── Provedor de Pagamento ────────────────────────────────────────────────────
# URL base do gateway de pagamento externo
PAYMENT_PROVIDER_URL=https://api.mockfly.dev/mocks/6114e842-d421-472c-a397-a235bf075761
```

#### 1.2.3 Preencher os valores

- `PROJECT_DB_URL` | URL JDBC de conexão com o PostgreSQL
- `PROJECT_DB_USERNAME` | Usuário do banco de dados
- `PROJECT_DB_PASSWORD` | Senha do banco de dados
- `PROJECT_SECRET_JWT` | Chave secreta para assinatura dos tokens JWT (mínimo 32 caracteres). Gere uma em https://packtypebot.com.br/gerador/jwt.php
- `PROJECT_SECRET_JWT_EXPIRATION` | Validade do token em milissegundos (ex.: `86400000` para 24 h)
- `PAYMENT_PROVIDER_URL` | URL base do gateway de pagamento externo. Utilize a seguinte URL: https://api.mockfly.dev/mocks/6114e842-d421-472c-a397-a235bf075761 - este mock simula respostas de sucesso e falha para processamentos de pagamento.
---

### 1.3 Instalação de Dependências

Todas as dependências são gerenciadas pelo Gradle e baixadas automaticamente na primeira compilação. Execute:

```bash
# Linux / macOS
./gradlew dependencies

# Windows
gradlew.bat dependencies
```

> Este passo é opcional. As dependências também são resolvidas automaticamente ao compilar ou iniciar a aplicação.

---

### 1.4 Banco de Dados

#### 1.4.1 Criar o banco de dados

Conecte-se ao PostgreSQL e crie o banco antes de iniciar a aplicação:

```sql
CREATE DATABASE raizesdonordeste;
```

> O nome do banco deve corresponder ao que foi configurado em `PROJECT_DB_URL`.

#### 1.4.2 Criação do schema (migrations)

O projeto utiliza `spring.jpa.hibernate.ddl-auto: update`. Isso significa que o **Hibernate cria e atualiza as tabelas automaticamente** ao iniciar a aplicação — não há scripts de migration separados para executar.

> Apenas certifique-se de que o **banco de dados existe** e que as credenciais em `.env` estão corretas antes de iniciar.

---

### 1.5 Iniciar a API

```bash
# Linux / macOS
./gradlew bootRun

# Windows
gradlew.bat bootRun
```

A aplicação sobe na porta **8001**.

```
http://localhost:8001
```

Para verificar se está no ar, acesse:

```
http://localhost:8001/swagger-ui/index.html
```

---

### 1.6 Documentação da API (Swagger)

Após iniciar a aplicação, a documentação interativa está disponível em:

| Interface | URL |
|---|---|
| Swagger UI | http://localhost:8001/swagger-ui/index.html |
| OpenAPI JSON | http://localhost:8001/v3/api-docs |

A documentação cobre todos os **43 endpoints** distribuídos em **10 recursos** (`/api/v1/products`, `/api/v1/customers`, `/api/v1/orders`, etc.).

Para testar endpoints protegidos diretamente pelo Swagger:
1. Use `POST /api/v1/auth/register` para criar um usuário.
2. Use `POST /api/v1/auth/login` para obter o token JWT.
3. Clique em **Authorize** no topo da página e insira o token no formato `Bearer <token>`.
4. Agora você pode testar os endpoints protegidos diretamente pelo Swagger.


---

## 2. Testes do Fluxo Principal — Realizar Pedido (Executar via Postman)

Este fluxo valida o ciclo completo de um pedido: configuração administrativa da loja, cadastro do cliente e realização do pedido. A collection Postman de referência está em [`docs/realizar pedido.postman_collection.json`](docs/realizar%20pedido.postman_collection.json).

> Todos os endpoints protegidos requerem o header `Authorization: Bearer <token>`. Após o login, o token é salvo automaticamente na variável `jwt_token` da collection.

---

### 2.1 Fluxo Administrativo

**1. Criar usuário administrador**

```http
POST /api/v1/auth/register
Content-Type: application/json

{
  "name": "joao",
  "email": "joao@teste.com",
  "password": "12345678",
  "role": "ADMIN"
}
```

---

**2. Fazer login com o usuário admin**

```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "email": "joao@teste.com",
  "password": "12345678"
}
```

> Resposta: `{ "token": "<jwt>" }`. Guarde o token para os próximos passos.

---

**3. Criar unidade**

```http
POST /api/v1/units
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Unidade Floripa",
  "address": "Rua Sebastião do Alto, n 204, Cloropiano, Rio Grande do Sul, Brasil"
}
```

---

**4. Criar produto (um)**

```http
POST /api/v1/products
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Coca Cola KS",
  "price": "4.99"
}
```

---

**5. Criar produto (dois)**

```http
POST /api/v1/products
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Baião de Dois",
  "price": "19.90"
}
```

---

**6. Cadastrar produtos no estoque**

Repita para cada produto criado, ajustando `productId`.

```http
POST /api/v1/inventory-items
Authorization: Bearer <token>
Content-Type: application/json

{
  "unitId": 1,
  "productId": 1,
  "quantity": 10,
  "minimumQuantity": 1
}
```

---

**7. Cadastrar cardápio da unidade**

```http
POST /api/v1/menus
Authorization: Bearer <token>
Content-Type: application/json

{
  "unitId": 1
}
```

---

**8. Adicionar produto ao cardápio**

Repita para cada produto que deve constar no cardápio, ajustando `productId`. O `{menuId}` é o ID retornado no passo anterior.

```http
POST /api/v1/menus/{menuId}/products
Authorization: Bearer <token>
Content-Type: application/json

{
  "productId": 1
}
```

---

### 2.2 Fluxo do Cliente

**1. Cadastrar cliente**

> Endpoint público — não requer autenticação.

```http
POST /api/v1/customers
Content-Type: application/json

{
  "name": "João Lucas",
  "document": "77469735070",
  "email": "joao@gmail.com",
  "password": "joao1234",
  "phone": "22997719088",
  "birthDate": "1998-12-06",
  "lgpdAccepted": true,
  "marketingAccepted": true
}
```

---

**2. Realizar pedido**

```http
POST /api/v1/orders
Authorization: Bearer <token>
Content-Type: application/json

{
  "customerId": 1,
  "unitId": 1,
  "deliveryType": "COUNTER",
  "orderChannel": "COUNTER",
  "paymentType": "PIX",
  "items": [
    {
      "productId": 1,
      "quantity": 3
    }
  ]
}
```

> O sistema cria o pedido, dá baixa no estoque de cada item e processa o pagamento junto ao gateway externo.

> **⚠️ Simulação de erro no pagamento:** o mock do provedor de pagamentos retorna erro quando o valor total do pedido for exatamente **R$ 10,00**. Use esse valor para testar o comportamento do sistema em caso de falha no pagamento.

---

### 2.3 Evidências — Verificar Resultado

**1. Conferir estoque** — validar que a quantidade foi reduzida após o pedido

```http
GET /api/v1/inventory-items
Authorization: Bearer <token>
```

**2. Conferir pedidos da unidade**

```http
GET /api/v1/orders/unit/1
Authorization: Bearer <token>
```

**3. Conferir pagamentos**

```http
GET /api/v1/payments
Authorization: Bearer <token>
```

---

### 2.4 Teste de Erro — Estoque Insuficiente

Para validar o comportamento da API quando a quantidade solicitada no pedido supera o estoque disponível, faça um pedido com quantidade maior do que a cadastrada para o produto.

**Exemplo:** produto *Coca Cola KS* com **5 unidades** em estoque, pedindo **7 unidades**.

```http
POST /api/v1/orders
Authorization: Bearer <token>
Content-Type: application/json

{
  "customerId": 1,
  "unitId": 1,
  "deliveryType": "COUNTER",
  "orderChannel": "COUNTER",
  "paymentType": "PIX",
  "items": [
    {
      "productId": 1,
      "quantity": 7
    }
  ]
}
```

**Resposta esperada — `400 Bad Request`:**

```json
{
  "status": 400,
  "message": "Insufficient stock for product 'Coca Cola KS'. Available: 5, requested: 7",
  "timestamp": "2026-06-21T15:59:24.123008"
}
```

---

## 3. Plano de Testes

> Em desenvolvimento.
