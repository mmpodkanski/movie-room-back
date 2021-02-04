package io.github.mmpodkanski.movieroom.controller;

import io.github.mmpodkanski.movieroom.service.PublicApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")
public class PublicApiController {
    private final Logger logger = LoggerFactory.getLogger(PublicApiController.class);
    private final PublicApiService service;

    PublicApiController(PublicApiService service) {
        this.service = service;
    }

    @GetMapping("/news")
    @Scheduled(fixedDelay = 180000)
    ResponseEntity<String> getNews() throws IOException, InterruptedException {
        logger.info("Displaying news!");
        return ResponseEntity.ok(service.showNews());
    }

    @GetMapping("/schedule")
    ResponseEntity<String> getSchedule() {
        logger.info("Displaying schedule of movies!");
        return ResponseEntity.ok(service.showScheduleOfMovies());
    }

    @GetMapping(value = "/actors", params = "name")
    ResponseEntity<String> getActor(@RequestParam @NotBlank String name) {
        logger.info("Searching actor with params:" + name);
        return ResponseEntity.ok(service.searchActorByName(name));
    }

}
