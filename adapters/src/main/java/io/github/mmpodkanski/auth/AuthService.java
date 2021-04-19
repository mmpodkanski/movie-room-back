package io.github.mmpodkanski.auth;

import io.github.mmpodkanski.auth.dto.JwtResponse;
import io.github.mmpodkanski.auth.dto.LoginRequest;
import io.github.mmpodkanski.auth.dto.RegisterRequest;
import io.github.mmpodkanski.exception.ApiBadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
class AuthService {
    private final UserQueryRepository queryRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;


    AuthService(
            final UserQueryRepository queryRepository,
            final UserRepository userRepository,
            final UserFacade userFacade,
            final AuthenticationManager authenticationManager,
            final PasswordEncoder encoder,
            final JwtUtils jwtUtils
    ) {
        this.queryRepository = queryRepository;
        this.userRepository = userRepository;
        this.userFacade = userFacade;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        if (queryRepository.existsByUsername(registerRequest.getUsername())) {
            throw new ApiBadRequestException("Username is already taken!");
        }
        if (queryRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ApiBadRequestException("Email is already in use!");
        }

        var user = new User(0,
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                encoder.encode(registerRequest.getPassword()),
                ERole.ROLE_USER,
                false
        );

        userRepository.save(user);
    }

    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtAccessToken = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();
//        String role = userDetails.getAuthorities().toString();

        return new JwtResponse(
                userDetails.getSnapshot().getId(),
                jwtAccessToken,
                userDetails.getSnapshot().getUsername(),
                null,
                userDetails.getSnapshot().getRole().toString());
    }
}
