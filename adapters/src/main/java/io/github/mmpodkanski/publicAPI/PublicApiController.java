package io.github.mmpodkanski.publicAPI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.io.IOException;

@RestController
@RequestMapping("/api")
class PublicApiController {
    private final Logger logger = LoggerFactory.getLogger(PublicApiController.class);
    private final PublicApiFacade facade;

    PublicApiController(final PublicApiFacade facade) {
        this.facade = facade;
    }

    @GetMapping("/news")
    ResponseEntity<News> getNews() throws IOException, InterruptedException {
        logger.info("Displaying news!");
        var news = facade.showNews();
        return new ResponseEntity<>(news, HttpStatus.OK);
    }

    @GetMapping("/schedule")
    ResponseEntity<String> getSchedule() {
        logger.info("Displaying schedule of movies!");
        var schedule = facade.showScheduleOfMovies();
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    @GetMapping(value = "/actors", params = "name")
    ResponseEntity<String> getActor(@RequestParam @NotBlank String name) {
        logger.info("Searching actor with params:" + name);
        var actor = facade.searchActorByName(name);
        return new ResponseEntity<>(actor, HttpStatus.OK);
    }

}
