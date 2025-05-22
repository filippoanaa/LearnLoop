package com.iss.learnloop.controller;

import com.iss.learnloop.dto.LoginRequest;
import com.iss.learnloop.dto.LoginResponse;
import com.iss.learnloop.dto.SignupRequest;
import com.iss.learnloop.exception.LearnLoopException;
import com.iss.learnloop.model.Professor;
import com.iss.learnloop.model.Student;
import com.iss.learnloop.model.User;
import com.iss.learnloop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            System.out.println("Ajunge cererea in controller");
            User user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
            String userType = (user instanceof Student) ? "student" : "professor";

            LoginResponse loginResponse = new LoginResponse(user, userType);

            return ResponseEntity.ok().body(loginResponse);
        } catch (LearnLoopException e) {
            if (e.getMessage().equals("No account found.")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } else if (e.getMessage().equals("Incorrect password.")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @PostMapping("/newAccount")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        try {
            User createdUser;
            if (signupRequest.getUserType().equals("student")) {
                Student student = signupRequest.getStudentFromRequest();
                createdUser = userService.signup(student);
            } else if (signupRequest.getUserType().equals("professor")) {
                Professor professor = signupRequest.getProfessorFromRequest();
                createdUser = userService.signup(professor);
            } else {
                return ResponseEntity.badRequest().body("Invalid user type.");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred. " + e.getMessage());
        }
    }

}
