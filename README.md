# FreteFlex

API REST para calculo de frete que expoe dois modos de envio: `standard` (padrao) e `express` (expresso). O valor do frete e calculado a partir da distancia e do peso informados na requisicao.

## Objetivo

Oferecer um servico simples e extensivel de calculo de frete, servindo como base para sistemas de e-commerce, logistica ou cotacao de envio. A logica de cada modalidade fica isolada em uma implementacao de `ShippingCalculator`, permitindo adicionar novos tipos de frete sem alterar o restante da aplicacao.

## Tecnologias utilizadas

- Java 21
- Spring Boot 4.2.0
- Spring Web MVC (API REST com Tomcat embutido)
- Maven (com Maven Wrapper)
- Docker (build multi-stage com Maven e JRE)

## Estrutura do projeto

```
src/main/java/com/example/demo
|-- DemoApplication.java              # Ponto de entrada da aplicacao
|-- controller/ShippingController.java # Endpoint REST
|-- service/ShippingService.java       # Selecao da modalidade de frete
|-- domain/ShippingCalculator.java     # Contrato de calculo
|-- domain/StandardShippingCalculator.java
|-- domain/ExpressShippingCalculate.java
|-- dto/ShippingResponse.java          # Resposta da API
src/main/resources/application.properties
src/test/java/com/example/demo/DemoApplicationTests.java
Dockerfile
```

## Regras de calculo

| Tipo      | Formula                        |
|-----------|--------------------------------|
| `standard`| `peso * 1 + distancia * 0.5`   |
| `express` | `peso * 1.5 + distancia * 0.75`|

Tipos diferentes de `standard` e `express` retornam HTTP 400.

## Endpoint

```
GET /shipping/calculate?type={tipo}&distance={distancia}&weight={peso}
```

Parametros:

- `type`: `standard` ou `express`
- `distance`: distancia em unidades de sua preferencia
- `weight`: peso em unidades de sua preferencia

Exemplos:

```bash
curl "http://localhost:8080/shipping/calculate?type=standard&distance=10&weight=2"
# {"ShippingCost":7.0}

curl "http://localhost:8080/shipping/calculate?type=express&distance=10&weight=2"
# {"ShippingCost":10.5}

curl "http://localhost:8080/shipping/calculate?type=foo&distance=10&weight=2"
# HTTP 400
```

## Como rodar na maquina

### Pre-requisitos

- Java 21 (JDK)
- Maven 3.9 ou superior (opcional, o projeto inclui o Maven Wrapper)

### Rodar em modo de desenvolvimento

```bash
./mvnw spring-boot:run
```

A aplicacao sobe em `http://localhost:8080`.

### Rodar a partir do jar

```bash
./mvnw clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

### Executar os testes

```bash
./mvnw test
```

Para alterar a porta, use a propriedade padrao do Spring Boot:

```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar --server.port=9090
```

## Como rodar com Docker

### Construir a imagem

```bash
docker build -t freteflex .
```

### Executar o container

```bash
docker run --rm -p 8080:8080 freteflex
```

A API fica disponivel em `http://localhost:8080`. Para usar outra porta no host:

```bash
docker run --rm -p 9090:8080 freteflex
```

### Testar a API no container

```bash
curl "http://localhost:8080/shipping/calculate?type=standard&distance=10&weight=2"
```

O `Dockerfile` usa build multi-stage: a primeira etapa compila o projeto com Maven e a segunda executa o jar em uma imagem JRE enxuta, rodando com usuario nao privilegiado (`app`).

## Adicionando um novo tipo de frete

1. Crie uma classe em `domain` que implemente `ShippingCalculator` e anote com `@Component`.
2. Injete a nova dependencia em `ShippingService`.
3. Adicione o novo tipo na logica de selecao de `ShippingService`.

Os valores de resposta usam o campo `ShippingCost`, mantido como esta na API atual.
