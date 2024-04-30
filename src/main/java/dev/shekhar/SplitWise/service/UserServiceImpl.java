package dev.shekhar.SplitWise.service;

import dev.shekhar.SplitWise.entity.User;
import dev.shekhar.SplitWise.exception.InvalidCredentialException;
import dev.shekhar.SplitWise.exception.UserDoesNotExistException;
import dev.shekhar.SplitWise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService { // Done in order to avoid violating Dependency Inversion Principle
    @Autowired
    UserRepository userRepository;

    @Override
    public User signUp(String name, String email, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(encoder.encode(password)) ;
        return userRepository.save(user);
    }

    @Override
    public User logIn(String email, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User savedUser = userRepository.findUserByEmail(email);

        if(savedUser == null) {
            throw new UserDoesNotExistException("User not found");
        }

        if(encoder.matches(password, savedUser.getPassword())) {
            return savedUser;
        } else {
            throw new InvalidCredentialException("Invalid password");
        }
    }
}
