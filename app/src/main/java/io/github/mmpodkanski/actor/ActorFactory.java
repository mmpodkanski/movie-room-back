package io.github.mmpodkanski.actor;

import io.github.mmpodkanski.actor.dto.ActorDto;
import io.github.mmpodkanski.actor.dto.ActorSimpleRequestDto;
import org.springframework.stereotype.Service;

@Service
class ActorFactory {
    Actor fromSimple(final ActorSimpleRequestDto source) {
        return new Actor(source.getFirstName(), source.getLastName());
    }

    Actor from(final ActorDto source, boolean createdByAdmin) {

        var result = new Actor(source.getFirstName(), source.getLastName());
        result.setAcceptedByAdmin(createdByAdmin);
        result.setBirthDate(source.getBirthDate());
        result.setImageUrl(source.getImageUrl());

        return result;
    }
}
