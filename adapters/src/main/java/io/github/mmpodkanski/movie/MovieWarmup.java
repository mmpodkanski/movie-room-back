package io.github.mmpodkanski.movie;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
class MovieWarmup implements ApplicationListener<ContextRefreshedEvent> {
    private final MovieInitializer initializer;

    MovieWarmup(final MovieRepository movieRepository, final MovieQueryRepository movieQueryRepository) {
        this.initializer = new MovieInitializer(movieRepository, movieQueryRepository);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initializer.init();
    }
}
