//package io.github.mmpodkanski.movieroom.user;
//
//import io.github.mmpodkanski.movieroom.movie.MovieResponseDtoCopy;
//import io.github.mmpodkanski.movieroom.movie.MovieService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/admin/board")
//@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
//@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600)
//class AdminBoardController {
//    private final Logger logger = LoggerFactory.getLogger(AdminBoardController.class);
//    private final MovieService movieService;
//    private final UserService userService;
//
//    AdminBoardController(
//            final MovieService movieService,
//            final UserService userService
//    ) {
//        this.movieService = movieService;
//        this.userService = userService;
//    }
//
//    @GetMapping("/users")
//    ResponseEntity<List<User>> getAllUsers() {
//        logger.warn("[ADMIN] Displaying all users!");
//        var userList = userService.readAllUsers();
//        return new ResponseEntity<>(userList, HttpStatus.OK);
//    }
//
//    @GetMapping("/requests")
//    ResponseEntity<List<MovieResponseDtoCopy>> getAllMoviesToAccept() {
//        logger.info("[ADMIN] Exposing all the movies to accept!");
//        List<MovieResponseDtoCopy> movieList = movieService.readAllMoviesToAccept();
//        return new ResponseEntity<>(movieList, HttpStatus.OK);
//    }
//
//    @PatchMapping(value = "/users", params = "id")
//    ResponseEntity<?> toggleUserStatus(@RequestParam int id) {
//        logger.warn("[ADMIN] Changing user status!");
//        userService.changeStatusOfUser(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @PatchMapping("/requests/{id}/accept")
//    ResponseEntity<?> acceptMovie(@PathVariable int id) {
//        logger.warn("[ADMIN] Movie with id: " + id + " has been accepted");
//        movieService.changeStatusOfMovie(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @PatchMapping("/requests/{id}/refuse")
//    ResponseEntity<?> refuseMovie(@PathVariable int id) {
//        logger.warn("[ADMIN] Movie with id: " + id + " has been refused");
//        movieService.deleteMovieById(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//}
