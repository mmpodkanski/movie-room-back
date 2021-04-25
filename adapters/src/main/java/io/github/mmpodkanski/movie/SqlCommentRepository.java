package io.github.mmpodkanski.movie;

import org.springframework.data.repository.Repository;

interface SqlCommentRepository extends Repository<CommentSnapshot, Integer> {
    <S extends CommentSnapshot> S save(S entity);

    void deleteById(int id);
}

interface SqlCommentQueryRepository extends CommentQueryRepository, Repository<CommentSnapshot, Integer> {
}

@org.springframework.stereotype.Repository
class CommentRepositoryImpl implements CommentRepository {
    private final SqlCommentRepository sqlRepository;

    CommentRepositoryImpl(final SqlCommentRepository sqlRepository) {
        this.sqlRepository = sqlRepository;
    }

    @Override
    public Comment save(final Comment entity) {
        return Comment.restore(sqlRepository.save(entity.getSnapshot()));
    }

    @Override
    public void deleteById(final int id) {
        sqlRepository.deleteById(id);
    }
}
