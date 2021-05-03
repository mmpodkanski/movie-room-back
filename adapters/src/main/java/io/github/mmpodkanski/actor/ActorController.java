package io.github.mmpodkanski.actor;

import io.github.mmpodkanski.actor.dto.ActorDto;
import io.github.mmpodkanski.exception.ApiNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/actors")
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600)
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
        logger.info("Displaying an actor with id: " + id);
        var actor = queryRepository.findDtoById(id)
                .orElseThrow(() -> new ApiNotFoundException("Actor with that id not exists!"));
        return new ResponseEntity<>(actor, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    ResponseEntity<ActorDto> addActor(
            @RequestBody ActorDto actorDto
    ) {
        logger.warn("Adding a new actor!");
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        var result = actorFacade.addActor(actorDto, currentUser.getName());
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    ResponseEntity<ActorDto> updateActor(
            @RequestBody ActorDto actorDto,
            @PathVariable int id
    ) {
        logger.warn("Updating an actor with id: " + actorDto.getId());
        var result = actorFacade.updateActor(id, actorDto);
        return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteActor(@PathVariable int id) {
        logger.warn("Removing an actor with id: " + id);
        actorFacade.removeActorById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
