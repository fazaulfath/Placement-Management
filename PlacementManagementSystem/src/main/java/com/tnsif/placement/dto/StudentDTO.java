package com.tnsif.placement.dto;

public class StudentDTO {
    private Long id;
    private String name;
    private String qualification;
    private String course;
    private int yearOfPassing;
    private long hallTicketNumber; // existing field
    private long roll; // existing field
    private String college; // newly added field
    private CertificateDTO certificate; // existing field

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getYearOfPassing() {
        return yearOfPassing;
    }

    public void setYearOfPassing(int yearOfPassing) {
        this.yearOfPassing = yearOfPassing;
    }

    public long getHallTicketNumber() {
        return hallTicketNumber;
    }

    public void setHallTicketNumber(long hallTicketNumber) {
        this.hallTicketNumber = hallTicketNumber;
    }

    public long getRoll() {
        return roll;
    }

    public void setRoll(long roll) {
        this.roll = roll;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public CertificateDTO getCertificate() {
        return certificate;
    }

    public void setCertificate(CertificateDTO certificate) {
        this.certificate = certificate;
    }
}
