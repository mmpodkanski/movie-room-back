package io.github.mmpodkanski.filmroom.security;

import io.github.mmpodkanski.filmroom.entity.User;
import io.github.mmpodkanski.filmroom.exception.UserAlreadyExistException;
import io.github.mmpodkanski.filmroom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private UserRepository repository;
    private PasswordEncoder passwordEncoder;

    public void register(UserDTO user) throws UserAlreadyExistException {

        //Let's check if user already registered with us
        if(checkIfUserExist(user.getUsername())){
            throw new UserAlreadyExistException("User already exists for this email");
        }
        User userEntity = new User();
        BeanUtils.copyProperties(user, userEntity);
        encodePassword(userEntity, user);
        repository.save(userEntity);
    }

    public boolean checkIfUserExist(String username) {
        return repository.existsByUsername(username);
    }

    private void encodePassword(User userEntity, UserDTO user){
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
