package com.tnsif.placement.dto;

public class CollegeDTO {
    private Long id;
    private String collegeName;
    private String location;
    private String affiliation; // Added affiliation field
    private Long collegeAdminId; // This references the college admin by ID

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public Long getCollegeAdminId() {
        return collegeAdminId;
    }

    public void setCollegeAdminId(Long collegeAdminId) {
        this.collegeAdminId = collegeAdminId;
    }
}
