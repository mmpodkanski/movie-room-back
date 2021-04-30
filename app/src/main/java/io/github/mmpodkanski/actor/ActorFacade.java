package io.github.mmpodkanski.actor;

import io.github.mmpodkanski.actor.dto.*;
import io.github.mmpodkanski.exception.ApiBadRequestException;
import io.github.mmpodkanski.user.UserFacade;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ActorFacade {
    private final ActorQueryRepository queryRepository;
    private final ActorRepository repository;
    private final ActorFactory factory;
    private final UserFacade userFacade;

    ActorFacade(
            final ActorRepository repository,
            final ActorFactory factory,
            final ActorQueryRepository queryRepository,
            final UserFacade userFacade
    ) {
        this.repository = repository;
        this.factory = factory;
        this.queryRepository = queryRepository;
        this.userFacade = userFacade;
    }

    //FIXME: ADD ACTOR
    ActorDto addActor(ActorDto newActor, String username) {
        if (queryRepository.existsActorByFirstNameAndLastName(newActor.getFirstName(), newActor.getLastName())) {
            throw new ApiBadRequestException("Actor with that name already exists!");
        }

        boolean createdByAdmin = userFacade.checkIfAdmin(username);

        var actor = factory.from(newActor, createdByAdmin);
        return toDto(repository.save(actor));
    }

    public Set<SimpleActor> addSimpleActors(Set<ActorSimpleRequestDto> actors, boolean createdByAdmin) {
        return actors.stream().map(actorDto -> {
                    var actor = regexActor(actorDto);
                    var snapshot = queryRepository.findByFirstNameAndLastName(actor.getFirstName(), actor.getLastName())
                            .orElseGet(() -> repository.save(new Actor(actor.getFirstName(), actor.getLastName(), createdByAdmin)).getSnapshot());


                    return SimpleActor.restore(new SimpleActorSnapshot(snapshot.getId(), snapshot.getFirstName(), snapshot.getLastName()));
                }
        ).collect(Collectors.toSet());
    }

//    @Transactional
//    public void updateActor(int actorId, ActorDto actor) {
//        var actorToUpdate = repository.findById(actorId)
//                .orElseThrow(() -> new ApiNotFoundException("Actor with that id not exists!"));
//
//        update(actorToUpdate, actor);
//    }


    private ActorSimpleRequestDto regexActor(ActorSimpleRequestDto actorSimpleRequestDTO) {
        var firstName = actorSimpleRequestDTO.getFirstName();
        var lastName = actorSimpleRequestDTO.getLastName();

        var newFirstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        var newLastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();

        actorSimpleRequestDTO.update(newFirstName, newLastName);
        return actorSimpleRequestDTO;
    }

    public ActorSimpleResponseDto toSimpleDto(SimpleActor actor) {
        var snap = actor.getSnapshot();
        return ActorSimpleResponseDto.create(snap.getId(), snap.getFirstName(), snap.getLastName());
    }

    private ActorDto toDto(Actor actor) {
        var snap = actor.getSnapshot();
        return ActorDto.builder()
                .withId(snap.getId())
                .withFirstName(snap.getFirstName())
                .withLastName(snap.getLastName())
                .withBirthDate(snap.getBirthDate())
                .withImageUrl(snap.getImageUrl())
                .build();
    }

//    private void update(Actor actorToUpdate, ActorDto actor) {
//        actorToUpdate.setFirstName(actor.getFirstName());
//        actorToUpdate.setLastName(actor.getLastName());
//        actorToUpdate.setBirthDate(actor.getBirthDate());
//        actorToUpdate.setImageUrl(actor.getImageUrl());
//    }

}
