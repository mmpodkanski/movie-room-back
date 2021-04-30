package io.github.mmpodkanski.user;

import io.github.mmpodkanski.movie.vo.MovieEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
class UserEventListener {
    private final UserFacade facade;

    UserEventListener(final UserFacade facade) {
        this.facade = facade;
    }

    @EventListener
    public void on(MovieEvent event) {
        facade.handle(event);
    }
}
