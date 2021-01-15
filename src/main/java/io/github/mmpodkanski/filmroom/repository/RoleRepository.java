package io.github.mmpodkanski.filmroom.repository;

import io.github.mmpodkanski.filmroom.models.ERole;
import io.github.mmpodkanski.filmroom.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
