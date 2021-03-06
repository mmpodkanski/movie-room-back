package io.github.mmpodkanski.movieroom.controller;

import io.github.mmpodkanski.movieroom.models.response.MovieResponse;
import io.github.mmpodkanski.movieroom.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600)
public class UserBoardController {
    public final Logger logger = LoggerFactory.getLogger(UserBoardController.class);
    public final UserService service;

    UserBoardController(UserService service) {
        this.service = service;
    }

    // TODO: clean code !!! <=> create new method to return UserNAME
    @GetMapping(params = "fav")
    ResponseEntity<List<MovieResponse>> getAllFavouritesMovies(@RequestBody int id) {
        String username = service.loadUserById(id).getUsername();
        logger.info("Displaying all liked movies by user: " + username);

        var movieList = service.readAllFavourites(id);
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }
}
