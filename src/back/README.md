# Recompensa Verde - Back-End

## Descrição

Este é o backend de um sistema de venda de reciclaveis entre empresas. Ele oferece serviços RESTful implementados em Java utilizando o framework Spring Boot.

## Funcionalidades Principais

### Empresas Compradoras

- **Login:** Realiza a autenticação de empresas compradoras.
- **CRUD:** Cria, atualiza, busca e exclui empresas compradoras.

### Empresas Vendedoras

- **Login:** Realiza a autenticação de empresas vendedoras.
- **CRUD:** Cria, atualiza, busca e exclui empresas vendedoras.

### Recicláveis

- **Cadastro:** Adiciona recicláveis associados a uma empresa vendedora.
- **Listagem:** Lista recicláveis por tipo (plástico, alumínio, eletrônico) ou de uma empresa específica.
- **Atualização:** Atualiza detalhes de um reciclável.
- **Exclusão:** Remove recicláveis associados a uma empresa.

### Negociações

- **Gestão de Negociações:** Cria, atualiza, conclui ou nega negociações de recicláveis.
- **Listagem:** Obtém negociações associadas a empresas compradoras e vendedoras.
- **Desconto:** Aplica desconto em negociações.

### Solicitações de Pedido

- **Cadastro:** Registra solicitações de pedido de recicláveis.
- **Atualização:** Atualiza status de solicitações (negociação, concluído, cancelado).
- **Listagem:** Filtra pedidos por status e empresa.
- **Exclusão:** Remove solicitações de pedido.

### Indicadores

- Calcula indicadores de desempenho, como:
    - Redução no volume de resíduos.
    - Contribuição à cidade de Belo Horizonte.
    - Taxa de entrega de recicláveis.

## Tecnologias Utilizadas

- **Java**: Linguagem principal.
- **Spring Boot**: Framework para criação de aplicações web.
- **JPA/Hibernate**: Persistência de dados.
- **Swagger/OpenAPI**: Documentação da API.
- **Jakarta Validation**: Validação de dados.

## Estrutura de Pastas

- **Controller:** Contém os controladores REST que expõem os endpoints da API.
- **Service:** Implementa a lógica de negócio.
- **Model:** Define as entidades do banco de dados.
- **DTO:** Define objetos de transferência de dados entre camadas.

## Endpoints

### Empresas Compradoras

- `POST /empresaCompradora/login`
- `POST /empresaCompradora`
- `GET /empresaCompradora/{id}`
- `PUT /empresaCompradora/{id}`
- `DELETE /empresaCompradora/{id}`

### Empresas Vendedoras

- `POST /empresaVendedora/login`
- `POST /empresaVendedora`
- `GET /empresaVendedora/{id}`
- `PUT /empresaVendedora/{id}`
- `DELETE /empresaVendedora/{id}`

### Recicláveis

- `POST /reciclavel/cadastrar`
- `GET /reciclavel/listar/{id}`
- `GET /reciclavel/listar`
- `GET /reciclavel/plastico`
- `GET /reciclavel/aluminio`
- `GET /reciclavel/eletronico`
- `PUT /reciclavel/atualizar/{id}`
- `DELETE /reciclavel/deletar/{id}/{empresaId}`

### Negociações

- `POST /negociacao/{empresaVendedoraId}/{empresaCompradoraId}/{reciclavelId}/{pedidoId}`
- `GET /negociacao/getAll`
- `GET /negociacao/{id}`
- `PUT /negociacao/{id}/negociacao`
- `PUT /negociacao/{id}/negar`
- `PUT /negociacao/{id}/concluir`
- `GET /negociacao/empresa/{id}`
- `GET /negociacao/empresa/compradora/{empresaCompradoraId}`
- `GET /negociacao/aplicarDesconto/{empresaCompradoraId}/{precoTotal}`

### Solicitações de Pedido

- `POST /api/solicitacao-pedido`
- `GET /api/solicitacao-pedido`
- `GET /api/solicitacao-pedido/{id}`
- `PUT /api/solicitacao-pedido/{id}`
- `DELETE /api/solicitacao-pedido/{id}`
- `PUT /api/solicitacao-pedido/{id}/negociacao`
- `PUT /api/solicitacao-pedido/{id}/negar`
- `PUT /api/solicitacao-pedido/{id}/concluir`
- `PUT /api/solicitacao-pedido/{id}/cancelar`

### Indicadores

- `GET /indicadores/reducao-residuos`
- `GET /indicadores/contribuicaoBH`
- `GET /indicadores/taxaDeEntrega`