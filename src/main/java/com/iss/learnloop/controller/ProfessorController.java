package com.iss.learnloop.controller;

import com.iss.learnloop.exception.LearnLoopException;
import com.iss.learnloop.model.Course;
import com.iss.learnloop.model.Professor;
import com.iss.learnloop.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("professors/")
public class ProfessorController {
    private final ProfessorService professorService;

    @Autowired
    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping("{professorId}")
    public ResponseEntity<?> getProfessorsCourses(@PathVariable long professorId) {
        List<Course> courses = professorService.getProfessorsCourses(professorId);
        return ResponseEntity.ok(courses);
    }

    @PostMapping("{professorId}")
    public ResponseEntity<?> addCourseToProfessor(@PathVariable long professorId, @RequestBody Course course) {
        try{
            professorService.addCourseForProfessor(professorId, course);
            return ResponseEntity.status(HttpStatus.CREATED).body("Course created successfully.");

        }catch (LearnLoopException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("{professorId}/{courseId}")
    public ResponseEntity<?> removeCourseFromProfessor(@PathVariable long professorId, @PathVariable long courseId) {
        try{
            professorService.removeCourseForProfessor(professorId, courseId);
            return ResponseEntity.ok("Course removed successfully.");
        }catch (LearnLoopException e){
            if(e.getMessage().equals("Course not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PutMapping("{professorId}")
    public ResponseEntity<?> updateCourse(@PathVariable long professorId, @RequestBody Course course) {
        try{
            professorService.updateCourseForProfessor(professorId, course);
            return ResponseEntity.ok("Course updated successfully.");
        }catch (LearnLoopException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



}
