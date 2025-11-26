# 📌 Kanban – API RESTful de Organização de Tarefas

API para gerenciamento de tarefas estilo Kanban, com suporte a usuários, grupos, abas (tabs), cards e integração opcional com Google Calendar.

---

## 🚀 Funcionalidades

**Usuários**

* Cadastro, login e autenticação via JWT
* Atualização de perfil
* Busca por ID, email e listagem de todos os usuários

**Grupos**

* Criação, atualização e deleção de grupos
* Adição e remoção de membros

**Abas (Tabs)**

* Criadas dentro de grupos
* Cada aba representa uma coluna do Kanban
* Suporte a CRUD completo

**Cards**

* Criados dentro de abas
* Campos: título, conteúdo, status, datas, usuários associados
* Opção para criar evento no Google Calendar

**Integração Google Calendar**

* Fluxo OAuth 2.0 completo
* Armazenamento de `access_token` e `refresh_token`
* Criação automática de eventos ao criar Cards

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

Token retornado no body:

```json
{ "token": "jwt_here" }
```

Header:

```
Authorization: Bearer <token>
```

---

## 👤 Rotas de Usuários

```
GET /users/:id         → Buscar usuário por ID
GET /users?email=...   → Buscar usuário por email
GET /users/all         → Listar todos usuários
POST /users            → Criar usuário
PUT /users/:id         → Atualizar usuário
DELETE /users/:id      → Deletar usuário
```

---

## 👥 Rotas de Grupos

```
GET /groups/:id         → Buscar grupo por ID
GET /groups/all         → Listar todos grupos
POST /groups            → Criar grupo
PUT /groups/:id         → Atualizar grupo
PATCH /groups/:id/users → Atualizar usuários do grupo
DELETE /groups/:id      → Deletar grupo
```

---

## 🗂️ Rotas de Abas (Tabs)

```
GET /tabs             → Listar todas abas
GET /tabs/:id         → Buscar aba por ID
POST /tabs            → Criar aba
PUT /tabs/:id         → Atualizar aba
DELETE /tabs/:id      → Deletar aba
```

Exemplo de criação:

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
GET /cards           → Listar todos cards
GET /cards/:id       → Buscar card por ID
POST /cards          → Criar card
PUT /cards/:id       → Atualizar card
DELETE /cards/:id    → Deletar card
```

Exemplo de criação:

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

---

## 📅 Integração com Google Calendar

**Obter URL de Consentimento**

```
GET /calendar/consent
```

**Callback**

```
GET /calendar/consent/callback?code=...&state=...
```

Após consentimento, backend salva tokens e permite criação de eventos ao criar cards.

---

## ⚙️ Tecnologias

* Java 21
* Spring Boot 3.5.6

    * Spring Web, Spring Security (JWT), Spring Data JPA, WebFlux
* PostgreSQL
* Docker Compose
* Maven
* dotenv-java

---

## ▶️ Como Rodar

1. Subir banco e Rodar aplicação: Pelo IntelliJ (executar `KanbanApplication.java`)
2. Acessar: `http://localhost:8080`

---

## 📄 Licença

Projeto acadêmico – uso livre para fins educacionais.