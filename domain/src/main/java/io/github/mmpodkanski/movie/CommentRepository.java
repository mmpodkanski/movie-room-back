package io.github.mmpodkanski.movie;

import org.springframework.data.repository.Repository;

interface CommentRepository extends Repository<Comment, Integer> {

    Comment save(Comment entity);

    void deleteById(int id);

}
