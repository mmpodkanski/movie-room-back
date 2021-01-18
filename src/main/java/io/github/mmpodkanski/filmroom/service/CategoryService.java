package io.github.mmpodkanski.filmroom.service;

import io.github.mmpodkanski.filmroom.models.ECategory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

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
