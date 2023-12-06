# Sistema de Registro de Ponto Eletrônico

O seguinte projeto é um sistema de registro de ponto eletrônico, tomando como inspiração para este sistema a cara do TJAM. Este projeto oferece aos usuários a facilidade de criar e recuperar seus perfis, realizar até 6 registros diários, consultar históricos e justificar pontos incorretos. Desenvolvido com foco na responsividade, garante uma experiência consistente em dispositivos móveis, monitores e notebooks. Priorizamos a segurança, usabilidade e adaptação fluida em diferentes plataformas. Explore as funcionalidades para otimizar o controle de ponto em seu ambiente de trabalho.

## Layout web

![Web 1](https://github.com/santosdp/Sistema-de-Registro-de-Ponto/blob/main/exemplo/web1.png)

![Web 2](https://github.com/santosdp/Sistema-de-Registro-de-Ponto/main/exemplo/web2.png)

![Web 3](https://github.com/santosdp/Sistema-de-Registro-de-Ponto/main/exemplo/web3.png)

![Web 4](https://github.com/santosdp/Sistema-de-Registro-de-Ponto/main/exemplo/web4.png)

![Web 5](https://github.com/santosdp/Sistema-de-Registro-de-Ponto/main/exemplo/web5.png)

![Web 6](https://github.com/santosdp/Sistema-de-Registro-de-Ponto/main/exemplo/web6.png)

![Web 7](https://github.com/santosdp/Sistema-de-Registro-de-Ponto/main/exemplo/web7.png)

## Layout mobile

![Mobile 1](https://github.com/santosdp/Sistema-de-Registro-de-Ponto/main/exemplo/mobile1.png) ![Mobile 2](https://github.com/santosdp/Sistema-de-Registro-de-Ponto/main/exemplo/mobile2.png) ![Mobile 3](https://github.com/santosdp/Sistema-de-Registro-de-Ponto/main/exemplo/mobile3.png)

![Mobile 4](https://github.com/santosdp/Sistema-de-Registro-de-Ponto/main/exemplo/mobile4.png) ![Mobile 5](https://github.com/santosdp/Sistema-de-Registro-de-Ponto/main/exemplo/mobile5.png) ![Mobile 6](https://github.com/santosdp/Sistema-de-Registro-de-Ponto/main/exemplo/mobile6.png)

## 🚀 Começando

Este projeto foi totalmente desenvolvido utilizando Node.js e seus frameworks/ferramentas, não somente isso como também utilizando o banco de dados PostgreSQL. Como tal, são necessários alguns especificações para garantir a implantação correta do sistema.

Consulte **[Implantação](#-implanta%C3%A7%C3%A3o)** para saber como implantar o projeto.

### 📋 Pré-requisitos

Para garantir que o sistema funcione corretamente são necessárias as seguintes ferramentas:

```
Node.js 16.0 ou superior
```
```
PostgreSQL 8.10 ou superior
```

### 🔧 Instalação

Para colocar o sistema em execução será necessário seguir alguns passos, fique ligado para garantir que o PostgreSQL está aberto e funcionando corretamente.

Primeiramente, abrá o diretório raiz do projeto no terminal e execute o seguinte comando:

```
cd back-end && npm install
```

E assim que o comando acima terminar de baixar os pacotes necessários desta parte do projeto, execute o seguinte comando:

```
cd ../front-end && npm install
```

Após isto é necessário realizar somente mais uma alteração para poder rodar este projeto normalmente. Abra o programa pgAdmin e crie um banco de dados chamado ‘Sistema_Ponto’ (caso não possua este programa, crie manualmente utilizando o comando 'CREATE DATABASE Sistema_Ponto') e então abra o arquivo 'database.js' na pasta 'back-end/Config/' do projeto e altere os dados de username e password para se adequar as suas configurações.

Feito isto o sistema está pronto para ser colocado no ar.

## 📦 Implantação

Para colocar o sistema ativo é necessário abrir duas abas do terminal na pasta raiz do projet, em uma aba utilize o comando:

```
cd back-end && npm run start
```

E na outra aba utilize:

```
cd front-end && quasar dev
```

Com isto o sistema está ativo e pronto para ser utilizado.

## 🛠️ Construído com

As ferramentas utilizadas para criação de todo este sistema foram:

* [Vue.js](https://vuejs.org/) - O framework JavaScript usado para construção das interfaces.
* [Quasar](https://quasar.dev/) - O framework web de componentes utilizado.
* [Express.js](https://expressjs.com/) - O framework utilizado para a criação da API REST utilizada.
* [JWT](https://jwt.io/) - Usada para fazer a autentiação de login de usuário.
* [PostgreSQL](https://www.postgresql.org/) - Banco de dados utilizado para guardar as informações do sistema.

## 📌 Versão

1.0.0/MVP. 
