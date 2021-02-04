package io.github.mmpodkanski.movieroom.service;

import io.github.mmpodkanski.movieroom.models.Actor;
import io.github.mmpodkanski.movieroom.models.Movie;
import io.github.mmpodkanski.movieroom.repository.ActorRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
class ActorService {
    private final ActorRepository repository;

    ActorService(final ActorRepository repository) {
        this.repository = repository;
    }

//    public Set<Actor> returnActors(Set<ActorWriteModel> actorsToCheck) {
//        Set<Actor> updatedActors = new HashSet<>();
//        for (ActorWriteModel actor : actorsToCheck) {
//            updatedActors.add(returnExistsOrCreateNew(actor));
//        }
//        return updatedActors;
//    }


    public Set<Actor> checkActors(Set<String> actors, Movie movie) {
        Set<Actor> result = new HashSet<>();
        for (String actor : actors) {
            result.add(returnExistsOrCreateNew(actor, movie));
        }
        return result;
    }


    public Actor returnExistsOrCreateNew(String name, Movie movie) {
        List<String> identity = formatNameToTwoStrings(name);
        String firstName = identity.get(0);
        String lastName = identity.get(1);

        Actor actor = repository
                .findByFirstNameAndLastName(firstName, lastName)
                .orElseGet(() -> {
                    var newActor = new Actor(firstName, lastName);
                    return repository.save(newActor);
                });

        actor.addMovie(movie);

        return actor;

    }

    List<String> formatNameToTwoStrings(String name) {
        // TODO: extract to method
        if(!name.matches("\\w+\\s\\w+")) {
            throw new IllegalStateException("Bad format of name (example:Johny Depp)!");
        }
        String[] parts = name.split(" ", 2);
        String firstName = parts[0];
        String lastName = parts[1];
        return List.of(firstName, lastName);
    }
}
