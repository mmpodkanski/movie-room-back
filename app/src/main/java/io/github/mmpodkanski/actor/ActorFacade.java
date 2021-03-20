package io.github.mmpodkanski.actor;

import io.github.mmpodkanski.actor.dto.ActorDto;
import io.github.mmpodkanski.actor.dto.ActorSimpleRequestDto;
import io.github.mmpodkanski.exception.ApiBadRequestException;
import io.github.mmpodkanski.user.ERole;
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

    ActorDto addActor(ActorDto newActor, int userId) {
        if (queryRepository.existsActorByFirstNameAndLastName(newActor.getFirstName(), newActor.getLastName())) {
            throw new ApiBadRequestException("Actor with that name already exists!");
        }

        boolean createdByAdmin = userFacade
                .loadUserById(userId)
                .getRole()
                .equals(ERole.ROLE_ADMIN);

        var actor = factory.from(newActor, createdByAdmin);
        return toDto(repository.save(actor));
    }

    public Set<Actor> addSimpleActors(Set<ActorSimpleRequestDto> actors) {
        return actors.stream().map(actorDto -> {
                    var actor = regexActor(actorDto);
                    return repository.findByFirstNameAndLastName(actor.getFirstName(), actor.getLastName())
                            .orElseGet(() -> new Actor(actor.getFirstName(), actor.getLastName()));
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


    public void deleteActorsFromExistingMovie(Set<Actor> actors) {
        actors.stream()
                .filter(actor -> actor.getSnapshot().getMovies().size() <= 1)
                .forEach(repository::delete);
    }

    private ActorSimpleRequestDto regexActor(ActorSimpleRequestDto actorSimpleRequestDTO) {
        var firstName = actorSimpleRequestDTO.getFirstName();
        var lastName = actorSimpleRequestDTO.getLastName();

        var newFirstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        var newLastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();

        actorSimpleRequestDTO.update(newFirstName, newLastName);
        return actorSimpleRequestDTO;
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
