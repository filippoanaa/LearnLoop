package com.iss.learnloop.service;

import com.iss.learnloop.exception.LearnLoopException;
import com.iss.learnloop.model.Course;
import com.iss.learnloop.model.Enrollment;
import com.iss.learnloop.model.Professor;
import com.iss.learnloop.repository.CourseRepository;
import com.iss.learnloop.repository.EnrollmentRepository;
import com.iss.learnloop.repository.ProfessorRepository;
import com.iss.learnloop.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;


    @Autowired
    public CourseService(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository, StudentRepository studentRepository, ProfessorRepository professorRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
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

    public int getNumberOfEnrollments(long courseId){
        return courseRepository.countEnrollmentsByCourseId(courseId);
    }

    public void addCourseForProfessor(long professorId, Course course) throws LearnLoopException {
        Professor professor = professorRepository.findById(professorId).orElseThrow(() -> new LearnLoopException("Professor not found"));
        course.setProfessor(professorId);
        courseRepository.save(course);
    }

    public void removeCourseForProfessor(long professorId, long courseId) throws LearnLoopException {
        Course foundCourse = courseRepository.findCourseById(courseId);
        if(foundCourse == null) {
            throw new LearnLoopException("Course not found");
        }
        if (foundCourse.getProfessorId() != professorId) {
            throw new LearnLoopException("Professor does not have the permission to delete this course.");
        }
        courseRepository.delete(foundCourse);
    }

    public void updateCourseForProfessor(long courseId, long professorId, Course updatedCourse) {
    }
}
