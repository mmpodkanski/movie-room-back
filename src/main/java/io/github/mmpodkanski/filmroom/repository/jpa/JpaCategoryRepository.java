package io.github.mmpodkanski.filmroom.repository.jpa;

import io.github.mmpodkanski.filmroom.models.Category;
import io.github.mmpodkanski.filmroom.repository.CategoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface JpaCategoryRepository extends CategoryRepository, JpaRepository<Category, Integer> {
}
