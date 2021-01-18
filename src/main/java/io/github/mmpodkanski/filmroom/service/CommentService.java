package io.github.mmpodkanski.filmroom.service;

import io.github.mmpodkanski.filmroom.models.Comment;
import io.github.mmpodkanski.filmroom.models.Movie;
import io.github.mmpodkanski.filmroom.models.User;
import io.github.mmpodkanski.filmroom.models.request.CommentDTO;
import io.github.mmpodkanski.filmroom.repository.CommentRepository;
import io.github.mmpodkanski.filmroom.repository.MovieRepository;
import io.github.mmpodkanski.filmroom.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository repository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    CommentService(final CommentRepository repository, final MovieRepository movieRepository, final UserRepository userRepository) {
        this.repository = repository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    public void createComment(CommentDTO newComment, int movieId) {
        if (repository.existsByTitle(newComment.getTitle())) {
            throw new IllegalStateException("Error: Comment with that title already exists!");
        }

        // FIXME: check if frontend give movie or movieId
        Movie movieToUpdate = movieRepository
                .findById(movieId)
                .orElseThrow(() -> new IllegalStateException("Movie with that id not exists!"));
        User owner = userRepository
                .findById(newComment.getOwnerId())
                .orElseThrow(() -> new IllegalStateException("User with that id not exists!"));
//        var movieToUpdate = newComment.getMovie();


        var comment = new Comment();
        comment.setTitle(newComment.getTitle());
        comment.setDescription(newComment.getDescription());
        comment.setAuthor(owner.getUsername());
        comment.setOwner(owner);
        comment.setMovie(movieToUpdate);

        movieToUpdate.getComments().add(comment);

        movieRepository.save(movieToUpdate);
        repository.save(comment);
    }

    public void removeComment(int idComment) {
        repository.deleteById(idComment);
    }
}
