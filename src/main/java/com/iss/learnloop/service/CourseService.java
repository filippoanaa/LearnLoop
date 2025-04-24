package com.iss.learnloop.service;

import com.iss.learnloop.exception.LearnLoopException;
import com.iss.learnloop.model.Course;
import com.iss.learnloop.model.Enrollment;
import com.iss.learnloop.repository.CourseRepository;
import com.iss.learnloop.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public Course getCourseById(long id) {
        return courseRepository.findCourseById(id);
    }

    public List<Course> getAvailableCourses() {
        return courseRepository.findAvailableCourses();
    }

    public void addCourse(Course course) {
        courseRepository.save(course);
    }


    public void removeCourse(Course course) throws LearnLoopException {
        if(!enrollmentRepository.findByCourseId(course.getId()).isEmpty()) {
            throw new LearnLoopException("Cannot delete this course because it has enrollments.");
        }
        courseRepository.delete(course);
    }

    public void updateCourse(Course course) throws LearnLoopException {
        if(!courseRepository.existsById(course.getId())) {
            throw new LearnLoopException("Cannot update this course because it does not exist.");
        }
        courseRepository.save(course);
    }

    public List<Enrollment> getEnrollments(Course course) {
        return enrollmentRepository.findByCourseId(course.getId());
    }

    public List<Course> getCoursesByProfessorId(long professorId) {
        return courseRepository.findCoursesByProfessorId(professorId);
    }
}
