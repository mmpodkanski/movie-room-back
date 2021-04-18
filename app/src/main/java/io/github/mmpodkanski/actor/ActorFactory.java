package io.github.mmpodkanski.actor;

import io.github.mmpodkanski.actor.dto.ActorDto;
import io.github.mmpodkanski.actor.dto.ActorSimpleRequestDto;
import org.springframework.stereotype.Service;

@Service
class ActorFactory {
    // FIXME: REMOVE IT OR REPLACE TO SNAPSHOT
    Actor fromSimple(final ActorSimpleRequestDto source) {
        return new Actor(source.getFirstName(), source.getLastName());
    }

    Actor from(final ActorDto source, boolean createdByAdmin) {
        return Actor.restore(new ActorSnapshot(
                source.getId(),
                source.getFirstName(),
                source.getLastName(),
                source.getBirthDate(),
                source.getImageUrl(),
                createdByAdmin
        ));
    }
}
