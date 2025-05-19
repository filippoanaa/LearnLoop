package com.iss.learnloop.controller;

import com.iss.learnloop.dto.CourseDetails;
import com.iss.learnloop.exception.LearnLoopException;
import com.iss.learnloop.model.Course;
import com.iss.learnloop.service.CourseService;
import com.iss.learnloop.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }


    @GetMapping("/{courseId}")
    public ResponseEntity<?> getCourseDetails(@PathVariable("courseId") int courseId) {
        Course course =  courseService.getCourseById(courseId);
        System.out.println("In course controller are id ul: " + courseId);
        if(course == null) {
            return ResponseEntity.notFound().build();
        }
        int numberOfEnrollments = courseService.getNumberOfEnrollments(courseId);
        CourseDetails courseDetails = new CourseDetails(course.getId(), course.getProfessorId(), course.getTitle(), course.getDescription(), course.getStartDate(), course.getDuration(), course.getApplicationDeadline(), course.getMaximumNumberOfStudents(), numberOfEnrollments);

        return ResponseEntity.ok(courseDetails);
    }


}
