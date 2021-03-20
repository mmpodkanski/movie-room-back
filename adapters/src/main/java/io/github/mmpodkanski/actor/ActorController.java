package io.github.mmpodkanski.actor;

import io.github.mmpodkanski.actor.dto.ActorDto;
import io.github.mmpodkanski.exception.ApiNotFoundException;
import io.github.mmpodkanski.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/actors")
class ActorController {
    private final Logger logger = LoggerFactory.getLogger(ActorController.class);
    private final ActorFacade actorFacade;
    private final ActorQueryRepository queryRepository;

    ActorController(
            final ActorFacade actorFacade,
            final ActorQueryRepository queryRepository
    ) {
        this.actorFacade = actorFacade;
        this.queryRepository = queryRepository;
    }

    @GetMapping
    ResponseEntity<List<ActorDto>> getActors() {
        logger.info("Displaying all actors!");
        var actors = queryRepository.findAllBy();
        return new ResponseEntity<>(actors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<ActorDto> getActor(@PathVariable int id) {
        var actor = queryRepository.findDtoById(id)
                .orElseThrow(() -> new ApiNotFoundException("Actor with that id not exists!"));
        return new ResponseEntity<>(actor, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<ActorDto> addActor(
            @RequestBody @Valid ActorDto actorDto,
            @AuthenticationPrincipal User user
    ) {
        logger.warn("Adding a new actor!");
        var result = actorFacade.addActor(actorDto, user.getId());
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

//    @PutMapping("/{id}")
//    ResponseEntity<ActorDto> updateActor(
//            @RequestBody @Valid ActorDto actorDto,
//            @PathVariable int id
//    ) {
//        logger.warn("Updating an actor with id: " + actorDto.getId());
//        actorFacade.updateActor(id, actorDto);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }


}
