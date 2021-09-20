# Address Service

O serviço de endereços é contruído utilizando a tecnologia REST em sua comunicação, todos os seus endpoints são seguros, ou seja, acessados apenas com um token JWT
gerado pelo serviço de usuários.

Em linhas gerais ele faz um acesso ao banco de dados para inserir ou recuperar informações de endereços a partir de um CEP passado por parametro. Um ponto de 
performance nessa consulta al banco de dados é o cache implementado nas consultas ao endereço que armazena por ***30 segundos*** a informação recolhida (esse timeout 
pode ser alterado no arquivo de properties para o tempo desejado)

## Endpoints
```
GET /api/v1/address/get-address-by-cep/{cep}
Payload Response
{
    "success": true,
    "data": {
        "id": 2,
        "postalCode": "12940622",
        "address": "Pca Miguel Vairo",
        "city": "Atibaia",
        "state": "SP",
        "country": "Brasil"
    }
}
```
Juntamente com um código de responsta ```200```
```
POST /api/v1/address/add-address
Payload Request:

{
    "address": "Lucas Nogueira Garces",
    "postalCode": "12940342",
    "neighborhood": "Maristla",
    "cityId": 1
}
```
Notem que o ``` cityId ``` é um código que sera passado direto do front end
A resposta desse endpoint é um ```201```
