package com.iss.learnloop.service;

import com.iss.learnloop.exception.LearnLoopException;
import com.iss.learnloop.model.Course;
import com.iss.learnloop.model.Enrollment;
import com.iss.learnloop.model.Student;
import com.iss.learnloop.repository.CourseRepository;
import com.iss.learnloop.repository.EnrollmentRepository;
import com.iss.learnloop.repository.StudentRepository;
import com.iss.learnloop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<Course> getEnrolledCourses(Long id){
        List<Enrollment> enrollments =  enrollmentRepository.findByStudentId(id);
        List<Course> enrolledCourses = new ArrayList<>();
        enrollments.stream().forEach(enrollment -> {
            enrolledCourses.add(enrollment.getCourse());
        });
        return enrolledCourses;
    }

    public List<Course> getStudentAvailableCourses(long studentId){
        List<Course> courses = courseRepository.findAll();
        List<Course> availableCourses = new ArrayList<>();
        courses.forEach(course -> {
            Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, course.getId());
            if(enrollment == null){
                availableCourses.add(course);
            }
        });
        return availableCourses;
    }


    public void dropCourse(long studentId, long courseId) throws LearnLoopException {
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId);
        if(enrollment == null){
            throw new LearnLoopException("Enrollment not found");
        }
        enrollmentRepository.delete(enrollment);
    }

    public void joinCourse(long studentId, long courseId) throws LearnLoopException {
        System.out.println("Incerc sa dau join la curs");
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new LearnLoopException("Course not found."));

        Student student = studentRepository.findById(studentId).orElseThrow(() -> new LearnLoopException("Student not found."));
        boolean enrolled = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId) != null;

        if(enrolled){
            throw new LearnLoopException("Enrollment already exists");
        }
        Enrollment enrollment = new Enrollment(student, course);

        enrollmentRepository.save(enrollment);

    }
}
