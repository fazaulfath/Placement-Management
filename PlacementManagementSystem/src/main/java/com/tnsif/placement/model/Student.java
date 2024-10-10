package com.tnsif.placement.model;

import jakarta.persistence.*;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String qualification;
    private String course;
    private int yearOfPassing;
    
    @Column(unique = true)
    private long hallTicketNumber;
    
    private long roll; // Added roll number field

    // Many-to-One relationship with College
    @ManyToOne
    @JoinColumn(name = "college_id", foreignKey = @ForeignKey(name = "FK_student_college"))
    private College college;

    // One-to-One relationship with Placement
    @OneToOne(mappedBy = "student")
    private Placement placement;

    // One-to-One relationship with Certificate
    @OneToOne
    @JoinColumn(name = "certificate_id")
    private Certificate certificate; // Added relationship for Certificate

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

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }

    public Placement getPlacement() {
        return placement;
    }

    public void setPlacement(Placement placement) {
        this.placement = placement;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }
}
