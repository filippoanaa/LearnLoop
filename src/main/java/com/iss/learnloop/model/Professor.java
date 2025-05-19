package com.iss.learnloop.model;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Professor extends User{

    private Position currentPosition;
    private String institute;
    private int experienceYears;
    @ElementCollection
    private List<String> expertiseDomains;


    public Professor() {}
    public Professor(Position currentPosition, String institute, int experienceYears, List<String> expertiseDomains) {
        this.currentPosition = currentPosition;
        this.institute = institute;
        this.experienceYears = experienceYears;
        this.expertiseDomains = expertiseDomains;
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
