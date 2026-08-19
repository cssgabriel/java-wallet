# Wallet API

API REST para gerenciamento de carteiras de investimentos, com registro de transações (compra/venda) de ativos e cotações em tempo real integradas via [Brapi](https://brapi.dev).

## Sobre o projeto

A Wallet API permite que um usuário organize uma ou mais carteiras (`Wallet`), registre transações de compra e venda de ativos (`Transaction`) e consulte dados de mercado (tickers e cotações) sem depender de cadastro manual de ativos — o ativo é criado automaticamente no banco na primeira vez que é referenciado em uma transação, usando dados reais da Brapi.

### Principais decisões de arquitetura

- **Separação entre dado de mercado e dado persistido**: endpoints de busca/cotação (`/market/*`) são um proxy direto para a Brapi, sem tocar o banco. Endpoints de domínio (`/assets`, `/wallets/*/transactions`) trabalham sobre dados persistidos.
- **Find-or-create de Asset**: um `Asset` só é persistido quando efetivamente usado em uma transação — evita poluir o banco com todo ticker apenas pesquisado.
- **Histórico imutável**: o preço pago em cada operação fica salvo na própria `Transaction` (`unitPrice`), independente da cotação atual do `Asset`, preservando o histórico real de compra/venda.
- **Transações atômicas**: a criação de uma `Transaction` (que pode envolver criar um `Asset` novo) é orquestrada dentro de uma única transação de banco (`@Transactional`), evitando registros órfãos em caso de falha.

## Tecnologias

- **Java 17+**
- **Spring Boot**
  - Spring Web (REST)
  - Spring Data JPA / Hibernate
  - Bean Validation (Jakarta Validation)
- **Banco de dados relacional** (via Spring Data JPA)
- **RestClient** (Spring) para integração HTTP com a API da Brapi
- **Records (Java)** para DTOs imutáveis
- **Maven/Gradle** (build)

## Estrutura do projeto

```
src/main/java/css/gabriel/wallet
├── controller/     # Endpoints REST
├── service/        # Regras de negócio e orquestração
├── repository/     # Interfaces Spring Data JPA
├── model/          # Entidades JPA
├── dto/            # Records de entrada/saída da API
├── dto/brapi/       # DTOs de resposta da API externa (Brapi)
├── mapper/         # Conversão entre DTOs da Brapi e DTOs da API
└── exception/      # Exceções de domínio
```

## Endpoints

### Market Data (dados externos, sem persistência)

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/market/tickers?page=1` | Lista paginada de ativos disponíveis na Brapi |
| `GET` | `/market/quote/{ticker}` | Cotação atual de um ativo específico |

### Wallets

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/wallets/{walletId}/transactions` | Lista as transações de uma carteira |
| `GET` | `/wallets/{walletId}/transactions/{id}` | Detalha uma transação específica da carteira |
| `POST` | `/wallets/{walletId}/transactions` | Registra uma nova transação (compra/venda) |

**Exemplo de requisição — criar transação:**

```http
POST /wallets/1/transactions
Content-Type: application/json

{
  "ticker": "PETR4",
  "type": "BUY",
  "quantity": 10,
  "unitPrice": 38.20
}
```

Se `unitPrice` não for informado, a API utiliza a cotação atual do ativo (obtida via Brapi no momento do primeiro cadastro).

**Fluxo interno da criação de transação:**

1. Valida se a `Wallet` existe.
2. Busca o `Asset` pelo ticker no banco local (find-or-create):
   - Se existir, reaproveita.
   - Se não existir, consulta a Brapi, persiste o `Asset` e segue.
3. Persiste a `Transaction` vinculada à `Wallet` e ao `Asset`, com o preço da operação.

## Modelo de dados (resumo)

- **Wallet** → possui várias `Transaction`.
- **Asset** → representa um ativo (ticker, nome, cotação mais recente conhecida). Uma linha por ticker, reaproveitada entre todas as transações daquele ativo.
- **Transaction** → registro histórico e imutável de uma operação de compra/venda, com o preço pago naquele momento (`unitPrice`), independente da cotação atual do `Asset`.

## Roadmap

- [ ] **Spring Security + JWT** — autenticação e autorização por usuário
- [ ] **Testes automatizados** (JUnit + Mockito) — cobertura de services e regras de negócio
- [ ] **Testcontainers** — testes de integração contra banco real
- [ ] **Cache com Redis** — reduzir chamadas repetidas à Brapi
- [ ] **RabbitMQ** — processamento assíncrono (a definir escopo)

## Como rodar localmente

```bash
# Configurar a variável de ambiente com o token da Brapi
export BRAPI_API_TOKEN=seu_token_aqui

# Build
./mvnw clean install

# Run
./mvnw spring-boot:run
```

A aplicação sobe por padrão em `http://localhost:8080`.

---

Projeto pessoal de estudo, desenvolvido com Spring Boot para praticar arquitetura de APIs REST, integração com serviços externos e boas práticas de modelagem de domínio.