# 📌 Kanban – API de Organização de Tarefas

API RESTful para gerenciamento de tarefas em estilo Kanban, com suporte a grupos, abas, cartões e integração opcional com Google Calendar. O projeto permite colaboração entre usuários, organização em equipes e automação de eventos.

---

# 🚀 Funcionalidades

### 👤 **Usuários**

* Cadastro, login e autenticação via JWT
* Atualização de perfil
* Filtros avançados via Specifications (nome, email, grupo, card)

### 👥 **Grupos**

* Usuários podem criar grupos
* Adicionar e remover membros
* Abas e tarefas associadas a grupos

### 🗂️ **Abas (Tabs)**

* Criadas dentro de grupos
* Cada aba representa uma coluna do Kanban
* Cada aba contém múltiplos cards

### 📝 **Cards**

* Criados dentro de abas
* Campos: título, descrição, prioridade, status
* Opção para **criar automaticamente um evento no Google Calendar**

### 📅 **Integração com Google Calendar**

* Fluxo OAuth 2.0 completo
* Armazenamento de access_token e refresh_token
* Criação automática de eventos ao criar Cards (opcional)

---

# 🛠️ Tecnologias Utilizadas

* **Java 21**
* **Spring Boot 3.5.6**

    * Web / Validation
    * Spring Security (JWT)
    * Spring Data JPA
    * WebFlux (Google APIs)
* **PostgreSQL**
* **Docker Compose**
* **Maven**
* **JWT – jjwt**
* **dotenv-java** para variáveis de ambiente

---

# 📂 Estrutura do Projeto

```
src/main/java/com/projeto/backend/Kanban
├── Auth
│   ├── Controllers
│   ├── DTOs
│   ├── Repositories
│   ├── Services
│   └── Specifications
├── Config
├── Integration
│   └── Google
│       ├── Controllers
│       ├── DTOs
│       ├── Repositories
│       └── Services
├── Models
└── KanbanApplication.java
```

---

# 🐳 Docker (Banco de Dados)

Arquivo `compose.yaml`:

```yaml
services:
  postgres:
    image: 'postgres:latest'
    environment:
      - 'POSTGRES_DB=kanban'
      - 'POSTGRES_PASSWORD=password'
      - 'POSTGRES_USER=kanban_db_user'
    ports:
      - '5432:5432'
    volumes:
      - postgres-data:/var/lib/postgresql

volumes:
  postgres-data:
```

### Subir banco

```bash
docker compose up -d
```

---

# ⚙️ Configuração – Variáveis de Ambiente

Crie `.env` na raiz com:

```
GOOGLE_CLIENT_ID=...
GOOGLE_CLIENT_SECRET=...
GOOGLE_REDIRECT_URI=http://localhost:8080/calendar/consent/callback

JWT_SECRET=uma_chave_secreta_segura

SPRING_MAIL_HOST=smtp.gmail.com
SPRING_MAIL_USERNAME=...
SPRING_MAIL_PASSWORD=...
```

---

# 🔐 Autenticação (JWT)

### 📌 Login

```
POST /auth/login
```

### 📌 Registro

```
POST /auth/register
```

O token JWT é retornado em:

```json
{
  "token": "jwt_here"
}
```

E deve ir no header:

```
Authorization: Bearer <token>
```

---

# 👤 Rotas de Usuários

### Listar usuários

```
GET /users/all
```

Com filtros:

```
GET /users/all?name=ana&email=gmail&groupId=1
```

### Criar usuário (admin / registro interno)

```
POST /users
```

### Atualizar usuário

```
PUT /users/{id}
```

---

# 👥 Rotas de Grupos

```
GET /groups
GET /groups/{id}
POST /groups
PUT /groups/{id}
PATCH /groups/{id}/users
```

---

# 🗂️ Rotas de Abas (Tabs)

```
GET /tabs
GET /tabs/{id}
POST /tabs
PUT /tabs/{id}
DELETE /tabs/{id}
PATCH /tabs/{id}/users
```

---

# 📝 Rotas de Cards

```
GET /cards
GET /cards/{id}
POST /cards
PUT /cards/{id}
DELETE /cards/{id}
```

### Exemplo de criação com evento no Calendar:

```json
{
  "title": "Reunião do grupo",
  "description": "Alinhar entrega final",
  "priority": "Alta",
  "status": "todo",
  "tab_id": 3,
  "create_calendar_event": true
}
```

---

# 📅 Integração com Google Calendar

## 🔄 Fluxo OAuth

### 1️⃣ Obter URL de Consentimento

```
POST /calendar/consent
```

Resposta:

```json
{
  "consent_url": "https://accounts.google.com/o/oauth2/v2/auth?..."
}
```

### 2️⃣ Callback do Google

```
GET /calendar/consent/callback?code=...&state=...
```

Backend troca `code` por:

* access_token
* refresh_token
* expires_in

E salva no banco.

### 3️⃣ Criação automática de eventos

Quando um card é criado com:

```json
"create_calendar_event": true
```

O serviço cria um evento no Google Calendar e registra:

* o ID do card
* o ID do evento no Google
* datas relevantes

---

# 🗃️ Modelos (Resumo)

### User

* id, name, email, password
* relacionamento:

    * N:N grupos
    * N:1 tabs

### Group

* id, name
* usuários
* tabs

### Tab

* id, title, color
* cards

### Card

* id, title, description, priority, status
* tab_id
* create_calendar_event (boolean)

### OAuthToken

* accessToken
* refreshToken
* expiresAt
* userId

### CardCalendarEvent

* cardId
* googleEventId
* start
* end

---

# ▶️ Como Rodar o Projeto

### 1. Subir banco

```
docker compose up -d
```

### 2. Rodar aplicação

Rodar pelo IntelliJ: abrir o projeto e executar a classe Application. (Ja sobe o banco caso necessario)


### 3. Acessar

```
http://localhost:8080
```

---

# 📄 Licença

Projeto acadêmico – uso livre para fins educacionais.
