package io.github.mmpodkanski.movieroom.controller;

import io.github.mmpodkanski.movieroom.models.User;
import io.github.mmpodkanski.movieroom.models.response.MovieResponse;
import io.github.mmpodkanski.movieroom.service.MovieService;
import io.github.mmpodkanski.movieroom.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/board")
@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
@CrossOrigin("http://localhost:8081")
public class AdminBoardController {
    private final Logger logger = LoggerFactory.getLogger(AdminBoardController.class);
    private final MovieService movieService;
    private final UserService userService;

    AdminBoardController(
            final MovieService movieService,
            final UserService userService
    ) {
        this.movieService = movieService;
        this.userService = userService;
    }

    @GetMapping
    ResponseEntity<List<User>> getAllUsers() {
        logger.warn("[ADMIN] Displaying all users!");
        var userList = userService.readAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/requests")
    ResponseEntity<List<MovieResponse>> getAllMoviesToAccept() {
        logger.warn("[ADMIN] Exposing all the movies to accept!");
        List<MovieResponse> movieList = movieService.readAllMoviesToAccept();
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    @PatchMapping(params = "toggle")
    ResponseEntity<?> toggleUserStatus(@RequestBody int id) {
        logger.info("[ADMIN] Changing user status!");
        userService.changeStatusOfUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PatchMapping("/accept/{id}")
    public ResponseEntity<?> acceptMovie(@PathVariable int id) {
        logger.info("[ADMIN] Movie with id: " + id + " has been accepted");
        movieService.changeStatusOfMovie(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
