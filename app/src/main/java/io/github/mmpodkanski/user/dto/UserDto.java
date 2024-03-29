package io.github.mmpodkanski.user.dto;


public interface UserDto {
    int getId();

    String getUsername();

    String getPassword();

    String getEmail();

    String getRole();

    boolean isLocked();
}
