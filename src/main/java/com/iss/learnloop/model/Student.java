package com.iss.learnloop.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Student extends User{
    private EducationLevel levelOfEducation;
    private String schoolName;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<Enrollment> enrollments;

    public Student(EducationLevel levelOfEducation, String schoolName) {
        this.levelOfEducation = levelOfEducation;
        this.schoolName = schoolName;
        this.enrollments = new HashSet<Enrollment>();
    }

    public Student(){}



    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public EducationLevel getLevelOfEducation() {
        return levelOfEducation;
    }

    public void setLevelOfEducation(EducationLevel levelOfEducation) {
        this.levelOfEducation = levelOfEducation;
    }
}
