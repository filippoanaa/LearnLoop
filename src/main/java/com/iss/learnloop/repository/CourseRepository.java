package com.iss.learnloop.repository;

import com.iss.learnloop.model.Course;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT c from Course c where c.applicationDeadline > CURRENT_DATE AND SIZE(c.enrollments) < c.maximumNumberOfStudents")
    List<Course> findAvailableCourses();
    List<Course> findCoursesByProfessorId(Long professorId);
    Course findCourseById(long id);
//
//    @Modifying
//    @Transactional
//    @Query("UPDATE Course c SET c.title = :title, c.description = :description, c.startDate = :startDate, " +
//            "c.duration = :duration, c.maximumNumberOfStudents = :maxStudents, c.applicationDeadline = :deadline " +
//            "WHERE c.id = :id")
//    void updateCourseById(
//            @Param("id") Long id,
//            @Param("title") String title,
//            @Param("description") String description,
//            @Param("startDate") Date startDate,
//            @Param("duration") Time duration,
//            @Param("maxStudents") int maxStudents,
//            @Param("deadline") Date deadline
//    );

}
