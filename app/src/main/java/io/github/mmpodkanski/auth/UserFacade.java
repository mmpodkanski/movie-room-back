package io.github.mmpodkanski.auth;

import io.github.mmpodkanski.exception.ApiBadRequestException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserFacade implements UserDetailsService{
    private final UserQueryRepository queryRepository;
    private final UserRepository userRepository;

    UserFacade(final UserQueryRepository queryRepository, final UserRepository userRepository) {
        this.queryRepository = queryRepository;
        this.userRepository = userRepository;
    }

    List<User> readAllUsers() {
        return queryRepository.findAll();
    }

//    public boolean existsByFavourites(Movie movie) {
//        return userRepository.existsByFavourites(movie);
//    }
//
//    public boolean existsUserByFavourite(int id, Movie movie) {
//        return userRepository.existsByIdAndFavourites(id, movie);
//    }

    public String loadUserNameById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with that id not exists!"))
                .getSnapshot()
                .getUsername();
    }

    public boolean checkIfAdmin(String username) {
        return queryRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with that id not exists!"))
                .getSnapshot()
                .getRole()
                .equals(ERole.ROLE_ADMIN);
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
    public void setUserRole(int userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiBadRequestException("User with that id not exists!"));

        user.setUserRole();
    }

    @Transactional
    public void setAdminRole(int userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiBadRequestException("User with that id not exists!"));

        user.setAdminRole();
    }

    @Transactional
    public void changeStatusOfUser(int userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiBadRequestException("User with that id not exists!"));

        user.toggleLocked();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        var user = queryRepository.findByUsername(s)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with that username not exists!"));

        return new UserDetailsImpl(
                user.getSnapshot().getId(),
                user.getSnapshot().getUsername(),
                user.getSnapshot().getEmail(),
                user.getSnapshot().getPassword(),
                user.getSnapshot().getRole(),
                user.getSnapshot().isLocked()
        );
    }



}
