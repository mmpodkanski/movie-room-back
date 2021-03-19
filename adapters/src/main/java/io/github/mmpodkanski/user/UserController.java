package io.github.mmpodkanski.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600)
class UserController {
    private final UserFacade service;

    UserController(final UserFacade service) {
        this.service = service;
    }


    @PostMapping("/upgrade/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    ResponseEntity<?> upgradeUser(
            @PathVariable int id
    ) {
        service.setAdminRole(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
