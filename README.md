# SpaceStack

![Frontend CI](https://github.com/sylhare/SpaceStack/workflows/Frontend%20CI/badge.svg) ![Backend CI](https://github.com/sylhare/SpaceStack/workflows/Backend%20CI/badge.svg)

## Intro

This is a full stack application prototype to manage a Space Colony.
It is composed of:
  - [Frontend](./frontend): A React single page application.
  - [Backend](./backend): A Spring Kotlin Rest application.
  
You can run the project using:

```bash
docker-compose up --build
``` 

You can have more details on each part of the application by checking the readme in their respective folder.

## Functions

To sum it up:
- The login is Jwt base and allow to record the user's action.
- The "baby call flow" is handled meaning you can create, retrieve, approve/deny and review the baby requests.
- Each accepted baby request will increase the population count.
- The baby requests are saved and updated inside a Mongo Database.
