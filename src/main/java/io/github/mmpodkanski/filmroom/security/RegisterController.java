package io.github.mmpodkanski.filmroom.security;

import io.github.mmpodkanski.filmroom.exception.UserAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
class RegistrationController {
    private final UserService service;

    @PostMapping("/register")
    public void userRegistration(final @Valid UserDTO user) throws UserAlreadyExistException {
        service.register(user);
    }
}