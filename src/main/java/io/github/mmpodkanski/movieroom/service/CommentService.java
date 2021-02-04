package io.github.mmpodkanski.movieroom.service;

import io.github.mmpodkanski.movieroom.models.Comment;
import io.github.mmpodkanski.movieroom.models.Movie;
import io.github.mmpodkanski.movieroom.models.User;
import io.github.mmpodkanski.movieroom.models.request.CommentDTO;
import io.github.mmpodkanski.movieroom.repository.CommentRepository;
import io.github.mmpodkanski.movieroom.repository.MovieRepository;
import io.github.mmpodkanski.movieroom.repository.UserRepository;
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

        var comment = new Comment(
                newComment.getTitle(),
                newComment.getDescription(),
                owner.getUsername(),
                owner,
                movieToUpdate);

        movieToUpdate.getComments().add(comment);
        movieRepository.save(movieToUpdate);
    }

    public void removeComment(int idComment) {
        if (!repository.existsById(idComment)) {
            throw new IllegalStateException("Comment with that id not exists!");
        }
        repository.deleteById(idComment);
    }
}
