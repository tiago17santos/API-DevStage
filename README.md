# DevStage - Sistema de Inscrição para Eventos

**DevStage** é uma aplicação que permite a inscrição de usuários em eventos, fornecendo um mecanismo de rastreamento e visualização de inscrições. Ela é desenvolvida para atender a necessidades de gerenciamento de eventos e inscrições em uma plataforma intuitiva.

## Funcionalidades

- **Cadastro de usuários**: Permite a criação de novos usuários para inscrição em eventos.
- **Inscrição em eventos**: Os usuários podem se inscrever em eventos, com um número de inscrição gerado automaticamente.
- **Exibição de ranking de eventos**: Visualiza o ranking de inscritos em eventos específicos.
- **Indicação de usuários**: Possibilidade de indicação de usuários por outros participantes.

## Tecnologias Utilizadas

O projeto foi desenvolvido utilizando as seguintes tecnologias:

- **Java 21**: Linguagem de programação principal.
- **Spring Boot 3.x**: Framework utilizado para o desenvolvimento da aplicação.
- **Spring Data JPA**: Para a integração com bancos de dados relacionais (MySQL).
- **MySQL**: Banco de dados utilizado para armazenar dados de usuários e inscrições.
- **Docker**: Utilizado para facilitar a execução do banco de dados MySQL.
- **Postman**: Para documentação automática da API.
- **JUnit**: Framework para testes unitários e integração.
- **TestRestTemplate**: Usado para realizar testes de integração com os endpoints REST da aplicação.
- **Maven**: Gerenciador de dependências e construção do projeto.

## Como Executar o Projeto

### Pré-requisitos

Certifique-se de ter o seguinte instalado em sua máquina:

- **JDK 21**
- **MySQL** ou qualquer outro banco de dados relacional (caso queira alterar a configuração).
- **Maven** (opcional, se você não usar o IDE com Maven integrado).

### Passos para executar:

1. Clone o repositório:

    ```bash
    git clone https://github.com/usuario/DevStage.git
    ```

2. Navegue até o diretório do projeto:

    ```bash
    cd DevStage
    ```

3. Configure o banco de dados em `application.properties` (no diretório `src/main/resources`).

4. Execute o projeto com o Maven:

    ```bash
    mvn spring-boot:run
    ```

5. Acesse a aplicação no seu navegador:

    ```
    http://localhost:8080
    ```
    
6. Para acessar a documentação da API com o Postman:

    ```
    https://documenter.getpostman.com/view/35154992/2sAYk8u3FD
    ```
    
## Como Contribuir

1. Faça um fork deste repositório.
2. Crie uma nova branch para suas alterações (`git checkout -b feature/xyz`).
3. Faça as alterações e commit (`git commit -am 'Add new feature'`).
4. Envie para o seu fork (`git push origin feature/xyz`).
5. Crie um novo Pull Request.

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).

