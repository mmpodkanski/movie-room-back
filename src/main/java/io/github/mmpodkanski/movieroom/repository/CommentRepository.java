package io.github.mmpodkanski.movieroom.repository;

import io.github.mmpodkanski.movieroom.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    boolean existsById(int id);

    boolean existsByTitle(String title);

    Comment save(Comment entity);

    void deleteById(int id);

}
