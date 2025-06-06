# 🚑 HumanLink API

## 📘 Visão Geral

**HumanLink** é uma plataforma de coordenação de ajuda humanitária pós-desastre. Foi criada para enfrentar a desorganização e ineficiência no socorro após eventos extremos, como terremotos, enchentes e incêndios de grande escala. A plataforma conecta vítimas, equipes de resgate, doadores e voluntários, promovendo uma gestão **eficiente, transparente e centralizada** dos recursos e necessidades.

---

## ⚠️ Problema

Após eventos extremos, a coordenação da ajuda humanitária é caótica. Isso dificulta o atendimento rápido às vítimas e a alocação eficiente de recursos como água, alimentos, medicamentos e abrigos.

---

## 💡 Solução

Uma plataforma web que:

- Permite o cadastro de **necessidades urgentes** (alimentos, água, abrigo, medicamentos, resgate);
- Possibilita o registro de **doadores e voluntários**;
- Realiza o **mapeamento geográfico em tempo real** de recursos e necessidades;
- Garante **comunicação eficiente** via notificações e mensagens.

---

## 🚀 Diferenciais da Plataforma

- 🗺️ **Mapeamento em tempo real** de áreas afetadas e recursos disponíveis;
- 🔗 **Conexão direta e transparente** entre vítimas e agentes de ajuda;
- 🎯 **Priorização inteligente** das demandas mais urgentes;
---

## 📄 OpenAPI (Swagger)

A especificação completa da API está disponível em:

🔗 [Swagger UI - HumanLink](https://humanlink-api-production.up.railway.app/swagger-ui/)

Arquivo: `openapi.yaml`

---

## 🛠️ Tecnologias Utilizadas

- **Back-end:** Java (Quarkus)
- **Banco de Dados:** Oracle Database
- **Documentação:** OpenAPI (Swagger)
- **Hospedagem:** Railway.app
- **Frontend:** [Repositório GitHub](https://github.com/annabonfim/gs-2025)

---

## 🔧 Endpoints da API

### 📌 Usuário

| Método | Rota                   | Descrição              | Códigos de Status         |
|--------|------------------------|------------------------|---------------------------|
| GET    | `/usuario`             | Listar todos           | 200, 500                  |
| POST   | `/usuario`             | Criar novo             | 201, 400, 500             |
| GET    | `/usuario/{idUsuario}` | Buscar por ID          | 200, 404, 500             |
| PUT    | `/usuario/{idUsuario}` | Atualizar por ID       | 200, 400, 404, 500        |
| DELETE | `/usuario/{idUsuario}` | Remover por ID         | 204, 404, 500             |

---

### 🔐 Login

| Método | Rota                 | Descrição            | Códigos de Status |
|--------|----------------------|----------------------|-------------------|
| GET    | `/login`             | Listar logins        | 200, 500          |
| POST   | `/login`             | Criar login          | 201, 400, 500     |
| GET    | `/login/{idLogin}`   | Buscar por ID        | 200, 404, 500     |
| PUT    | `/login/{idLogin}`   | Atualizar por ID     | 200, 400, 404, 500|
| DELETE | `/login/{idLogin}`   | Remover por ID       | 204, 404, 500     |

---

### 🎯 Campanhas Humanitárias

| Método | Rota                             | Descrição               | Códigos de Status |
|--------|----------------------------------|--------------------------|-------------------|
| GET    | `/campanhas-humanitarias`        | Listar campanhas         | 200, 500          |
| POST   | `/campanhas-humanitarias`        | Criar nova campanha      | 201, 400, 500     |
| GET    | `/campanhas-humanitarias/{id}`   | Buscar campanha por ID   | 200, 404, 500     |
| PUT    | `/campanhas-humanitarias/{id}`   | Atualizar campanha       | 200, 400, 404, 500|
| DELETE | `/campanhas-humanitarias/{id}`   | Remover campanha         | 204, 404, 500     |

---

### 📍 Localizações Registradas

| Método | Rota                                | Descrição               | Códigos de Status |
|--------|-------------------------------------|--------------------------|-------------------|
| GET    | `/localizacoes-registradas`         | Listar localizações      | 200, 500          |
| POST   | `/localizacoes-registradas`         | Criar localização        | 201, 400, 500     |
| GET    | `/localizacoes-registradas/{id}`    | Buscar por ID            | 200, 404, 500     |
| PUT    | `/localizacoes-registradas/{id}`    | Atualizar localização    | 200, 400, 404, 500|
| DELETE | `/localizacoes-registradas/{id}`    | Remover localização      | 204, 404, 500     |

---

### 🎁 Registro de Doação

| Método | Rota                      | Descrição             | Códigos de Status |
|--------|---------------------------|------------------------|-------------------|
| GET    | `/registro-doacao`        | Listar doações         | 200, 500          |
| POST   | `/registro-doacao`        | Registrar nova doação  | 201, 400, 500     |
| GET    | `/registro-doacao/{id}`   | Buscar por ID          | 200, 404, 500     |
| PUT    | `/registro-doacao/{id}`   | Atualizar doação       | 200, 400, 404, 500|
| DELETE | `/registro-doacao/{id}`   | Remover doação         | 204, 404, 500     |

---

### 👥 Voluntários

| Método | Rota                 | Descrição            | Códigos de Status |
|--------|----------------------|-----------------------|-------------------|
| GET    | `/voluntarios`       | Listar voluntários    | 200, 500          |
| POST   | `/voluntarios`       | Criar voluntário      | 201, 400, 500     |
| GET    | `/voluntarios/{id}`  | Buscar por ID         | 200, 404, 500     |
| PUT    | `/voluntarios/{id}`  | Atualizar voluntário  | 200, 400, 404, 500|
| DELETE | `/voluntarios/{id}`  | Remover voluntário    | 204, 404, 500     |

---

### 📷 Relatos

| Método | Rota              | Descrição           | Códigos de Status |
|--------|-------------------|---------------------|-------------------|
| GET    | `/relatos`        | Listar relatos      | 200, 500          |
| POST   | `/relatos`        | Criar novo relato   | 201, 400, 500     |
| GET    | `/relatos/{id}`   | Buscar por ID       | 200, 404, 500     |
| PUT    | `/relatos/{id}`   | Atualizar relato    | 200, 400, 404, 500|
| DELETE | `/relatos/{id}`   | Remover relato      | 204, 404, 500     |

---

### 🔔 Notificações

| Método | Rota                   | Descrição               | Códigos de Status |
|--------|------------------------|--------------------------|-------------------|
| GET    | `/notificacao`         | Listar notificações      | 200, 500          |
| POST   | `/notificacao`         | Criar notificação        | 201, 400, 500     |
| GET    | `/notificacao/{id}`    | Buscar por ID            | 200, 404, 500     |
| PUT    | `/notificacao/{id}`    | Atualizar notificação    | 200, 400, 404, 500|
| DELETE | `/notificacao/{id}`    | Remover notificação      | 204, 404, 500     |

---

### 🌍 Áreas de Desastre

| Método | Rota                        | Descrição               | Códigos de Status |
|--------|-----------------------------|--------------------------|-------------------|
| GET    | `/areas-desastre`           | Listar áreas             | 200, 500          |
| POST   | `/areas-desastre`           | Criar nova área          | 201, 400, 500     |
| GET    | `/areas-desastre/{id}`      | Buscar por ID            | 200, 404, 500     |
| PUT    | `/areas-desastre/{id}`      | Atualizar área           | 200, 400, 404, 500|
| DELETE | `/areas-desastre/{id}`      | Remover área             | 204, 404, 500     |

---

### 📝 Cadastros

| Método | Rota              | Descrição           | Códigos de Status |
|--------|-------------------|----------------------|-------------------|
| GET    | `/cadastros`      | Listar cadastros     | 200, 500          |
| POST   | `/cadastros`      | Criar novo cadastro  | 201, 400, 500     |
| GET    | `/cadastros/{id}` | Buscar por ID        | 200, 404, 500     |
| PUT    | `/cadastros/{id}` | Atualizar cadastro   | 200, 400, 404, 500|
| DELETE | `/cadastros/{id}` | Remover cadastro     | 204, 404, 500     |

---

## 🧪 Como Executar o Projeto Localmente

### 1. Clonar o repositório


```bash
git clone https://github.com/alanerochaa/humanlink-api.git
cd humanlink-api

2. Instalar dependências com Maven
mvn clean install

3. Configurar Banco de Dados (Oracle)
Configure uma instância do Oracle Database.

Altere o arquivo src/main/resources/application.properties com suas credenciais (URL, usuário, senha).

4. Rodar a aplicação em modo desenvolvimento
./mvnw quarkus:dev

5. Acessar a documentação Swagger
Acesse em:
🔗 https://humanlink-api-production.up.railway.app/swagger-ui/