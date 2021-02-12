package io.github.mmpodkanski.movieroom.service;

import io.github.mmpodkanski.movieroom.exception.ApiBadRequestException;
import io.github.mmpodkanski.movieroom.models.ECategory;
import org.springframework.stereotype.Service;

@Service
class CategoryService {

    public ECategory checkCategory(String categoryToCheck) {
        for (ECategory cat : ECategory.values()) {
            if (cat.name().equals(categoryToCheck)) {
                return cat;
            }
        }

        throw new ApiBadRequestException("That category not exists!");
    }
}
