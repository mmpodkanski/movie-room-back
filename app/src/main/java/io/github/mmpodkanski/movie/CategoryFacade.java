package io.github.mmpodkanski.movie;

import io.github.mmpodkanski.exception.ApiBadRequestException;
import org.springframework.stereotype.Service;

@Service
class CategoryFacade {

    public ECategory checkCategory(String categoryToCheck) {
        for (ECategory cat : ECategory.values()) {
            if (cat.name().equals(categoryToCheck)) {
                return cat;
            }
        }

        throw new ApiBadRequestException("That category not exists!");
    }
}
