package io.github.mmpodkanski.auth.dto;


import io.github.mmpodkanski.auth.ERole;

public interface UserDto {
    int getId();

    String getUsername();

    String getPassword();

    String getEmail();

    ERole getRole();

    boolean isLocked();
}
