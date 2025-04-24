package com.iss.learnloop.service;

import com.iss.learnloop.exception.LearnLoopException;
import com.iss.learnloop.model.Course;
import com.iss.learnloop.model.Professor;
import com.iss.learnloop.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;
    private final CourseService courseService;

    @Autowired
    public ProfessorService(ProfessorRepository professorRepository, CourseService courseService) {
        this.professorRepository = professorRepository;
        this.courseService = courseService;
    }

    public List<Course> getProfessorsCourses(long professorId) {
        return courseService.getCoursesByProfessorId(professorId);
    }

    public void addCourseForProfessor(long professorId, Course course) throws LearnLoopException {
        Professor professor = professorRepository.findById(professorId).orElseThrow(() -> new LearnLoopException("Professor not found"));
        course.setProfessor(professor);
        courseService.addCourse(course);
    }

    public void removeCourseForProfessor(long professorId, long courseId) throws LearnLoopException {
        Course course = courseService.getCourseById(courseId);
        if(course == null) {
            throw new LearnLoopException("Course not found");
        }
        if (course.getProfessor() == null || course.getProfessor().getId() != professorId) {
            throw new LearnLoopException("Professor does not have the permission to delete this course.");
        }
        course.setProfessor(null);
        courseService.removeCourse(course);

    }

    public void updateCourseForProfessor(long professorId, Course course) throws LearnLoopException {
        if (course.getProfessor() == null || course.getProfessor().getId() != professorId) {
            throw new LearnLoopException("Professor does not have the permission to update this course.");
        }
        courseService.updateCourse(course);
    }


}
