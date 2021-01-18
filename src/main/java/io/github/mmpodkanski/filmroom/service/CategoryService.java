package io.github.mmpodkanski.filmroom.service;

import io.github.mmpodkanski.filmroom.models.Category;
import io.github.mmpodkanski.filmroom.models.ECategory;
import io.github.mmpodkanski.filmroom.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
class CategoryService {
    private final CategoryRepository repository;

    CategoryService(final CategoryRepository repository) {
        this.repository = repository;
    }

    public Set<Category> checkCategories(Set<String> categoriesToCheck) {
        Set<Category> categories = new HashSet<>();

        if (categoriesToCheck.isEmpty()) {
            throw new IllegalStateException("Categories are empty!");
        }

        categoriesToCheck.forEach(cat -> {
            Category category = repository
                    .findByName(ECategory.valueOf(cat))
                    .orElseThrow(() -> new IllegalStateException("That category not exists!"));
            categories.add(category);
        });
        return categories;
    }
}
