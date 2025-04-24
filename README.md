# CaringU - Backend

## 🧠 Sobre o projeto

O CaringU é uma aplicação voltada para a área fitness, permitindo o gerenciamento completo da relação entre personal trainers e seus alunos. A plataforma oferece funcionalidades como:

- Cadastro de pessoas com perfis distintos: Aluno e Personal Trainer
- Login com autenticação via JWT
- Gestão de usuários, treinos, objetivos e evolução
- API RESTful com boas práticas de desenvolvimento
- Documentação completa via Swagger/OpenAPI

## 🚀 Como rodar o projeto localmente

## ✔️ Pré-requisitos
Antes de começar, você vai precisar ter instalado:

- Java 21
- IDE (como IntelliJ ou VSCode)
- Postman (Insomnia ou qualquer cliente REST)
- Git

# 💻 Passos para rodar em ambiente de desenvolvimento

```
# Clone o repositório

git clone https://github.com/VitalisTech-Brasil/caringu-backend.git
cd caringu-backend
```

# 🔑 Configuração da variável de ambiente
O projeto utiliza uma variável de ambiente chamada SENHA que é usada para permitir que o Spring Boot utilize as configurações do application-dev.properties.
Você precisa defini-la no IntelliJ da seguinte forma:

Vá até o menu: Run > Edit Configurations

Em Environment variables, adicione:

# Passo adicional para configurar a variável de ambiente SENHA no IntelliJ:
Para rodar o projeto em ambiente de desenvolvimento, você precisa configurar a variável de ambiente SENHA no IntelliJ (ou sua IDE preferida) para que o application-dev.properties seja utilizado corretamente.

1. No IntelliJ, vá até `Run > Edit Configurations`.

2. Selecione a configuração de execução que você usa para rodar o Spring Boot.

3. No campo Environment variables, clique no ícone de mais (+) e adicione a variável:

- Name: SENHA

- Value: ``sua-senha-aqui``

### A aplicação estará disponível em:

```http://localhost:8080```

### Você pode acessar a documentação Swagger em:

```http://localhost:8080/swagger-ui/index.html ou http://localhost:8080/docs```

# 🛠️ Tecnologias e ferramentas utilizadas

- Java 21

- Spring Boot 3.4.3

- Spring Web

- Spring Security

- JWT (JSON Web Token)

- Spring Data JPA

- Hibernate

- H2 Database (dev)

- MySQL (prod/test)

- Swagger/OpenAPI 3

- Maven

# 🧱 Estrutura do Projeto

```
src
├── main
│   ├── java
│   │   └── tech.vitalis.caringu
│   │       ├── config                  # Configurações globais da aplicação, como Swagger e Security
│   │       ├── controller              # Endpoints RESTful que expõem os serviços da aplicação
│   │       ├── dtos                    # Objetos de Transferência de Dados usados para entrada e saída nas APIs
│   │       ├── entity                  # Entidades JPA que representam as tabelas do banco de dados
│   │       ├── enums                   # Enumerações utilizadas no sistema (ex: nível de atividade, tipo de usuário)
│   │       ├── exception               # Tratamento de exceções personalizadas e handlers globais
│   │       ├── id                      # Classes auxiliares de ID compostos que implementam Serializable (ex: chaves compostas)
│   │       ├── mapper                  # Conversores manuais entre DTOs e entidades (seguindo boas práticas)
│   │       ├── repository              # Interfaces que extendem JpaRepository para acesso aos dados
│   │       ├── service                 # Camada de serviço com a lógica de negócio da aplicação
│   │       └── strategy                # Implementações do padrão Strategy para regras de negócio variáveis (ex: cálculos, filtros, validações dinâmicas)
│   └── resources
│       └── application.properties      # Arquivo de configuração principal da aplicação 
│       └── application-dev.properties  # Arquivo de configuração de desenvolvimento da aplicação
└── test
    └── java
        └── tech.vitalis.caringu        # Testes unitários e de integração
```

# 🔐 Segurança

O projeto utiliza Spring Security com autenticação baseada em JWT.

- Após o login (rota `/auth/login`), o usuário recebe um token JWT válido por tempo limitado.

- Esse token deve ser enviado no header `Authorization` com o prefixo `Bearer`.

# 📚 Documentação da API

A documentação está disponível via Swagger:

```
http://localhost:8080/swagger-ui/index.html
```

Exemplo de rota documentada:

```
POST /auth/login
```

Envia um JSON com email e senha, e recebe um token de autenticação válido.

# ⚙️ Boas Práticas Adotadas

- Separação de responsabilidades (Controller, Service, Repository)

- DTOs para encapsulamento e segurança de dados

- MapStruct manual (Mapper customizado)

- Validações com anotações (@Valid, @NotNull, etc)

- Tratamento global de exceções (@ControllerAdvice)

- Uso de padrões RESTful (HTTP Status Codes, Verbos adequados)

- Código limpo, coeso e com nomes semânticos

- Testes unitários em construção

# 📈 Futuras implementações

- Gerenciamento de treinos com CRUD completo

- Geração de gráficos e PDF de evolução

- Notificações e lembretes

- Integração com frontend mobile

- Autenticação via redes sociais (OAuth2)

# 👨‍💼 Contribuintes

Bianca, Gustavo, Lucas, Igor, Pedro e Rafael - Desenvolvimento Backend

# 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.