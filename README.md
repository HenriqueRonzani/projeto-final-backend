
# 📌 Kanban – API RESTful de Organização de Tarefas

API para gerenciamento de tarefas estilo Kanban, com suporte a usuários, grupos, abas (tabs), cards e integração opcional com Google Calendar.


## Membros da equipe
1. Henrique da Silva Ronzani
2. Leonardo da Silva Joaquim

---

## 🚀 Funcionalidades

**Usuários**

* Cadastro, login e autenticação via JWT
* Atualização de perfil
* Busca por ID, email e listagem
* **Notificação automática por e-mail ao serem adicionados a um Card**
* Tokens OAuth armazenados por usuário
* Filtros via query params

**Grupos**

* Criação, edição e exclusão
* Associação e remoção de membros
* Cada grupo contém suas próprias abas e cards
* Usuário só acessa grupos dos quais participa *(permissionamento completo ainda não implementado)*
* Filtros via query params

**Abas (Tabs)**

* Representam colunas do Kanban
* Criadas dentro de grupos
* Suporte a nome, cor e comportamento de movimentação
* CRUD completo
* Filtros via query params

**Cards**

* Criados dentro de abas
* Campos: título, conteúdo, status, datas, aba, grupo e usuários associados
* **Notificação automática para os usuários participantes**
* **Opção de criar evento no Google Calendar**
* Filtros via query params

**Integração Google Calendar**

* OAuth 2.0 completo
* Armazenamento de `access_token` e `refresh_token`
* **Criação de eventos** vinculados ao Card
* (Editar/excluir eventos existe, mas **não está implementado nesta versão**)

---

## 📁 Modelo de Dados

A API utiliza um conjunto de entidades relacionadas para gerenciar usuários, grupos, abas e cards, além da integração com Google Calendar.
A seguir estão os modelos e seus relacionamentos principais.


### 🧍‍♂️ **User**

**Tabela:** `users`
Campos:

* `id`
* `name`
* `email` (único)
* `password`

Relacionamentos:

* **N:N** → `groups`
* **1:N** → `created_cards`
* **N:N** → `cards` (cards atribuídos ao usuário)
* **1:N** → `OAuthToken`

---

### 👥 **Group**

**Tabela:** `groups`
Campos:

* `id`
* `name`

Relacionamentos:

* **N:N** → `users`
* **1:N** → `tabs`

---

### 🗂️ **Tab**

**Tabela:** `tabs`
Campos:

* `id`
* `name`
* `color`
* `actionOnMove` (enum)

Relacionamentos:

* **N:1** → `group`
* **1:N** → `cards`

---

### 📝 **Card**

**Tabela:** `cards`
Campos:

* `id`
* `title`
* `content`
* `status` (enum `CardStatus`)
* `start_date`
* `end_date`

Relacionamentos:

* **N:1** → `creator` (`User`)
* **N:N** → `users`
* **N:1** → `tab`
* **1:1** → `CardCalendarEvent`

---

### 📅 **CardCalendarEvent**

**Tabela:** `cards_events`
Campos:

* `id`
* `google_event_id`

Relacionamentos:

* **1:1** → `card`

---

### 🔑 **OAuthToken**

**Tabela:** `oauth_token`
Campos:

* `id`
* `access_token`
* `refresh_token`
* `expires_at` (Instant)

Relacionamentos:

* **N:1** → `user`

---

## 🔐 Autenticação (JWT)

**Login**

```
POST /auth/login
```

**Registro**

```
POST /auth/register
```

Resposta:

```json
{ "token": "jwt_here" }
```

Header obrigatório:

```
Authorization: Bearer <token>
```

---

## 👤 Rotas de Usuários

```
GET    /users/:id          → Buscar usuário por ID
GET    /users?email=...    → Buscar por email
GET    /users/all          → Listar todos
POST   /users              → Criar usuário
PUT    /users/:id          → Atualizar usuário
DELETE /users/:id          → Deletar usuário
```

---

## 👥 Rotas de Grupos

```
GET     /groups/:id          → Buscar grupo
GET     /groups/all          → Listar grupos
POST    /groups              → Criar grupo
PUT     /groups/:id          → Atualizar grupo
PATCH   /groups/:id/users    → Atualizar membros
DELETE  /groups/:id          → Deletar grupo
```

---

## 🗂️ Rotas de Abas (Tabs)

```
GET    /tabs           → Listar todas
GET    /tabs/:id       → Buscar por ID
POST   /tabs           → Criar aba
PUT    /tabs/:id       → Atualizar aba
DELETE /tabs/:id       → Deletar aba
```

Exemplo:

```json
{
  "name": "To Do",
  "color": "#ff0000",
  "actionOnMove": "START",
  "groupId": 2
}
```

---

## 📝 Rotas de Cards

```
GET    /cards         → Listar cards
GET    /cards/:id     → Buscar card
POST   /cards         → Criar card
PUT    /cards/:id     → Atualizar card
DELETE /cards/:id     → Deletar card
```

Exemplo:

```json
{
  "title": "Criar API",
  "content": "Fazer endpoints",
  "status": "FINISHED",
  "start": "2025-11-26T10:00:00Z",
  "end": "2025-11-26T12:00:00Z",
  "tabId": 1,
  "userIds": [1],
  "createEvent": true
}
```

### 🔔 Notificações Automáticas

Ao criar um card:

* todos os usuários do campo `userIds` recebem e-mail automaticamente (exceto criador)
* caso `createEvent = true`, o evento é criado no Google Calendar do **primeiro usuário da lista**

---

## 📅 Rotas do Google Calendar

```
GET /calendar/consent
GET /calendar/consent/callback?code=...&state=...
```

Tokens são armazenados — **não é necessário novo consentimento** a cada evento.

---

## ⚙️ Tecnologias

* Java 21
* Spring Boot 3.5.6

    * Spring Web, Security (JWT), JPA, WebFlux
* PostgreSQL
* Docker Compose
* Maven
* dotenv-java
* Gmail SMTP para notificações

---

## 🧪 Validações

A API retorna erros padronizados no formato:

```json
{
  "email": "Email obrigatorio"
}
```

---

## ▶️ Como Rodar

1. Configure o `.env` baseado no `.env.example`
2. Suba o banco via Docker `docker compose up`
3. Build do projeto `mvn clean install`
4. Run projeto `mvn spring-boot:run`
5. Acesse: `http://localhost:8080`

### Alternativa Via IntelliJ (recomendado)
1. Abra o projeto na IDE
2. Realize o Build do Maven
3. Clique no botão Run ao da classe principal
4. O IntelliJ identifica o docker-compose.yml e oferece a possibilidade de rodar docker automaticamente.
