package io.github.mmpodkanski.filmroom.repository.jpa;

import io.github.mmpodkanski.filmroom.models.Comment;
import io.github.mmpodkanski.filmroom.repository.CommentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface JpaCommentRepository extends CommentRepository, JpaRepository<Comment, Integer> {
}
