package io.github.mmpodkanski.filmroom.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class LoginController {
    @PostMapping("/login")
    public void login(){
    }
}
