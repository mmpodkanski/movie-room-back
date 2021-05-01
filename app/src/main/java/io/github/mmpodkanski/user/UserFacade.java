package io.github.mmpodkanski.user;

import io.github.mmpodkanski.exception.ApiNotFoundException;
import io.github.mmpodkanski.movie.dto.MovieResponseDto;
import io.github.mmpodkanski.movie.vo.MovieEvent;
import io.github.mmpodkanski.movie.vo.UserSourceId;
import io.github.mmpodkanski.user.dto.UserDto;
import io.github.mmpodkanski.user.dto.UserMovieDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserFacade implements UserDetailsService {
    private final UserQueryRepository queryRepository;
    private final UserRepository userRepository;
    private final UserMovieRepository favRepository;

    UserFacade(
            final UserQueryRepository queryRepository,
            final UserRepository userRepository,
            final UserMovieRepository favRepository
    ) {
        this.queryRepository = queryRepository;
        this.userRepository = userRepository;
        this.favRepository = favRepository;
    }

    void handle(MovieEvent event) {
        event.getSourceId()
                .map(UserSourceId::getId)
                .map(Integer::parseInt)
                .ifPresent(userId -> {
                    var data = event.getData();
                    var movie = new UserMovie(
                            data.getId(),
                            data.getTitle(),
                            data.getReleaseDate(),
                            data.getCategory(),
                            data.getStars(),
                            data.isAcceptedByAdmin(),
                            data.getImgLogoUrl()
                    );

                    switch (event.getState()) {
                        case ADDED:
                            addMovieToFav(userId, movie);
                            break;

                        case REMOVED:
                            removeMovieFromFav(userId, movie.getId());
                            break;
                    }
                });
    }

    private User findUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiNotFoundException("User with that id not exists!"));
    }

    public void addMovieToFav(int userId, UserMovie movie) {
        var user = findUserById(userId);

        user.addFavourite(movie);
        userRepository.save(user);
    }

    public void removeFavouriteFromAllUsers(int id) {
        var fav = favRepository.findById(id)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists"));

        var users = userRepository.findAllByFavouritesContaining(fav);
        users.forEach(user -> user.removeFavourite(fav));
    }

    public void removeMovieFromFav(int userId, int movieId) {
        var user = findUserById(userId);
        var movie = favRepository.findById(movieId)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));


        user.removeFavourite(movie);
        userRepository.save(user);
    }

    public List<UserDto> readAllUsers() {
        return queryRepository.findAllBy();
    }

    public boolean existsByFavourite(String username, MovieResponseDto movie) {
        int userId = loadIdByUsername(username);
        var fav = new UserMovie(
                movie.getId(),
                movie.getTitle(),
                movie.getReleaseDate(),
                movie.getCategory(),
                movie.getStars(),
                movie.isAcceptedByAdmin(),
                movie.getImgLogoUrl()
        );

        return queryRepository.existsByIdAndFavourites(userId, fav);
    }
//
//    public boolean existsUserByFavourite(int id, Movie movie) {
//        return userRepository.existsByIdAndFavourites(id, movie);
//    }

    public String loadUserNameById(int id) {
        return findUserById(id)
                .getSnapshot()
                .getUsername();
    }

    // FIXME: !!!!! DELETE THIS AND FIX MOVIE CONTROLLER READING CURRENT USER !!!!!
    public int loadIdByUsername(String name) {
        return queryRepository.findByUsername(name)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with that username not exists!"))
                .getId();
    }


    public boolean checkIfAdmin(String username) {
        return queryRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with that id not exists!"))
                .getRole()
                .equals(ERole.ROLE_ADMIN.toString());
    }

    public List<UserMovieDto> readAllFavourites(int id) {
        return findUserById(id).getSnapshot().getFavourites()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    @Transactional
    public void setUserRole(int userId) {
        var user = findUserById(userId);

        user.setUserRole();
    }

    @Transactional
    public void setAdminRole(int userId) {
        var user = findUserById(userId);

        user.setAdminRole();
    }

    public void changeStatusOfUser(int userId) {
        var user = findUserById(userId);

        user.toggleLocked();
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        var user = queryRepository.findByUsername(s)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with that username not exists!"));

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                ERole.valueOf(user.getRole()),
                user.isLocked()
        );
    }

    UserMovieDto toDto(UserMovie movie) {
        return new UserMovieDto(
                movie.getId(),
                movie.getTitle(),
                movie.getReleaseDate(),
                movie.getCategory(),
                movie.getStars(),
                movie.isAcceptedByAdmin(),
                movie.getImgLogoUrl()
        );
    }


}
