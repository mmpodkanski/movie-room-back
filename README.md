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

1.
![image](https://user-images.githubusercontent.com/75319903/119206014-3f9e4900-ba9a-11eb-875f-bf3710a0e4a2.png)

2.
![image](https://user-images.githubusercontent.com/75319903/119206029-4c22a180-ba9a-11eb-959d-b2969d197840.png)

3.
![image](https://user-images.githubusercontent.com/75319903/119206049-5c3a8100-ba9a-11eb-8148-177714bf9906.png)

4.
![image](https://user-images.githubusercontent.com/75319903/119206078-6bb9ca00-ba9a-11eb-8d62-2fbc9dbf80f3.png)

5.
![image](https://user-images.githubusercontent.com/75319903/119206087-74aa9b80-ba9a-11eb-819c-17be6852e3a0.png)

6.
![image](https://user-images.githubusercontent.com/75319903/119205961-1ed5f380-ba9a-11eb-89ed-94f77c7e22b1.png)

7.
![image](https://user-images.githubusercontent.com/75319903/119206094-7d02d680-ba9a-11eb-8086-021f8a0bd974.png)


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
