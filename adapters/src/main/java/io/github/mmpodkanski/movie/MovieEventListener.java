package io.github.mmpodkanski.movie;

import io.github.mmpodkanski.actor.vo.ActorEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
class MovieEventListener {
    private final MovieFacade facade;

    MovieEventListener(final MovieFacade facade) {
        this.facade = facade;
    }

    @EventListener
    public void on(ActorEvent event) {
        facade.handle(event);
    }
}