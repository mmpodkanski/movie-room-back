package io.github.mmpodkanski.movieroom.service;

import io.github.mmpodkanski.movieroom.exception.ApiBadRequestException;
import io.github.mmpodkanski.movieroom.models.Actor;
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

    public Set<Actor> checkActors(Set<String> actors) {
        Set<Actor> result = new HashSet<>();
        for (String actor : actors) {
            result.add(returnExistsOrCreateNew(actor));
        }

        return result;
    }

    public Actor returnExistsOrCreateNew(String name) {
        List<String> identity = formatNameToTwoStrings(name);
        String firstName = identity.get(0);
        String lastName = identity.get(1);

        return repository
                .findByFirstNameAndLastName(firstName, lastName)
                .orElseGet(() -> new Actor(firstName, lastName));
    }

    List<String> formatNameToTwoStrings(String name) {
        // TODO: extract to method
        if(!name.matches("\\w+\\s\\w+")) {
            throw new ApiBadRequestException("Bad format of name (example:Johny Depp)!");
        }

        String[] parts = name.split(" ", 2);
        String firstName = parts[0];
        String lastName = parts[1];

        return List.of(firstName, lastName);
    }
}
