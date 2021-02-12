package io.github.mmpodkanski.movieroom.service;

import io.github.mmpodkanski.movieroom.exception.ApiBadRequestException;
import io.github.mmpodkanski.movieroom.exception.ApiNotFoundException;
import io.github.mmpodkanski.movieroom.models.Comment;
import io.github.mmpodkanski.movieroom.models.Movie;
import io.github.mmpodkanski.movieroom.models.User;
import io.github.mmpodkanski.movieroom.models.request.CommentRequest;
import io.github.mmpodkanski.movieroom.repository.CommentRepository;
import io.github.mmpodkanski.movieroom.repository.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final MovieRepository movieRepository;
    private final UserService userService;

    CommentService(
            final CommentRepository commentRepository,
            final MovieRepository movieRepository,
            final UserService userService
    ) {
        this.commentRepository = commentRepository;
        this.movieRepository = movieRepository;
        this.userService = userService;
    }

    public void createComment(CommentRequest commentReq, int movieId) {
        if (commentRepository.existsByTitle(commentReq.getTitle())) {
            throw new ApiBadRequestException("Comment with that title already exists!");
        }
        var movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ApiNotFoundException("Movie with that id not exists!"));

        User owner = userService.loadUserById(commentReq.getOwnerId());
        var commentToSave = mapCommentDTO(commentReq, movie, owner);

        commentRepository.save(commentToSave);
    }

    public void removeComment(int idComment) {
        if (!commentRepository.existsById(idComment)) {
            throw new ApiBadRequestException("Comment with that id not exists!");
        }
        commentRepository.deleteById(idComment);
    }

    private Comment mapCommentDTO(CommentRequest commentRequest, Movie movieToUpdate, User owner) {
        var modelMapper = new ModelMapper();
        var createdAt = OffsetDateTime.now();

        Comment comment = modelMapper.map(commentRequest, Comment.class);

        comment.setCreatedAt(createdAt);
        comment.setAuthor(owner.getUsername());
        comment.setOwner(owner);
        comment.setMovie(movieToUpdate);
        return comment;
    }
}
