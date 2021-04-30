package io.github.mmpodkanski.actor;

import io.github.mmpodkanski.actor.dto.ActorDto;
import org.springframework.stereotype.Service;

@Service
class ActorFactory {
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
