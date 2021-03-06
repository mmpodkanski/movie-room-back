package io.github.mmpodkanski.movieroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

@SpringBootApplication
@EnableScheduling
public class FilmroomApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilmroomApplication.class, args);
    }

    @Bean
    Validator validator() {
        return new LocalValidatorFactoryBean();
    }

}
