package io.github.mmpodkanski.movieroom.publicApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")
class PublicApiController {
    private final Logger logger = LoggerFactory.getLogger(PublicApiController.class);
    private final PublicApiService service;

    PublicApiController(PublicApiService service) {
        this.service = service;
    }

    @GetMapping("/news")
    ResponseEntity<News> getNews() throws IOException, InterruptedException {
        logger.info("Displaying news!");
        var news = service.showNews();
        return new ResponseEntity<>(news, HttpStatus.OK);
    }

    @GetMapping("/schedule")
    ResponseEntity<String> getSchedule() {
        logger.info("Displaying schedule of movies!");
        var schedule = service.showScheduleOfMovies();
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    @GetMapping(value = "/actors", params = "name")
    ResponseEntity<String> getActor(@RequestParam @NotBlank String name) {
        logger.info("Searching actor with params:" + name);
        var actor = service.searchActorByName(name);
        return new ResponseEntity<>(actor, HttpStatus.OK);
    }

}
