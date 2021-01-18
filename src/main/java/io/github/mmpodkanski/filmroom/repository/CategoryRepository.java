package io.github.mmpodkanski.filmroom.repository;

import io.github.mmpodkanski.filmroom.models.Category;
import io.github.mmpodkanski.filmroom.models.ECategory;

import java.util.Optional;

public interface CategoryRepository {
    Optional<Category> findByName(ECategory name);
}
