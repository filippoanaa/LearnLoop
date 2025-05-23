package com.iss.learnloop.model;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private Date startDate;
    private String duration;
    private int maximumNumberOfStudents;
    private Date applicationDeadline;
    private long professorId;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<Enrollment> enrollments;


    public Course() {}

    public Course(String title, String description, Date startDate, String duration, int maximumNumberOfStudents, Date applicationDeadline) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.duration = duration;
        this.maximumNumberOfStudents = maximumNumberOfStudents;
        this.applicationDeadline = applicationDeadline;
        this.enrollments = new HashSet<Enrollment>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getMaximumNumberOfStudents() {
        return maximumNumberOfStudents;
    }

    public void setMaximumNumberOfStudents(int maximumNumberOfStudents) {
        this.maximumNumberOfStudents = maximumNumberOfStudents;
    }

    public Date getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(Date applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public long getProfessorId() {
        return professorId;
    }

    public void setProfessor(long professorId) {
        this.professorId = professorId;
    }
}
