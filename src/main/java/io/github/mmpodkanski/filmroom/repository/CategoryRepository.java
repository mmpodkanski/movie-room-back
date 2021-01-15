package io.github.mmpodkanski.filmroom.repository;

import io.github.mmpodkanski.filmroom.models.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<Category> findAll();

    Optional<Category> findById(int id);

    Optional<Category> findByName(String name);

    boolean existsByName(String name);

    boolean existsById(int id);

    Category save(Category entity);
}
