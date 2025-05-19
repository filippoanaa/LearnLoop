package com.iss.learnloop.controller;

import com.iss.learnloop.dto.CourseDetails;
import com.iss.learnloop.exception.LearnLoopException;
import com.iss.learnloop.model.Course;
import com.iss.learnloop.model.Professor;
import com.iss.learnloop.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("professors")
public class ProfessorController {
    private final ProfessorService professorService;

    @Autowired
    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping("/{professorId}/courses")
    public ResponseEntity<?> getProfessorsCourses(@PathVariable long professorId) {
        List<CourseDetails> courseDetails = professorService.getProfessorsCoursesDetails(professorId);
        return ResponseEntity.ok().body(courseDetails);
    }

    @PostMapping("/{professorId}/courses")
    public ResponseEntity<?> addCourseToProfessor(@PathVariable long professorId, @RequestBody Course course) {
        try{
            professorService.addCourseForProfessor(professorId, course);
            return ResponseEntity.status(HttpStatus.CREATED).body("Course created successfully.");

        }catch (LearnLoopException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{professorId}/courses/{courseId}")
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

    @PutMapping("/{professorId}/courses/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable long professorId, @PathVariable long courseId, @RequestBody Course course) {
        try{
            System.out.println("Id primit de la curs: " +  courseId);
            course.setId(courseId);
            System.out.println("Am apelat updatee cu id ul cursului: " + course.getId());

            Course updatedCourse = professorService.updateCourseForProfessor(professorId, courseId, course);

            // baiul ii aici, imi da exceptie metoda de mai sus ca are cursul id ul 0!!!
            return ResponseEntity.ok(updatedCourse);
        }catch (LearnLoopException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



}
