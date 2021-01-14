package io.github.mmpodkanski.filmroom.repository;

import io.github.mmpodkanski.filmroom.entity.Award;

import java.util.List;
import java.util.Optional;

public interface AwardRepository {
    List<Award> findAll();

    Optional<Award> findById(int id);

    Optional<Award> findByName(String name);

    boolean existsById(int id);

    Award save(Award entity);
}
