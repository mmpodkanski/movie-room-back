# MovieRoom
FRONT-END: https://github.com/mmpodkanski/movie-room-front

MovieRoom allows you to follow your favourites movies.

> Own movies and actors databases !

* Default crud operations like: create, read, update, remove.
* Has been used design pattern: **Domain-Driven Design** and **clean-architecture**, which allows to optimalize and in future separate application (e.g. easy to change spring framework to other).
* Admin board is place where you can block users or accept all movie requests.
* As user you can insert movie to favourites, add new movie to pending requests or comment each movie.
* Has been used public api to display news from around the world.

## Screenshots

![image](https://user-images.githubusercontent.com/75319903/116761656-85766d00-aa18-11eb-9453-bda7b9fcbb1b.png)

![image](https://user-images.githubusercontent.com/75319903/116762321-5b25af00-aa1a-11eb-8183-73197e40bf78.png)

![image](https://user-images.githubusercontent.com/75319903/116762375-83151280-aa1a-11eb-8d69-bbba7292c16f.png)

## Code snippet showing DDD and clean-architecture potential (e.g. encapsulation)

![image](https://user-images.githubusercontent.com/75319903/116762840-06833380-aa1c-11eb-848e-3f3ea453978c.png)


## Technologies and tools
* Java 11
* Spring
* Maven
* Hibernate
* MySQL
* H2
* IntelliJ IDEA


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
