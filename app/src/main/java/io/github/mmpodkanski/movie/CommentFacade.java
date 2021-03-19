package io.github.mmpodkanski.movie;

import io.github.mmpodkanski.exception.ApiBadRequestException;
import io.github.mmpodkanski.movie.dto.CommentRequestDto;
import io.github.mmpodkanski.user.User;
import io.github.mmpodkanski.user.UserFacade;
import org.springframework.stereotype.Service;

@Service
class CommentFacade {
    private final CommentRepository commentRepository;
    private final UserFacade userFacade;
    private final CommentFactory commentFactory;
    private final CommentQueryRepository queryRepository;

    CommentFacade(
            final CommentRepository commentRepository,
            final UserFacade userFacade,
            final CommentFactory commentFactory,
            final CommentQueryRepository queryRepository
    ) {
        this.commentRepository = commentRepository;
        this.userFacade = userFacade;
        this.commentFactory = commentFactory;
        this.queryRepository = queryRepository;
    }

    Comment createComment(CommentRequestDto commentReq, Movie movie) {
        if (queryRepository.existsByTitle(commentReq.getTitle())) {
            throw new ApiBadRequestException("Comment with that title already exists!");
        }
        User owner = userFacade.loadUserById(commentReq.getOwnerId());
        var comment = commentFactory.mapCommentDTO(commentReq, movie, owner);

        return commentRepository.save(comment);
    }

    void deleteComment(int commentId) {
        commentRepository.deleteById(commentId);
    }

}
