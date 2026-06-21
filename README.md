# Raízes do Nordeste — API

API REST para gerenciamento operacional da rede de lojas **"Raízes do Nordeste"**. Controla unidades, produtos, cardápios, estoque, funcionários, clientes, pedidos, pagamentos, promoções e auditoria.

---

## Índice

1. [Executar o Projeto](#1-executar-o-projeto)
2. [Testes do Fluxo Principal — Realizar Pedido](#2-testes-do-fluxo-principal--realizar-pedido)
3. [Plano de Testes](#3-plano-de-testes)
   - [3.1 Pré-configuração](#31-pré-configuração-da-coleção-plano-de-testes)
   - [3.2 Variáveis da Coleção](#32-variáveis-da-coleção-plano-de-testes)
   - [3.3 Visão Geral dos Cenários](#33-visão-geral-dos-cenários)
   - [3.4 Rastreio de Estoque](#34-rastreio-de-estoque-produto-1--coca-cola-ks)
   - [3.5 Cenários de Teste](#35-cenários-de-teste)

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

> **⚠️ IMPORTANTE — ordem de execução obrigatória**
> As coleções Postman devem ser executadas **nesta ordem**:
> 1. **`realizar pedido`** ([`docs/realizar pedido.postman_collection.json`](docs/realizar%20pedido.postman_collection.json)) — fluxo principal que cria os dados de base: usuário ADMIN, unidade, produto, estoque, cardápio e cliente. A coleção de plano de testes pressupõe que todos esses registros já existam no banco.
> 2. **`plano de testes`** ([`docs/plano de testes.postman_collection.json`](docs/plano%20de%20testes.postman_collection.json)) — executa os casos de teste sobre os dados criados pelo fluxo principal.
>
> Executar `plano de testes` sem antes executar `realizar pedido` causará falhas por ausência de dados dependentes (usuário ADMIN, cliente, produto 1, unidade 1, etc.).

---

### 3.1 Pré-configuração da coleção `plano de testes`

Antes de executar qualquer caso de teste, execute os requests da pasta **`pré-configuração`** na ordem abaixo. Eles preparam o estado exato do banco necessário para que todos os cenários funcionem corretamente.

| # | Request | O que faz |
|---|---|---|
| 1 | `login` | `POST /auth/login` com `joao@teste.com` (ADMIN). Salva `jwt_token` nas variáveis da coleção. |
| 2 | `login user` | `POST /auth/login` com `joao@gmail.com` (cliente/USER). Salva `jwt_token_user`. Necessário para T03. |
| 3 | `Setup: repor estoque produto 1 (+5 unidades)` | `PATCH /inventory-items/1/add-stock` com `amount: 5` → estoque do produto 1: 5 → **10**. |
| 4 | `Setup: criar produto mock pagamento recusado (R$ 10,00)` | `POST /products` com `price: 10.00`. Salva `product_id_declined` nas variáveis. |
| 5 | `Setup: registrar produto mock no estoque` | `POST /inventory-items` com `quantity: 5`. Salva `inventory_id_declined`. |
| 6 | `Setup: adicionar produto mock ao cardápio` | `POST /menus/1/products` com `productId: {{product_id_declined}}`. |

---

### 3.2 Variáveis da Coleção `plano de testes`

| Variável | Preenchimento | Uso |
|---|---|---|
| `jwt_token` | Automático (pré-configuração e T01) | Header `Authorization` em todos os requests como ADMIN. |
| `jwt_token_user` | Automático (pré-configuração) | T03 — acesso com role USER. |
| `product_id_declined` | Automático (Setup 4) | T05 — ID do produto mock R$ 10,00. |
| `inventory_id_declined` | Automático (Setup 5) | Referência ao item de estoque do produto mock. |
| `order_id` | Automático (T06) | ID do pedido criado em T06 (pagamento aprovado). |
| `order_id_declined` | Automático (T05) | ID do pedido criado em T05 (pagamento recusado). |

---

### 3.3 Visão Geral dos Cenários

| ID | Cenário | Tipo | Categoria |
|----|---------|------|-----------|
| T01 | Login com credenciais válidas | ✅ Positivo | Autenticação |
| T02 | Acesso sem token → 401 | ❌ Negativo | Autenticação |
| T03 | Acesso com perfil sem permissão → 403 | ❌ Negativo | Autorização |
| T04a | Campo obrigatório ausente → 400 | ❌ Negativo | Validação |
| T04b | Valor inválido (preço negativo) → 400 | ❌ Negativo | Validação |
| T05 | Pagamento mock recusado — pedido criado (total = R$ 10,00) | ❌ Negativo | Pagamento |
| T05a | Verificar status DECLINED do pagamento | ❌ Negativo | Pagamento |
| T06 | Criar pedido com itens válidos → 201 | ✅ Positivo | Regra de Negócio |
| T07 | Conferir estoque após pedido — baixa confirmada | ✅ Positivo | Regra de Negócio |
| T08 | Pedido com produto inexistente → 404 | ❌ Negativo | Regra de Negócio |
| T09 | Pagamento mock aprovado → status APPROVED (+ verificação cruzada T05) | ✅ Positivo | Pagamento |
| T10 | Pedido com estoque insuficiente → 400 | ❌ Negativo | Regra de Negócio |
| T11 | Consultar pedidos da unidade → 200 | ✅ Positivo | Regra de Negócio |
| T12 | Auditoria — criação de pedido gera log → 200 | ✅ Positivo | Auditoria |

**Total:** 14 cenários — 6 positivos · 8 negativos

---

### 3.4 Rastreio de Estoque (Produto 1 — Coca Cola KS)

```
Após fluxo principal (realizar pedido):  quantity = 5
Após pré-configuração Setup 3 (+5):      quantity = 10
Após T06 (−5):                           quantity = 5  ← T07 verifica este valor
T10 solicita 7 → falha (7 > 5)          ← estoque insuficiente confirmado
```

---

### 3.5 Cenários de Teste

---

#### T01 — Login com credenciais válidas

**Tipo:** ✅ Positivo  
**Categoria:** Autenticação  
**Endpoint:** `POST /api/v1/auth/login`  
**Pré-condição:** Usuário `joao@teste.com` cadastrado via `POST /api/v1/auth/register` com role `ADMIN`.

**Entrada:**
```json
{
  "email": "joao@teste.com",
  "password": "12345678"
}
```

**Saída esperada:**
- **Status:** `200 OK`
- **Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

**Evidência:** Requisição **`T01 - Login com credenciais válidas`** — pasta *autenticação e autorização* na collection `plano de testes`.

---

#### T02 — Acesso sem token → 401

**Tipo:** ❌ Negativo  
**Categoria:** Autenticação  
**Endpoint:** `GET /api/v1/inventory-items`  
**Pré-condição:** Nenhuma. Requisição enviada sem o header `Authorization`.

**Entrada:** *(sem body; sem header Authorization)*

**Saída esperada:**
- **Status:** `401 Unauthorized`
- **Response:**
```json
{
  "status": 401,
  "message": "Não autenticado",
  "timestamp": "2026-06-21T15:00:00.000000"
}
```

**Evidência:** Requisição **`T02 - Acesso sem token → 401`** — pasta *autenticação e autorização* na collection `plano de testes`. Header `Authorization` deliberadamente ausente.

---

#### T03 — Acesso com perfil sem permissão → 403

**Tipo:** ❌ Negativo  
**Categoria:** Autorização  
**Endpoint:** `POST /api/v1/units`  
**Pré-condição:** `jwt_token_user` preenchido pela pré-configuração (login com `joao@gmail.com`, role USER).

**Entrada:**
```json
{
  "name": "Unidade Teste",
  "address": "Rua Exemplo, 100"
}
```
*(Authorization: Bearer `{{jwt_token_user}}`)*

**Saída esperada:**
- **Status:** `403 Forbidden`

> `POST /api/v1/units` requer role `ADMIN` ou `MANAGER`. Token com role `USER` resulta em acesso negado.

**Evidência:** Requisição **`T03 - Acesso com perfil sem permissão → 403`** — pasta *autenticação e autorização* na collection `plano de testes`.

---

#### T04a — Campo obrigatório ausente → 400

**Tipo:** ❌ Negativo  
**Categoria:** Validação de Dados  
**Endpoint:** `POST /api/v1/products`  
**Pré-condição:** `jwt_token` preenchido.

**Entrada — campo `name` ausente (`@NotBlank`):**
```json
{
  "price": "4.99"
}
```

**Saída esperada:**
- **Status:** `400 Bad Request`
- **Response:** mensagem de validação indicando que `name` é obrigatório. Nenhum produto é criado.

**Evidência:** Requisição **`T04a - Campo obrigatório ausente → 400`** — pasta *validação de dados* na collection `plano de testes`.

---

#### T04b — Valor inválido (preço negativo) → 400

**Tipo:** ❌ Negativo  
**Categoria:** Validação de Dados  
**Endpoint:** `POST /api/v1/products`  
**Pré-condição:** `jwt_token` preenchido.

**Entrada — preço violando `@DecimalMin("0.01")`:**
```json
{
  "name": "Produto Inválido",
  "price": "-5.00"
}
```

**Saída esperada:**
- **Status:** `400 Bad Request`
- **Response:** mensagem indicando que o preço deve ser maior que zero. Nenhum produto é criado.

**Evidência:** Requisição **`T04b - Valor inválido (preço negativo) → 400`** — pasta *validação de dados* na collection `plano de testes`.

---

#### T05 — Pagamento mock recusado — pedido criado (total = R$ 10,00)

**Tipo:** ❌ Negativo  
**Categoria:** Pagamento  
**Endpoint:** `POST /api/v1/orders`  
**Pré-condição:** Pré-configuração executada (Setups 4, 5 e 6). `product_id_declined` preenchido com o ID do produto criado a R$ 10,00.

**Entrada:**
```json
{
  "customerId": 1,
  "unitId": 1,
  "deliveryType": "COUNTER",
  "orderChannel": "COUNTER",
  "paymentType": "PIX",
  "items": [
    {
      "productId": "{{product_id_declined}}",
      "quantity": 1
    }
  ]
}
```

**Saída esperada:**
- **Status:** `201 Created` com `totalAmount: 10.00` *(o pedido é criado normalmente; o pagamento é processado de forma assíncrona e resultará em DECLINED)*

> O ID do pedido é salvo automaticamente na variável `order_id_declined`.

**Evidência:** Requisição **`T05 - Pagamento mock recusado (total = R$ 10,00)`** — pasta *pagamento* na collection `plano de testes`.

---

#### T05a — Verificar status DECLINED do pagamento

**Tipo:** ❌ Negativo  
**Categoria:** Pagamento  
**Endpoint:** `GET /api/v1/payments`  
**Pré-condição:** T05 executado.

**Entrada:** *(sem body)*

**Saída esperada:**
- **Status:** `200 OK`
- **Response (trecho relevante):** registro com `amount: 10.00` e `status: DECLINED`.

**Evidência:** Requisição **`T05a - Pagamento mock recusado → status DECLINED`** — pasta *pagamento* na collection `plano de testes`.

---

#### T06 — Criar pedido com itens válidos → 201

**Tipo:** ✅ Positivo  
**Categoria:** Regra de Negócio  
**Endpoint:** `POST /api/v1/orders`  
**Pré-condição:** Pré-configuração executada (Setup 3 repôs estoque para **10 unidades**). Produto 1 *Coca Cola KS* (R$ 4,99) no estoque e no cardápio da unidade 1. Cliente 1 e unidade 1 existentes. `jwt_token` preenchido.

**Entrada:**
```json
{
  "customerId": 1,
  "unitId": 1,
  "deliveryType": "COUNTER",
  "orderChannel": "COUNTER",
  "paymentType": "PIX",
  "items": [
    {
      "productId": 1,
      "quantity": 5
    }
  ]
}
```

**Saída esperada:**
- **Status:** `201 Created`
- **Response (trechos relevantes):**
```json
{
  "id": 1,
  "status": "PENDING",
  "totalAmount": 24.95,
  "items": [
    {
      "productId": 1,
      "productName": "Coca Cola KS",
      "quantity": 5,
      "unitPrice": 4.99
    }
  ]
}
```

> O ID do pedido é salvo automaticamente na variável `order_id`. Após T06, o estoque do produto 1 passa de 10 para **5 unidades**.

**Evidência:** Requisição **`T06 - Criar pedido com itens válidos → 201`** — pasta *regras de negócio* na collection `plano de testes`.

---

#### T07 — Conferir estoque após pedido — baixa confirmada

**Tipo:** ✅ Positivo  
**Categoria:** Regra de Negócio  
**Endpoint:** `GET /api/v1/inventory-items`  
**Pré-condição:** T06 executado com sucesso (5 unidades consumidas do estoque inicial de 10).

**Entrada:** *(sem body)*

**Saída esperada:**
- **Status:** `200 OK`
- **Response (trecho relevante):**
```json
[
  {
    "productId": 1,
    "productName": "Coca Cola KS",
    "quantity": 5,
    "minimumQuantity": 1
  }
]
```
> A quantidade deve ser **5** (10 iniciais − 5 consumidos no pedido).

**Evidência:** Requisição **`T07 - Conferir estoque após pedido (baixa confirmada)`** — pasta *regras de negócio* na collection `plano de testes`.

---

#### T08 — Pedido com produto inexistente → 404

**Tipo:** ❌ Negativo  
**Categoria:** Regra de Negócio  
**Endpoint:** `POST /api/v1/orders`  
**Pré-condição:** Usuário autenticado. Cliente e unidade existentes. `productId: 999` não existe no banco.

**Entrada:**
```json
{
  "customerId": 1,
  "unitId": 1,
  "deliveryType": "COUNTER",
  "orderChannel": "COUNTER",
  "paymentType": "PIX",
  "items": [
    {
      "productId": 999,
      "quantity": 1
    }
  ]
}
```

**Saída esperada:**
- **Status:** `404 Not Found`
- **Response:**
```json
{
  "status": 404,
  "message": "Product not found: 999",
  "timestamp": "2026-06-21T15:00:00.000000"
}
```

**Evidência:** Requisição **`T08 - Pedido com produto inexistente → 404`** — pasta *regras de negócio* na collection `plano de testes`.

---

#### T09 — Pagamento mock aprovado → status APPROVED (+ verificação cruzada T05)

**Tipo:** ✅ Positivo  
**Categoria:** Pagamento  
**Endpoint:** `GET /api/v1/payments`  
**Pré-condição:** T06 e T05 executados. Este cenário consolida dois asserts em uma única chamada.

**Entrada:** *(sem body)*

**Saída esperada:**
- **Status:** `200 OK`
- **Response — dois registros esperados:**
```json
[
  {
    "amount": 24.95,
    "status": "APPROVED",
    "paymentType": "PIX"
  },
  {
    "amount": 10.00,
    "status": "DECLINED",
    "paymentType": "PIX"
  }
]
```

> Assert 1: pagamento com `amount: 24.95` e `status: APPROVED` → confirma T06 (R$ 24,95 ≠ R$ 10,00, mock aprova).  
> Assert 2: pagamento com `amount: 10.00` e `status: DECLINED` → confirma T05 (mock recusa quando total = R$ 10,00).

**Evidência:** Requisição **`T09 - Pagamento mock aprovado → status APPROVED`** — pasta *pagamento* na collection `plano de testes`.

---

#### T10 — Pedido com estoque insuficiente → 400

**Tipo:** ❌ Negativo  
**Categoria:** Regra de Negócio  
**Endpoint:** `POST /api/v1/orders`  
**Pré-condição:** Após T06, o produto *Coca Cola KS* possui **5 unidades** disponíveis. A requisição solicita **7 unidades**.

**Entrada:**
```json
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

**Saída esperada:**
- **Status:** `400 Bad Request`
- **Response:**
```json
{
  "status": 400,
  "message": "Insufficient stock for product 'Coca Cola KS'. Available: 5, requested: 7",
  "timestamp": "2026-06-21T15:59:24.123008"
}
```

**Evidência:** Requisição **`T10 - Pedido com estoque insuficiente → 400`** — pasta *regras de negócio* na collection `plano de testes`.

---

#### T11 — Consultar pedidos da unidade → 200

**Tipo:** ✅ Positivo  
**Categoria:** Regra de Negócio  
**Endpoint:** `GET /api/v1/orders/unit/{unitId}`  
**Pré-condição:** T06 executado. Unidade 1 possui ao menos um pedido registrado.

**Entrada:** `GET /api/v1/orders/unit/1`

**Saída esperada:**
- **Status:** `200 OK`
- **Response (trecho relevante):**
```json
[
  {
    "id": 1,
    "customerId": 1,
    "unitId": 1,
    "status": "PENDING",
    "totalAmount": 24.95
  }
]
```

**Evidência:** Requisição **`T11 - Consultar pedidos da unidade → 200`** — pasta *regras de negócio* na collection `plano de testes`.

---

#### T12 — Auditoria — criação de pedido gera log → 200

**Tipo:** ✅ Positivo  
**Categoria:** Auditoria  
**Endpoint:** `GET /api/v1/audit-logs`  
**Pré-condição:** T06 executado. A anotação `@Auditable(action = AuditAction.CREATE)` em `CreateOrderUseCase` dispara o registro assíncrono de auditoria via `AuditAspect`.

**Entrada:** *(sem body)*

**Saída esperada:**
- **Status:** `200 OK`
- **Response (trecho relevante):**
```json
[
  {
    "action": "CREATE",
    "entityId": 1,
    "performedBy": "joao@teste.com",
    "timestamp": "2026-06-21T15:00:00.000000"
  }
]
```
> O log deve conter ao menos um registro de ação `CREATE` identificando o usuário responsável pela criação do pedido.

**Evidência:** Requisição **`T12 - Auditoria: criação de pedido gera log → 200`** — pasta *auditoria* na collection `plano de testes`.
