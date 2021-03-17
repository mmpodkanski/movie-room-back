package io.github.mmpodkanski.movieroom.comment;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface CommentRepository extends Repository<Comment, Integer> {
    Optional<Comment> findById(int id);

    Comment save(Comment entity);

    void deleteById(int id);
}
