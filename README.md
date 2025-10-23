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

## Principais Funcionalidades da API
Método	Rota	Descrição
POST	/usuarios	Cria um novo usuário
GET	/usuarios	Lista todos os usuários
GET	/usuarios/{id}	Retorna um usuário específico
PUT	/usuarios/{id}	Atualiza dados de um usuário
DELETE	/usuarios/{id}	Remove um usuário
POST	/abas	Cria uma nova aba
GET	/abas	Lista todas as abas (com filtros e paginação)
GET	/abas/{id}	Retorna uma aba específica
PUT	/abas/{id}	Atualiza uma aba
DELETE	/abas/{id}	Remove uma aba
POST	/cards	Cria um novo card dentro de uma aba
GET	/cards	Lista todos os cards (com filtros de status, prioridade, etc.)
PUT	/cards/{id}	Atualiza dados do card
DELETE	/cards/{id}	Remove um card

## Arquitetura Inicial

A API será estruturada em camadas, conforme boas práticas do Spring Boot:

src/
 ├── controller/
 │    ├── UsuarioController.java
 │    ├── AbaController.java
 │    └── CardController.java
 ├── service/
 │    ├── UsuarioService.java
 │    ├── AbaService.java
 │    └── CardService.java
 ├── dto/
 │    ├── UsuarioDTO.java
 │    ├── AbaDTO.java
 │    └── CardDTO.java
 ├── model/
 │    ├── Usuario.java
 │    ├── Aba.java
 │    └── Card.java
 ├── repository/
 │    ├── UsuarioRepository.java
 │    ├── AbaRepository.java
 │    └── CardRepository.java

## Diagrama de Banco de Dados do Projeto
https://app.diagrams.net/#G1bj_c3r7WAeNnYk_et1v53v8QEhkmafGi#%7B%22pageId%22%3A%22v57n-Yx-cETdbVC3EAeT%22%7D
