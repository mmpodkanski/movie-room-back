package io.github.mmpodkanski.movie;

interface CommentRepository {

    Comment save(Comment entity);

    void deleteById(int id);

}
