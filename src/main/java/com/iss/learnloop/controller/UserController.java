package com.iss.learnloop.controller;

import com.iss.learnloop.dto.LoginRequest;
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

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/authentication/")
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

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok().body(user);
        } catch (LearnLoopException e) {
            if (e.getMessage().equals("No account found.")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } else if (e.getMessage().equals("Incorrect password.")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @PostMapping("signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        try {
            if (signupRequest.getUserType().equals("student")) {
                Student student = new Student();
                student.setFirstName(signupRequest.getFirstName());
                student.setLastName(signupRequest.getLastName());
                student.setDateOfBirth(signupRequest.getDateOfBirth());
                student.setEmail(signupRequest.getEmail());
                student.setPassword(signupRequest.getPassword());
                student.setLevelOfEducation(signupRequest.getLevelOfEducation());
                student.setSchoolName(signupRequest.getSchoolName());
                userService.signup(student);
            }else if(signupRequest.getUserType().equals("professor")){
                Professor professor = new Professor();
                professor.setFirstName(signupRequest.getFirstName());
                professor.setLastName(signupRequest.getLastName());
                professor.setDateOfBirth(signupRequest.getDateOfBirth());
                professor.setEmail(signupRequest.getEmail());
                professor.setPassword(signupRequest.getPassword());
                professor.setCurrentPosition(signupRequest.getCurrentPosition());
                professor.setExperienceYears(signupRequest.getExperienceYears());
                professor.setInstitute(signupRequest.getInstitute());
                professor.setExpertiseDomains(signupRequest.getExpertiseDomains());
                userService.signup(professor);
            }else{
                return ResponseEntity.badRequest().body("Invalid user type.");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("Registration done successfully.");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred. " + e.getMessage());
        }
    }


}
