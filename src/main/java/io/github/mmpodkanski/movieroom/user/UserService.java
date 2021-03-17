package io.github.mmpodkanski.movieroom.user;

import io.github.mmpodkanski.movieroom.exception.ApiBadRequestException;
import io.github.mmpodkanski.movieroom.exception.ApiNotFoundException;
import io.github.mmpodkanski.movieroom.movie.dto.MovieQueryDtoEntity;
import io.github.mmpodkanski.movieroom.security.LoginRequest;
import io.github.mmpodkanski.movieroom.security.RegisterRequest;
import io.github.mmpodkanski.movieroom.security.jwt.JwtResponse;
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
import org.springframework.transaction.annotation.Transactional;

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

    List<User> readAllUsers() {
        return userRepository.findAll();
    }

    public boolean existsByFavourites(MovieQueryDtoEntity movie) {
        return userRepository.existsByFavourites(movie);
    }

    public boolean existsUserByFavourite(int id, MovieQueryDtoEntity movie) {
        return userRepository.existsByIdAndFavourites(id, movie);
    }

    public User loadUserById(int id) {
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

        var user = new User(registerRequest.getUsername(),
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

    //FIXME: VERY IMPORTANT !!!!!!
    List<MovieQueryDtoEntity> readAllFavourites(int id) {
        Set<MovieQueryDtoEntity> movies = userRepository.findById(id)
                .map(User::getFavourites)
                .orElseThrow(() -> new ApiNotFoundException("User with that id not exists!"));

//        return movies.stream().map(MovieResponse::new).collect(Collectors.toList());
        return new ArrayList<>(movies);
    }

    public void addFavouriteToUser(MovieQueryDtoEntity movieQueryDtoEntity, User user) {
        user.addFavourite(movieQueryDtoEntity);
    }

    public void removeFavouriteFromUser(MovieQueryDtoEntity movieQueryDtoEntity, User user) {
        user.removeFavourite(movieQueryDtoEntity);
    }

    @Transactional
    public void setAdminRole(int userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiBadRequestException("User with that id not exists!"));

        user.setRole(ERole.ROLE_ADMIN);
    }

    @Transactional
    public void changeStatusOfUser(int userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiBadRequestException("User with that id not exists!"));

        user.setLocked(!user.isLocked());
    }
}
