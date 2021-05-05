package io.github.mmpodkanski.user;

import io.github.mmpodkanski.movie.MovieFacade;
import io.github.mmpodkanski.movie.MovieQueryRepository;
import io.github.mmpodkanski.movie.dto.MovieResponseDto;
import io.github.mmpodkanski.user.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/board")
@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
class AdminBoardController {
    private final Logger logger = LoggerFactory.getLogger(AdminBoardController.class);
    private final MovieQueryRepository movieQueryRepository;
    private final MovieFacade movieFacade;
    private final UserFacade userFacade;

    AdminBoardController(
            final MovieQueryRepository movieQueryRepository,
            final MovieFacade movieFacade,
            final UserFacade userFacade
    ) {
        this.movieQueryRepository = movieQueryRepository;
        this.movieFacade = movieFacade;
        this.userFacade = userFacade;
    }

    @GetMapping("/users")
    ResponseEntity<List<UserDto>> getAllUsers() {
        var userList = userFacade.readAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/requests")
    ResponseEntity<List<MovieResponseDto>> getAllMoviesToAccept() {
        logger.info("[ADMIN] Exposing all the movies to accept!");
        List<MovieResponseDto> movieList = movieQueryRepository.findMoviesByAcceptedByAdminFalse();
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    @PatchMapping(value = "/users", params = "id")
    ResponseEntity<?> toggleUserStatus(@RequestParam int id) {
        logger.warn("[ADMIN] Changing user status!");
        userFacade.changeStatusOfUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/requests/{id}/accept")
    ResponseEntity<?> acceptMovie(@PathVariable int id) {
        logger.warn("[ADMIN] Movie with id: " + id + " has been accepted");
        movieFacade.changeStatusOfMovie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/requests/{id}/refuse")
    ResponseEntity<?> refuseMovie(@PathVariable int id) {
        logger.warn("[ADMIN] Movie with id: " + id + " has been refused");
        movieFacade.deleteMovieById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
