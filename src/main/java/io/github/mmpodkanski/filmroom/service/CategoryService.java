package io.github.mmpodkanski.filmroom.service;

import io.github.mmpodkanski.filmroom.models.ECategory;
import org.springframework.stereotype.Service;

@Service
class CategoryService {

    public ECategory checkCategory(String categoryToCheck) {
        for (ECategory cat : ECategory.values()) {
            if (cat.name().equals(categoryToCheck)) {
                return cat;
            }
        }
        throw new IllegalStateException("That category not exists!");
    }
}
