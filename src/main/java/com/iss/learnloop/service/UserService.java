package com.iss.learnloop.service;

import com.iss.learnloop.exception.LearnLoopException;
import com.iss.learnloop.model.User;
import com.iss.learnloop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User login(String email, String password) throws LearnLoopException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new LearnLoopException("No account found.");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new LearnLoopException("Incorrect password.");
        }
        return user;
    }

    public User signup(User user) throws LearnLoopException {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new LearnLoopException("User with this email already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

}
