package io.github.mmpodkanski.movieroom.actor;

import io.github.mmpodkanski.movieroom.actor.dto.ActorRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/actors")
class ActorController {
    private final Logger logger = LoggerFactory.getLogger(ActorController.class);
    private final ActorFacade actorFacade;

    ActorController(ActorFacade actorFacade) {
        this.actorFacade = actorFacade;
    }


    @RequestMapping("/{id}")
    @GetMapping
    ResponseEntity<List<ActorRequestDto>> getActors() {
        logger.info("Displaying all actors!");
        var actors = actorFacade.readAllActors();
        return new ResponseEntity<>(actors, HttpStatus.OK);
    }


}
