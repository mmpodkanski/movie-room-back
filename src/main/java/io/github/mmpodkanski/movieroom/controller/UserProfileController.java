package io.github.mmpodkanski.movieroom.controller;

import io.github.mmpodkanski.movieroom.models.response.MovieReadModel;
import io.github.mmpodkanski.movieroom.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
@CrossOrigin("http://localhost:8081")
public class UserProfileController {
    public final Logger logger = LoggerFactory.getLogger(UserProfileController.class);
    public final UserService service;

    UserProfileController(UserService service) {
        this.service = service;
    }

    @GetMapping(params = "fav")
    ResponseEntity<List<MovieReadModel>> getAllFavouritesMovies(@RequestBody int id) {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        logger.info("Displaying all liked movies by user: " + username);
        return ResponseEntity.ok(service.readAllFavourites(id));
    }
}
