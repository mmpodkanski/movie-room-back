package io.github.mmpodkanski.movieroom.service;

import io.github.mmpodkanski.movieroom.exception.ApiRequestException;
import io.github.mmpodkanski.movieroom.models.Movie;
import io.github.mmpodkanski.movieroom.models.User;
import io.github.mmpodkanski.movieroom.models.response.MovieReadModel;
import io.github.mmpodkanski.movieroom.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<MovieReadModel> readAllFavourites(int id) {
        List<MovieReadModel> favouriteMovies = new ArrayList<>();

        Set<Movie> movies = userRepository.findById(id)
                .map(User::getFavourites)
                .orElseThrow(() -> new ApiRequestException("User with that id not exists!"));

        movies.forEach(source -> favouriteMovies.add(new MovieReadModel(source)));

        return favouriteMovies;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User Not Found with username: " + username));
    }

}
