package com.iss.learnloop.dto;

import com.iss.learnloop.model.EducationLevel;
import com.iss.learnloop.model.Position;
import com.iss.learnloop.model.Student;
import com.iss.learnloop.model.Professor;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;

import java.util.Date;
import java.util.List;

public class SignupRequest {
    private String userType;
    //common attributes
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String email;
    private String password;

    //students
    private EducationLevel levelOfEducation;
    private String schoolName;

    //professors
    private Position currentPosition;
    private String institute;
    private int experienceYears;
    private List<String> expertiseDomains;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EducationLevel getLevelOfEducation() {
        return levelOfEducation;
    }

    public void setLevelOfEducation(EducationLevel levelOfEducation) {
        this.levelOfEducation = levelOfEducation;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public List<String> getExpertiseDomains() {
        return expertiseDomains;
    }

    public void setExpertiseDomains(List<String> expertiseDomains) {
        this.expertiseDomains = expertiseDomains;
    }



    public Student getStudentFromRequest() {
        Student student = new Student();
        student.setFirstName(this.firstName);
        student.setLastName(this.lastName);
        student.setDateOfBirth(this.dateOfBirth);
        student.setEmail(this.email);
        student.setPassword(this.password);
        student.setLevelOfEducation(this.levelOfEducation);
        student.setSchoolName(this.schoolName);
        return student;
    }

    public Professor getProfessorFromRequest() {
        Professor professor = new Professor();
        professor.setFirstName(this.firstName);
        professor.setLastName(this.lastName);
        professor.setDateOfBirth(this.dateOfBirth);
        professor.setEmail(this.email);
        professor.setPassword(this.password);
        professor.setCurrentPosition(this.currentPosition);
        professor.setExperienceYears(this.experienceYears);
        professor.setInstitute(this.institute);
        professor.setExpertiseDomains(this.expertiseDomains);
        return professor;
    }
}
