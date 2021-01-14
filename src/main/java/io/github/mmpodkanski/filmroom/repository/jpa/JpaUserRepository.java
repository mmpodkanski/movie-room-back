package io.github.mmpodkanski.filmroom.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.mmpodkanski.filmroom.entity.User;
import io.github.mmpodkanski.filmroom.repository.UserRepository;

@Repository
interface JpaUserRepository extends UserRepository, JpaRepository<User, Integer>{
}
