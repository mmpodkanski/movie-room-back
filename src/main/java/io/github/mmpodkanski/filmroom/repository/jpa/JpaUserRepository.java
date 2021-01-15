package io.github.mmpodkanski.filmroom.repository.jpa;

import io.github.mmpodkanski.filmroom.models.User;
import io.github.mmpodkanski.filmroom.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface JpaUserRepository extends UserRepository, JpaRepository<User, Integer> {
}
