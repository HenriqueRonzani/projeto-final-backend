# Projeto Final Backend

## Membros da Equipe:
- Henrique da Silva Ronzani
- Leonardo da Silva Joaquim

## Tema
App de Organização Pessoal – Kanban

## Descrição Geral do Projeto

O KanbanApp é uma API RESTful voltada para organização pessoal e produtividade, permitindo que usuários criem, organizem e compartilhem tarefas de forma visual, no estilo Kanban (com colunas e cartões).

Cada usuário pode criar abas (boards), que funcionam como colunas do Kanban (por exemplo: “A Fazer”, “Em Progresso”, “Concluído”).
Dentro de cada aba, o usuário pode criar cards, que representam tarefas ou atividades individuais.

Além disso, o sistema permitirá o compartilhamento de abas entre usuários, possibilitando colaboração em equipe.

## Modelos (Entidades)

### Entidade: Usuário
Campo	Tipo	Descrição
id	UUID	Identificador único do usuário
nome	String	Nome completo do usuário
email	String	E-mail do usuário (único)
senha	String	Senha criptografada do usuário
dataCriacao	LocalDateTime	Data de criação do usuário

Relacionamentos:
1:N com Aba (um usuário pode possuir várias abas)
N:N com Aba (compartilhamento entre usuários)

### Entidade: Aba (Board)
Campo	Tipo	Descrição
id	UUID	Identificador único da aba
titulo	String	Nome da aba (ex: “A Fazer”, “Concluído”)
cor	String	Cor representativa da aba
usuarioId	UUID	Dono principal da aba
compartilhadaCom	List<UUID>	Lista de usuários com acesso compartilhado
dataCriacao	LocalDateTime	Data de criação da aba

Relacionamentos:
N:1 com Usuário
1:N com Card

### Entidade: Card
Campo	Tipo	Descrição
id	UUID	Identificador único do card
titulo	String	Título do card (tarefa)
descricao	String	Descrição detalhada da tarefa
prioridade	String	Nível de prioridade (ex: Alta, Média, Baixa)
status	String	Estado atual da tarefa
dataCriacao	LocalDateTime	Data de criação
dataConclusao	LocalDateTime	Data de conclusão (opcional)
abaId	UUID	Identificador da aba a que pertence

Relacionamentos:
N:1 com Aba

## DTOs (Data Transfer Objects)

Os DTOs serão usados para transferência segura e estruturada de dados entre o cliente e o servidor, evitando exposição de atributos sensíveis e facilitando a validação.

 ### Usuário DTOs

UserCreateDTO
{
  "nome": "João Silva",
  "email": "joao@kanban.com",
  "senha": "123456"
}


### UserResponseDTO
{
  "id": "uuid",
  "nome": "João Silva",
  "email": "joao@kanban.com"
}

## Aba DTOs

### AbaCreateDTO
{
  "titulo": "Em Progresso",
  "cor": "#FFC107"
}


### AbaResponseDTO
{
  "id": "uuid",
  "titulo": "Em Progresso",
  "cor": "#FFC107",
  "usuarioId": "uuid"
}

## Card DTOs

### CardCreateDTO
{
  "titulo": "Criar API Kanban",
  "descricao": "Desenvolver as rotas iniciais da API",
  "prioridade": "Alta",
  "abaId": "uuid"
}


### CardResponseDTO
{
  "id": "uuid",
  "titulo": "Criar API Kanban",
  "descricao": "Desenvolver as rotas iniciais da API",
  "prioridade": "Alta",
  "status": "A Fazer",
  "abaId": "uuid"
}

## Mapeamento (Mapping)

O mapeamento entre as entidades e os DTOs será feito usando ModelMapper dentro da camada de serviço, conforme exemplo:

ModelMapper mapper = new ModelMapper();
Usuario usuario = mapper.map(userCreateDTO, Usuario.class);
UserResponseDTO response = mapper.map(usuario, UserResponseDTO.class);

## Diagrama de Banco de Dados do Projeto
https://app.diagrams.net/#G1bj_c3r7WAeNnYk_et1v53v8QEhkmafGi#%7B%22pageId%22%3A%22v57n-Yx-cETdbVC3EAeT%22%7D

Mapeamento de rotas em collection de postman
```json
{
  "info": {
    "name": "Projeto final",
    "_postman_id": "b7c98df8-1a3f-4a60-a12b-bf6a34a1e1a9",
    "description": "Coollection",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "users",
      "item": [
        {
          "name": "GET /users",
          "request": { "method": "GET", "url": "/users" }
        },
        {
          "name": "GET /users/:id",
          "request": { "method": "GET", "url": "/users/:id" }
        },
        {
          "name": "POST /users",
          "request": {
            "method": "POST",
            "header": [{ "key": "Content-Type", "value": "application/json" }],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"name\": \"Name Example\",\n  \"email\": \"example@email.com\",\n  \"password\": \"teste123\"\n}"
            },
            "url": "/users"
          }
        },
        {
          "name": "PUT /users/:id",
          "request": {
            "method": "PUT",
            "header": [{ "key": "Content-Type", "value": "application/json" }],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"name\": \"Name Example\",\n  \"email\": \"example@email.com\",\n  \"password\": \"teste123\"\n}"
            },
            "url": "/users/:id"
          }
        }
      ]
    },
    {
      "name": "cards",
      "item": [
        {
          "name": "GET /cards",
          "request": { "method": "GET", "url": "/cards" }
        },
        {
          "name": "GET /cards/:id",
          "request": { "method": "GET", "url": "/cards/:id" }
        },
        {
          "name": "POST /cards",
          "request": {
            "method": "POST",
            "header": [{ "key": "Content-Type", "value": "application/json" }],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"title\": \"Terminar projeto da faculdade\",\n  \"content\": \"Preciso adicionar mais algumas informacoes na documentacao e concluir o projeto\",\n  \"status\": \"done\",\n  \"tab_id\": 1\n}"
            },
            "url": "/cards"
          }
        },
        {
          "name": "PUT /cards/:id",
          "request": {
            "method": "PUT",
            "header": [{ "key": "Content-Type", "value": "application/json" }],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"title\": \"Terminar projeto da faculdade\",\n  \"content\": \"Preciso adicionar mais algumas informacoes na documentacao e concluir o projeto\",\n  \"tab_id\": 1\n}"
            },
            "url": "/cards/:id"
          }
        },
        {
          "name": "DELETE /cards/:id",
          "request": { "method": "DELETE", "url": "/cards/:id" }
        }
      ]
    },
    {
      "name": "tabs",
      "item": [
        {
          "name": "GET /tabs",
          "request": { "method": "GET", "url": "/tabs" }
        },
        {
          "name": "GET /tabs/:id",
          "request": { "method": "GET", "url": "/tabs/:id" }
        },
        {
          "name": "POST /tabs",
          "request": {
            "method": "POST",
            "header": [{ "key": "Content-Type", "value": "application/json" }],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"name\": \"Para fazer\",\n  \"color\": \"red\",\n  \"action_on_move\": \"finish\",\n  \"users_id\": []\n}"
            },
            "url": "/tabs"
          }
        },
        {
          "name": "PUT /tabs/:id",
          "request": {
            "method": "PUT",
            "header": [{ "key": "Content-Type", "value": "application/json" }],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"name\": \"Para fazer\",\n  \"color\": \"red\",\n  \"action_on_move\": \"finish\",\n  \"users_id\": []\n}"
            },
            "url": "/tabs/:id"
          }
        },
        {
          "name": "PATCH /tabs/:id/users",
          "request": {
            "method": "PATCH",
            "header": [{ "key": "Content-Type", "value": "application/json" }],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"users_id\": []\n}"
            },
            "url": "/tabs/:id/users"
          }
        },
        {
          "name": "DELETE /tabs/:id",
          "request": { "method": "DELETE", "url": "/tabs/:id" }
        }
      ]
    }
  ]
}
```