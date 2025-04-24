package com.iss.learnloop.service;

import com.iss.learnloop.exception.LearnLoopException;
import com.iss.learnloop.model.User;
import com.iss.learnloop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User login(String email, String password) throws LearnLoopException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new LearnLoopException("No account found.");
        }
        if (!user.getPassword().equals(password)) {
            throw new LearnLoopException("Incorrect password.");
        }
        return user;
    }

    public void signup(User user) throws LearnLoopException {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new LearnLoopException("User with this email already exists.");
        }
        userRepository.save(user);
    }

}
