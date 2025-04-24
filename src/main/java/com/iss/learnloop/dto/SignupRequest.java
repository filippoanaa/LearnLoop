package com.iss.learnloop.dto;

import com.iss.learnloop.model.EducationLevel;
import com.iss.learnloop.model.Position;
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
    private String phoneNumber;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
}
