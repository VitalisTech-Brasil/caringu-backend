# 🦘 **CaringU – Backend**

Plataforma completa de gestão para profissionais de educação física, oferecendo controle de treinos, alunos, métricas de evolução, comunicação e autenticação segura para alunos e personal trainers.

---

# 📘 **Sumário**

1. [Sobre o Projeto](#-sobre-o-projeto)
2. [Arquitetura e Tecnologias](#-arquitetura-e-tecnologias)
3. [Pré-requisitos](#️-pré-requisitos)
4. [Ambientes de Execução](#-ambientes-de-execução)

    * Desenvolvimento local
    * Execução via Docker (com mensageria e Redis)
5. [Configuração de Perfis (Profiles)](#-configuração-de-perfis-profiles)
6. [Configuração do IntelliJ (SENHA + Profiles)](#️-configuração-do-intellij)
7. [Verificando o Redis em execução](#-verificando-o-redis-em-execução)
8. [Estrutura do Projeto](#-estrutura-do-projeto)
9. [Segurança (JWT + Spring Security)](#-segurança)
10. [Swagger / OpenAPI](#-documentação-swagger)
11. [Boas Práticas Aplicadas](#-boas-práticas-adotadas)
12. [Contribuintes](#-contribuintes)
13. [Licença](#-licença)

---

# 🧠 **Sobre o Projeto**

O **CaringU** é uma solução corporativa voltada ao gerenciamento centralizado de interação entre **alunos e personal trainers**, oferecendo:

* Gestão de perfis (Aluno / Personal Trainer)
* Autenticação segura via JWT
* Estratégias de treino, objetivos e evolução física
* Mensageria assíncrona para notificações
* Suporte a múltiplos ambientes (dev, docker, prod)
* Redis para controle de tentativas de login
* Arquitetura escalável, orientada a boas práticas

---

# 🏗 **Arquitetura e Tecnologias**

Tecnologias adotadas no projeto:

* **Java 21**
* **Spring Boot 3.4.3**
* Spring Web
* Spring Security
* JWT
* Spring Data JPA
* Hibernate
* MySQL 8 (docker/prod)
* Redis 7 (controle de login)
* RabbitMQ (mensageria)
* Docker & Docker Compose
* Swagger / OpenAPI
* Linux (execução recomendada para mensageria)

---

# 🖥️ **Pré-requisitos**

### **Ambiente de desenvolvimento**

* Java 21
* IntelliJ IDEA ou VSCode
* Postman / Insomnia
* Git

### **Para mensageria e Redis**

* Docker
* Docker Compose
* Redis 7
* RabbitMQ (via Docker Compose)

---

# 🚀 **Ambientes de Execução**

---

## **1. Desenvolvimento (Local + Profile `dev`)**

Clone o repositório:

```bash
git clone https://github.com/VitalisTech-Brasil/caringu-backend.git
cd caringu-backend
```

O backend utiliza o profile `dev` para rodar sem Redis.

---

## **2. Execução Completa via Docker (Redis + RabbitMQ + MySQL)**

Clone também o repositório de notificações (necessário):

```bash
git clone https://github.com/VitalisTech-Brasil/caringu-notificacao.git
```

Ambos os repositórios devem estar no **mesmo diretório**.

### Para subir toda a stack:

```bash
docker compose up --build -d
```

Isso iniciará:

| Serviço     | Porta       | Descrição                 |
| ----------- | ----------- | ------------------------- |
| Backend     | 8080        | Aplicação principal       |
| MySQL       | 3307 → 3306 | Banco principal           |
| Redis       | 6379        | Cache / Controle de login |
| RabbitMQ    | 15672       | Painel de mensageria      |
| Notificação | 8081        | Microsserviço de alerta   |

---

# 🧩 **Configuração de Perfis (Profiles)**

O projeto utiliza perfis separados:

| Profile          | Uso                             | Redis |
| ---------------- | ------------------------------- | ----- |
| `dev`            | desenvolvimento local           | ❌    |
| `dev-with-redis` | desenvolvimento local com Redis | ✔️    |
| `docker`         | ambiente docker                 | ✔️    |
| `prod`           | produção                        | ✔️    |

---

# ⚙️ **Configuração do IntelliJ**

### 1. Definir variável SENHA

Vá em:

> Run → Edit Configurations → Environment Variables

Adicione:

* **Name:** `SENHA`
* **Value:** `sua-senha-aqui`

### 2. Ativar profile `dev`

Ainda em Edit Configurations → VM Options:

```
-Dspring.profiles.active=dev
```

---

# 📌 **Verificando o Redis em execução**

Quando a stack estiver rodando (Docker), é possível verificar se as tentativas de login estão sendo salvas no Redis.

### 1️⃣ Acessar o container

No Windows (Git Bash ou Mintty), use `winpty`:

```bash
winpty docker exec -it caringu-redis redis-cli
```

Em Linux/Mac:

```bash
docker exec -it caringu-redis redis-cli
```

### 2️⃣ Listar as chaves do sistema

```redis
KEYS *
```

Você deve ver algo como:

```
login:tentativas:email@exemplo.com
login:bloqueado:email@exemplo.com
```

### 3️⃣ Ler o valor de uma chave

```redis
GET `login:tentativas:email@exemplo.com`
```

Ou:

```redis
TTL login:bloqueado:email@exemplo.com
```

### 4️⃣ Limpar tudo (opcional)

```redis
FLUSHALL
```

---

# 📁 **Estrutura do Projeto**

Organizada segundo padrões corporativos:

```
src/
├── main/java/tech.vitalis.caringu
│   ├── config
│   ├── consumer
│   ├── controller
│   ├── dtos
│   ├── entity
│   ├── enums
│   ├── exception
│   ├── id
│   ├── mapper
│   ├── repository
│   ├── service
│   └── strategy
└── resources
    ├── application.properties
    ├── application-dev.properties
    ├── application-dev-with-redis.properties
    ├── application-prod.properties
```

---

# 🔐 **Segurança**

O projeto implementa:

* Spring Security 6
* JWT com expiração controlada e armazenamento em Cookies
* Filtro de autenticação customizado
* Controle de tentativas de login via Redis
* Bloqueio temporário automático após 5 falhas

Fluxo de autenticação:

```
/auth/login  → validação → geração JWT → resposta
```

---

# 📚 **Documentação Swagger**

Disponível automaticamente em:

```
http://localhost:8080/swagger-ui/index.html
```

ou

```
http://localhost:8080/docs
```

---

# 🛠 **Boas Práticas Adotadas**

* Clean Architecture aplicada parcialmente
* DTOs encodados com Records (Java 21)
* Separação rigorosa entre Controller / Service / Repository
* Mappers manuais de alta legibilidade
* Exception Handler global
* Profiles para isolamento de ambientes
* Uso de Redis para operações não persistentes
* Docker como camada de orquestração padronizada

---

# 👥 **Contribuintes**

Time de desenvolvimento backend:
**Bianca, Gustavo, Lucas, Igor, Pedro e Rafael**

---

# 📄 **Licença**

Licença MIT. Consulte o arquivo `LICENSE` para mais detalhes.

