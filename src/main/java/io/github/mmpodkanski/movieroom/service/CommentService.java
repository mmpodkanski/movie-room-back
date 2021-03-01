package io.github.mmpodkanski.movieroom.service;

import io.github.mmpodkanski.movieroom.exception.ApiBadRequestException;
import io.github.mmpodkanski.movieroom.models.Comment;
import io.github.mmpodkanski.movieroom.models.Movie;
import io.github.mmpodkanski.movieroom.models.User;
import io.github.mmpodkanski.movieroom.models.request.CommentRequest;
import io.github.mmpodkanski.movieroom.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;

    CommentService(
            final CommentRepository commentRepository,
            final UserService userService
    ) {
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    public Comment createComment(CommentRequest commentReq, Movie movie) {
        if (commentRepository.existsByTitle(commentReq.getTitle())) {
            throw new ApiBadRequestException("Comment with that title already exists!");
        }
        User owner = userService.loadUserById(commentReq.getOwnerId());

        return mapCommentDTO(commentReq, movie, owner);
    }

    public Comment findCommentById(int idComment) {
        return commentRepository.findById(idComment)
                .orElseThrow(() -> new ApiBadRequestException("Comment with that id not exists!"));
    }

    private Comment mapCommentDTO(CommentRequest commentRequest, Movie movie, User owner) {
        var createdAt = OffsetDateTime.now();
        var result = new Comment();

        result.setTitle(commentRequest.getTitle());
        result.setDescription(commentRequest.getDescription());
        result.setCreatedAt(createdAt);
        result.setAuthor(owner.getUsername());
        result.setOwner(owner);
        result.setMovie(movie);

        return result;
    }
}
