package com.iss.learnloop.dto;

import java.sql.Time;
import java.util.Date;

public class CourseDetails {
    private long id;
    private String title;
    private String description;
    private Date startDate;
    private String duration;
    private int maximumNumberOfStudents;
    private Date applicationDeadline;
    private int numberOfEnrollments;
    private long professorId;


    public CourseDetails(long id, long professorId, String title, String description, Date startDate, String duration, Date applicationDeadline, int maximumNumberOfStudents, int numberOfEnrollments) {
        this.id = id;
        this.professorId = professorId;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.duration = duration;
        this.applicationDeadline = applicationDeadline;
        this.maximumNumberOfStudents = maximumNumberOfStudents;
        this.numberOfEnrollments = numberOfEnrollments;
    }

    public long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(long professorId) {
        this.professorId = professorId;
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

    public int getNumberOfEnrollments() {
        return numberOfEnrollments;
    }

    public void setNumberOfEnrollments(int numberOfEnrollments) {
        this.numberOfEnrollments = numberOfEnrollments;
    }
}
