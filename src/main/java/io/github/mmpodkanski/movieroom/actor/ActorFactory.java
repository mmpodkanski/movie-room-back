package io.github.mmpodkanski.movieroom.actor;

import io.github.mmpodkanski.movieroom.actor.dto.ActorSimpleRequestDto;
import org.springframework.stereotype.Service;

@Service
class ActorFactory {
    Actor from(final ActorSimpleRequestDto source) {
        return new Actor(source.getFirstName(), source.getLastName());
    }
}
