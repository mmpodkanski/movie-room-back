package io.github.mmpodkanski.movieroom.repository;

import io.github.mmpodkanski.movieroom.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Comment save(Comment entity);

    boolean existsByTitle(String title);

    void deleteById(int id);

    boolean existsById(int id);
}
