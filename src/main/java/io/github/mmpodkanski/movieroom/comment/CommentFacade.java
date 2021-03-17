package io.github.mmpodkanski.movieroom.comment;

import io.github.mmpodkanski.movieroom.comment.dto.CommentRequestDto;
import io.github.mmpodkanski.movieroom.exception.ApiBadRequestException;
import io.github.mmpodkanski.movieroom.movie.dto.MovieQueryDtoEntity;
import io.github.mmpodkanski.movieroom.user.User;
import io.github.mmpodkanski.movieroom.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class CommentFacade {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final CommentFactory commentFactory;
    private final CommentQueryRepository queryRepository;

    CommentFacade(
            final CommentRepository commentRepository,
            final UserService userService,
            final CommentFactory commentFactory,
            final CommentQueryRepository queryRepository
    ) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.commentFactory = commentFactory;
        this.queryRepository = queryRepository;
    }

    public Comment createComment(CommentRequestDto commentReq, MovieQueryDtoEntity movie) {
        if (queryRepository.existsByTitle(commentReq.getTitle())) {
            throw new ApiBadRequestException("Comment with that title already exists!");
        }
        User owner = userService.loadUserById(commentReq.getOwnerId());
        var comment = commentFactory.mapCommentDTO(commentReq, movie, owner);

        return commentRepository.save(comment);
    }

    public void deleteComment(int commentId) {
        commentRepository.deleteById(commentId);
    }

}
