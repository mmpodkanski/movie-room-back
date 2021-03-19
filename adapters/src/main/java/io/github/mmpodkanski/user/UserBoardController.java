package io.github.mmpodkanski.user;

import io.github.mmpodkanski.movie.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600)
class UserBoardController {
    private final Logger logger = LoggerFactory.getLogger(UserBoardController.class);
    private final UserFacade service;

    UserBoardController(UserFacade service) {
        this.service = service;
    }

    // TODO: clean code !!! <=> create new method to return UserNAME
    @GetMapping(params = "fav")
    ResponseEntity<List<Movie>> getAllFavouritesMoviesByUser(@RequestBody int id) {
        String username = service.loadUserById(id).getUsername();
        logger.info("Displaying all liked movies by user: " + username);

        var movieList = service.readAllFavourites(id);
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }
}
