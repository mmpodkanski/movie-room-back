package io.github.mmpodkanski.filmroom.service;

import io.github.mmpodkanski.filmroom.models.request.AwardDTO;
import io.github.mmpodkanski.filmroom.models.Award;
import io.github.mmpodkanski.filmroom.repository.AwardRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AwardService {
    private final AwardRepository repository;

    AwardService(final AwardRepository repository) {
        this.repository = repository;
    }

    public Set<Award> returnAwards(Set<AwardDTO> awardsToCheck) {
        Set<Award> result = new HashSet<>();
        for (AwardDTO actor : awardsToCheck) {
            result.add(returnExistsOrCreateNew(actor));
        }
        return result;
    }

    Award returnExistsOrCreateNew(AwardDTO award) {
        return repository
                .findByName(award.getName())
                .orElse(saveAward(award.toAward()));
    }

    // TODO: maybe better to save Award and return Award
    Award saveAward(Award awardToSave) {
        return repository.save(awardToSave);
    }
}
