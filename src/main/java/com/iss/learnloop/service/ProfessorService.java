package com.iss.learnloop.service;

import com.iss.learnloop.dto.CourseDetails;
import com.iss.learnloop.exception.LearnLoopException;
import com.iss.learnloop.model.Course;
import com.iss.learnloop.model.Professor;
import com.iss.learnloop.repository.CourseRepository;
import com.iss.learnloop.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public ProfessorService(ProfessorRepository professorRepository, CourseRepository courseRepository) {
        this.professorRepository = professorRepository;
        this.courseRepository = courseRepository;
    }

    public List<CourseDetails> getProfessorsCoursesDetails(long professorId) {
        List<Course> courses = courseRepository.findCoursesByProfessorId(professorId);
        List<CourseDetails> availableCourseDetails = new ArrayList<>();
        for (Course course : courses) {
            System.out.println("Are id ul: " +  course.getId());

            int numberOfEnrollments = courseRepository.countEnrollmentsByCourseId(course.getId());
            CourseDetails courseDetails =new CourseDetails(course.getId() ,course.getProfessorId(), course.getTitle(), course.getDescription(), course.getStartDate(), course.getDuration(), course.getApplicationDeadline(), course.getMaximumNumberOfStudents(), numberOfEnrollments);
            availableCourseDetails.add(courseDetails);
        }
        return availableCourseDetails;
    }

    public void addCourseForProfessor(long professorId, Course course) throws LearnLoopException {
        professorRepository.findById(professorId).orElseThrow(() -> new LearnLoopException("Professor not found"));
        course.setProfessor(professorId);
        courseRepository.save(course);
    }

    public void removeCourseForProfessor(long professorId, long courseId) throws LearnLoopException {
        Course course = courseRepository.findCourseById(courseId);
        if(course == null) {
            throw new LearnLoopException("Course not found");
        }
        if (course.getProfessorId() != professorId) {
            throw new LearnLoopException("Professor does not have the permission to delete this course.");
        }
        courseRepository.delete(course);

    }

    public Course updateCourseForProfessor(long professorId, long courseId, Course updatedCourse) throws LearnLoopException {
        Course foundCourse = courseRepository.findCourseById(courseId);

        if(foundCourse == null) {
            throw new LearnLoopException("Course not found");
        }

        if (foundCourse.getProfessorId() != professorId) {
            throw new LearnLoopException("Professor does not have the permission to update this course.");
        }

        foundCourse.setTitle(updatedCourse.getTitle());
        foundCourse.setDescription(updatedCourse.getDescription());
        foundCourse.setStartDate(updatedCourse.getStartDate());
        foundCourse.setDuration(updatedCourse.getDuration());
        foundCourse.setApplicationDeadline(updatedCourse.getApplicationDeadline());
        foundCourse.setMaximumNumberOfStudents(updatedCourse.getMaximumNumberOfStudents());
        courseRepository.save(foundCourse);

        return updatedCourse;




    }



}
