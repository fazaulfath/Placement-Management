package com.tnsif.placement.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Placement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Many-to-One relationship with College
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "college_id", foreignKey = @ForeignKey(name = "FK_placement_college"))
    private College college;

    private LocalDate date;  // Changed from String to LocalDate for better date handling

    private String qualification;

    private String companyName;
    private String jobRole;
    private String location;
    private double salary;

    // One-to-One relationship with Student
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id", foreignKey = @ForeignKey(name = "FK_placement_student"))
    private Student student;

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

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
