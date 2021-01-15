package io.github.mmpodkanski.filmroom.service;

import io.github.mmpodkanski.filmroom.models.Category;
import io.github.mmpodkanski.filmroom.models.Movie;
import io.github.mmpodkanski.filmroom.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
class CategoryService {
    private final CategoryRepository repository;

    CategoryService(final CategoryRepository repository) {
        this.repository = repository;
    }

    public Category returnExistsOrCreateNew(String categoryName, Movie movie) {
        Category category = repository.findByName(categoryName)
                .orElseGet(() -> {
                    var newCategory = new Category(categoryName, false);
                    return repository.save(newCategory);
                });

        category.getMovies().add(movie);
        return category;

    }
}
