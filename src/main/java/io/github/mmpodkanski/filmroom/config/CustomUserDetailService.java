package io.github.mmpodkanski.filmroom.config;

import io.github.mmpodkanski.filmroom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CustomUserDetailService implements UserDetailsService{
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserEntity customer = repository.findByUsername(username);
        if (customer == null) {
            throw new UsernameNotFoundException(email);
        }
        UserDetails user = User.withUsername(customer.getEmail())
                .password(customer.getPassword())
                .authorities("USER").build();
        return user;
    }

}
