package io.github.mmpodkanski.user;

import io.github.mmpodkanski.user.dto.UserMovieDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600)
class UserController {
    private final UserFacade service;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    UserController(final UserFacade service) {
        this.service = service;
    }

    @PostMapping("/{id}/upgrade")
    @PreAuthorize(value = "hasRole('ADMIN')")
    ResponseEntity<?> upgradeUser(
            @PathVariable int id
    ) {
        service.setAdminRole(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/favourites")
    ResponseEntity<List<UserMovieDto>> getFavourites(
            @PathVariable int id
    ) {
        logger.info("User with id: " + id + " reading all favourites!");
        var result = service.readAllFavourites(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
