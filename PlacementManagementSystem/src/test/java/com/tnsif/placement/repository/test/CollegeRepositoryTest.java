package com.tnsif.placement.repository.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.tnsif.placement.model.Admin;
import com.tnsif.placement.model.AppUser;
import com.tnsif.placement.model.College;
import com.tnsif.placement.model.Student;
import com.tnsif.placement.repository.AdminRepository;
import com.tnsif.placement.repository.CollegeRepository;
import com.tnsif.placement.repository.UserRepository;

@DataJpaTest
public class CollegeRepositoryTest {

    @Autowired
    private CollegeRepository collegeRepository;
    
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    private College college;

    @BeforeEach
    public void setUp() {
        // Initialize a sample Admin object and save it
        Admin admin = new Admin();
        // Set the necessary fields for Admin before saving (if any)
        admin = adminRepository.saveAndFlush(admin); // Save the admin first

        // Initialize a sample AppUser object and save it
        AppUser collegeAdmin = new AppUser();
        // Set the necessary fields for AppUser before saving (if any)
        collegeAdmin = userRepository.saveAndFlush(collegeAdmin); // Save the collegeAdmin first

        // Initialize a sample College object without setting the ID manually
        college = new College();
        college.setCollegeName("ABC College");
        college.setLocation("City Center");
        
        // Set the saved admin and collegeAdmin
        college.setAdmin(admin);
        college.setCollegeAdmin(collegeAdmin);
        college.setStudents(new ArrayList<>()); // Initialize an empty list for students
        
        // Save and flush the college to ensure it's persisted
        college = collegeRepository.saveAndFlush(college);
    }


    @Test
    public void testFindById() {
        // Ensure the college can be found by the auto-generated ID
        Optional<College> foundCollege = collegeRepository.findById(college.getId());
        assertTrue(foundCollege.isPresent(), "College should be present");
        assertEquals(college.getCollegeName(), foundCollege.get().getCollegeName());
    }

    @Test
    public void testSave() {
        College newCollege = new College();
        newCollege.setCollegeName("XYZ University");
        newCollege.setLocation("Downtown");

        // Assuming Admin and AppUser have default constructors
        newCollege.setAdmin(new Admin());
        newCollege.setCollegeAdmin(new AppUser());
        newCollege.setStudents(new ArrayList<>()); // Initialize an empty list for students

        // Save and flush the new college
        College savedCollege = collegeRepository.saveAndFlush(newCollege);
        assertNotNull(savedCollege.getId(), "College ID should be generated");
        assertEquals(newCollege.getCollegeName(), savedCollege.getCollegeName());
    }

    @Test
    public void testDelete() {
        // Delete the college and check if it no longer exists
        collegeRepository.delete(college);
        Optional<College> deletedCollege = collegeRepository.findById(college.getId());
        assertFalse(deletedCollege.isPresent(), "College should be deleted");
    }

    @Test
    public void testUpdate() {
        // Update the college name
        college.setCollegeName("Updated ABC College");
        College updatedCollege = collegeRepository.saveAndFlush(college);
        assertEquals("Updated ABC College", updatedCollege.getCollegeName());
    }

    @Test
    public void testAddStudent() {
        // Add a student to the college
        Student student = new Student(); // Assuming Student has a default constructor
        college.getStudents().add(student);
        College updatedCollege = collegeRepository.saveAndFlush(college);
        assertEquals(1, updatedCollege.getStudents().size(), "College should have one student");
    }

    @Test
    public void testSetAdmin() {
        // Update the admin of the college
        Admin newAdmin = new Admin(); // Assuming Admin has a default constructor
        college.setAdmin(newAdmin);
        College updatedCollege = collegeRepository.saveAndFlush(college);
        assertEquals(newAdmin, updatedCollege.getAdmin(), "Admin should be updated");
    }

    @Test
    public void testSetCollegeAdmin() {
        // Update the college admin
        AppUser newCollegeAdmin = new AppUser(); // Assuming AppUser has a default constructor
        college.setCollegeAdmin(newCollegeAdmin);
        College updatedCollege = collegeRepository.saveAndFlush(college);
        assertEquals(newCollegeAdmin, updatedCollege.getCollegeAdmin(), "College admin should be updated");
    }
}
