package io.github.mmpodkanski.filmroom.repository;

import io.github.mmpodkanski.filmroom.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Comment save(Comment entity);

    boolean existsByTitle(String title);

    void deleteById(int id);

    boolean existsById(int id);
}
