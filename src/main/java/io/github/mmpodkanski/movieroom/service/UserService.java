package io.github.mmpodkanski.movieroom.service;

import io.github.mmpodkanski.movieroom.exception.ApiBadRequestException;
import io.github.mmpodkanski.movieroom.models.ERole;
import io.github.mmpodkanski.movieroom.models.Movie;
import io.github.mmpodkanski.movieroom.models.User;
import io.github.mmpodkanski.movieroom.models.request.LoginRequest;
import io.github.mmpodkanski.movieroom.models.request.RegisterRequest;
import io.github.mmpodkanski.movieroom.models.response.JwtResponse;
import io.github.mmpodkanski.movieroom.models.response.MovieResponse;
import io.github.mmpodkanski.movieroom.repository.UserRepository;
import io.github.mmpodkanski.movieroom.security.jwt.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    UserService(
            final AuthenticationManager authenticationManager,
            final UserRepository userRepository,
            final PasswordEncoder encoder,
            final JwtUtils jwtUtils
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;

    }

    public boolean existsByFavourites(Movie movie) {
        return userRepository.existsByFavourites(movie);
    }

    public User loadUserById(int id){
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User with that id not exists!"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User with that username not exists!"));
    }

    public void signup(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new ApiBadRequestException("Username is already taken!");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ApiBadRequestException("Email is already in use!");
        }

        User user = new User(registerRequest.getUsername(),
                registerRequest.getEmail(),
                encoder.encode(registerRequest.getPassword()));

        user.setRole(ERole.ROLE_USER);
        userRepository.save(user);
    }

    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtAccessToken = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();
        String role = userDetails.getAuthorities().toString();

        return new JwtResponse(
                userDetails.getId(),
                jwtAccessToken,
                userDetails.getUsername(),
                userDetails.getEmail(),
                role);
    }

    public List<MovieResponse> readAllFavourites(int id) {
        List<MovieResponse> favouriteMovies = new ArrayList<>();

        Set<Movie> movies = userRepository.findById(id)
                .map(User::getFavourites)
                .orElseThrow(() -> new ApiBadRequestException("User with that id not exists!"));

        movies.forEach(source -> favouriteMovies.add(new MovieResponse(source)));

        return favouriteMovies;
    }

    public void addAdminRole(int userId, String key) {
        User userDetails = userRepository
                .findById(userId)
                .orElseThrow(() -> new ApiBadRequestException("User with that id not found!"));

        if (!key.equals("ADMIN")) {
            throw new ApiBadRequestException("Incorrect key!");
        }

        userDetails.setRole(ERole.ROLE_ADMIN);
        userRepository.save(userDetails);
    }
}
