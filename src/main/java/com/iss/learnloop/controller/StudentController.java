package com.iss.learnloop.controller;

import com.iss.learnloop.exception.LearnLoopException;
import com.iss.learnloop.model.Course;
import com.iss.learnloop.model.Enrollment;
import com.iss.learnloop.repository.StudentRepository;
import com.iss.learnloop.service.StudentService;
import jakarta.websocket.EncodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{studentId}/courses")
    public ResponseEntity<?> getCourses(
            @PathVariable long studentId,
            @RequestParam(value = "coursesType", required = false) String filter) {
        try {
            System.out.println("Received request for studentId: " + studentId + ", coursesType: " + filter);

            if ("enrolledCourses".equalsIgnoreCase(filter)) {
                List<Course> enrolledCourses = studentService.getEnrolledCourses(studentId);
                return ResponseEntity.ok(enrolledCourses);
            } else if ("availableCourses".equalsIgnoreCase(filter)) {
                List<Course> availableCourses = studentService.getStudentAvailableCourses(studentId);
                return ResponseEntity.ok(availableCourses);
            } else {
                return ResponseEntity.badRequest().body("Invalid filter parameter.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("{studentId}/courses/{courseId}")
    public ResponseEntity<?> joinCourse(@PathVariable long studentId, @PathVariable long courseId) {
        try{
            studentService.joinCourse(studentId, courseId);
            return ResponseEntity.ok().body("Course successfully joined.");
        }catch (LearnLoopException e){
           return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


    @DeleteMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<?> dropCourse(@PathVariable long studentId, @PathVariable long courseId) {
        try{
            studentService.dropCourse(studentId, courseId);
            return ResponseEntity.ok().body("Course successfully dropped");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
