# MovieRoom
FRONT-END: https://github.com/mmpodkanski/movie-room-front

Heroku: https://mmpod-movie-room.herokuapp.com/

MovieRoom allows you to follow your favourites movies.

> Own movies and actors databases !

This project uses:
- Design pattern: **Domain-Driven Design** and **clean-architecture**, which allows to optimalize and in future separate application to microservices,
- **public api** to display news from around the world,
- **JPA Projections**,
- **events**,
- breakdown of the reading methods into **QueryRepository** and **Facades** into writers (CQRS),
- **ORM.xml** mapping


### Short description

Admin can:
  - block users, toggle movie requests (from users), edit or delete each movie or an actor

As user you can:
  - add movie to favourites
  - add new movie (request)
  - comment each movie


## Screenshots

![image](https://user-images.githubusercontent.com/75319903/116761656-85766d00-aa18-11eb-9453-bda7b9fcbb1b.png)

![image](https://user-images.githubusercontent.com/75319903/117204403-8c262b00-adf0-11eb-89d9-61d2f9dc6039.png)

![image](https://user-images.githubusercontent.com/75319903/116762321-5b25af00-aa1a-11eb-8183-73197e40bf78.png)

![image](https://user-images.githubusercontent.com/75319903/117204344-7ca6e200-adf0-11eb-82e0-87378f32b455.png)

![image](https://user-images.githubusercontent.com/75319903/116762375-83151280-aa1a-11eb-8d69-bbba7292c16f.png)

![image](https://user-images.githubusercontent.com/75319903/117204454-99431a00-adf0-11eb-9ca8-f38c7f8b1b92.png)

## Code snippet showing DDD and clean-architecture potential (e.g. encapsulation)

![image](https://user-images.githubusercontent.com/75319903/116762840-06833380-aa1c-11eb-848e-3f3ea453978c.png)


## Technologies and tools
* Java 11
* Spring
* Maven
* Hibernate
* MySQL
* PostgreSQL (heroku)
* IntelliJ IDEA

## Endpoints (examples)
| Value | Endpoint | Access |
| :---         |     :---:      |          ---: |
| GET   | /movies    | not required  |
| GET   | /movies?top-rated    | not required  |
| GET   | /movies?new-added   | not required  |
| GET   | /movies?year=xxxx    | user  |
| GET   | /movies/:id    | user  |
| GET   | /actors    | user  |
| GET   | /actors/:id    | user  |
| POST, PUT, DELETE   | /actors/:id    | admin  |
| POST   | /movies    | user(request), admin |
| PATCH, DELETE   | /movies/:id    | admin  |
| POST | /movies/:id/comments    | user |
| PATCH   | /movies/:id?add-fav    | user  |
| PATCH   | /movies/:id?remove-fav    | user  |
| GET, PATCH   | /admin/board/users   | admin  |
| PATCH   | /admin/board/requests/:id/accept  | admin  |
| PATCH   | /admin/board/requests/:id/refuse  | admin  |

And there are still such as /auth.., /api.. (public-api), /users.. (profile)


## Installation

1. Clone the Project using link https://github.com/mmpodkanski/movie-room-back.git or Download the zip
2. Open project in IntelliJ:
- File->New->Project from Version Control then past clone link
- File->Open then find and open downloaded zip
3. Run application in **monolith** package

OR

You can run application with maven wrapper:
```
- mvnw clean install
- mvnw spring-boot:run
```

## Contact
Created by [@mmpodkanski](https://github.com/mmpodkanski/)
