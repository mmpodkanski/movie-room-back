package io.github.mmpodkanski.user;

import io.github.mmpodkanski.exception.ApiBadRequestException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserFacade implements UserDetailsService {
    private final UserRepository userRepository;

    UserFacade(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    List<User> readAllUsers() {
        return userRepository.findAll();
    }

//    public boolean existsByFavourites(Movie movie) {
//        return userRepository.existsByFavourites(movie);
//    }
//
//    public boolean existsUserByFavourite(int id, Movie movie) {
//        return userRepository.existsByIdAndFavourites(id, movie);
//    }

    public User loadUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with that id not exists!"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with that username not exists!"));
    }


//    //FIXME: VERY IMPORTANT !!!!!!
//    List<Movie> readAllFavourites(int id) {
//        Set<Movie> movies = userRepository.findById(id)
//                .map(User::getFavourites)
//                .orElseThrow(() -> new ApiNotFoundException("User with that id not exists!"));
//
////        return movies.stream().map(MovieResponse::new).collect(Collectors.toList());
//        return new ArrayList<>(movies);
//    }

//    public void addFavouriteToUser(Movie movie, User user) {
//        user.addFavourite(movie);
//    }
//
//    public void removeFavouriteFromUser(Movie movie, User user) {
//        user.removeFavourite(movie);
//    }


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
