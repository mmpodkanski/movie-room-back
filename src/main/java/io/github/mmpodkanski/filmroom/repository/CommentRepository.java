package io.github.mmpodkanski.filmroom.repository;

import io.github.mmpodkanski.filmroom.models.Comment;

public interface CommentRepository {
    Comment save(Comment entity);

    boolean existsByTitle(String title);

    void deleteById(int id);
}
