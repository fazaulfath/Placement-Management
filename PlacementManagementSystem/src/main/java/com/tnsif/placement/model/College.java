package com.tnsif.placement.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class College {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String collegeName;
    private String location;
    private String affiliation;

    // One-to-Many relationship with Student
    @OneToMany(mappedBy = "college")
    private List<Student> students;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "admin_id", foreignKey = @ForeignKey(name = "FK_college_admin"))
    private Admin admin;

    // One-to-One relationship with User as collegeAdmin with cascading
    @OneToOne(cascade = CascadeType.PERSIST) // CascadeType.ALL can also be used if you want other operations like REMOVE or MERGE cascaded
    @JoinColumn(name = "college_admin_id", foreignKey = @ForeignKey(name = "FK_college_user"))
    private AppUser collegeAdmin;

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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public AppUser getCollegeAdmin() {
        return collegeAdmin;
    }

    public void setCollegeAdmin(AppUser collegeAdmin) {
        this.collegeAdmin = collegeAdmin;
    }
}
