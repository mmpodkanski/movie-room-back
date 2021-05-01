package io.github.mmpodkanski.user;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

interface SqlUserRepository extends Repository<UserSnapshot, Integer> {
    Optional<UserSnapshot> findById(int id);

    List<UserSnapshot> findAllByFavouritesContaining(UserMovie userMovie);

    UserSnapshot save(UserSnapshot entity);

    void delete(UserSnapshot entity);
}

interface SqlUserQueryRepository extends UserQueryRepository, Repository<UserSnapshot, Integer> {
}

@org.springframework.stereotype.Repository
class UserRepositoryImpl implements UserRepository {
    private final SqlUserRepository sqlUserRepository;

    UserRepositoryImpl(final SqlUserRepository sqlUserRepository) {
        this.sqlUserRepository = sqlUserRepository;
    }

    @Override
    public List<User> findAllByFavouritesContaining(UserMovie userMovie) {
        return sqlUserRepository.findAllByFavouritesContaining(userMovie).stream().map(User::restore).collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(int id) {
        return sqlUserRepository.findById(id).map(User::restore);
    }

    @Override
    public User save(User entity) {
        return User.restore(sqlUserRepository.save(entity.getSnapshot()));
    }

    @Override
    public void delete(User entity) {
        sqlUserRepository.delete(entity.getSnapshot());
    }


}
