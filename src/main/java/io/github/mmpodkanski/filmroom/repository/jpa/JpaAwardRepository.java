package io.github.mmpodkanski.filmroom.repository.jpa;

import io.github.mmpodkanski.filmroom.models.Award;
import io.github.mmpodkanski.filmroom.repository.AwardRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface JpaAwardRepository extends AwardRepository, JpaRepository<Award, Integer> {
}
