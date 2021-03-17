package io.github.mmpodkanski.movieroom.category;

import io.github.mmpodkanski.movieroom.exception.ApiBadRequestException;
import org.springframework.stereotype.Service;

@Service
public class CategoryFacade {

    public ECategory checkCategory(String categoryToCheck) {
        for (ECategory cat : ECategory.values()) {
            if (cat.name().equals(categoryToCheck)) {
                return cat;
            }
        }

        throw new ApiBadRequestException("That category not exists!");
    }
}
