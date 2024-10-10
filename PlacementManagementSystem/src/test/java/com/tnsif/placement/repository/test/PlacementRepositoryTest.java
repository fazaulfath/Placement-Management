package com.tnsif.placement.repository.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.tnsif.placement.model.College;
import com.tnsif.placement.model.Placement;
import com.tnsif.placement.model.Student;
import com.tnsif.placement.repository.CollegeRepository;
import com.tnsif.placement.repository.PlacementRepository;
import com.tnsif.placement.repository.StudentRepository;

@DataJpaTest
public class PlacementRepositoryTest {

    @Autowired
    private PlacementRepository placementRepository;
    
    @Autowired
    private CollegeRepository collegeRepository;
    
    @Autowired
    private StudentRepository studentRepository;

    private Placement placement;

    @BeforeEach
    public void setUp() {
        // Initialize and save a College object first
        College college = new College();
        college.setCollegeName("ABC College");
        college = collegeRepository.save(college); // Save the college

        // Initialize and save a Student object with a unique hall ticket number
        Student student = new Student();
        student.setName("John Doe");
        student.setHallTicketNumber(12345); // Set a unique hall ticket number
        student = studentRepository.save(student); // Save the student

        // Now initialize the Placement object
        placement = new Placement();
        placement.setName("Software Placement");
        placement.setCollege(college); // Associate with the saved college
        placement.setDate(LocalDate.of(2023, 6, 1));
        placement.setQualification("B.Tech");
        placement.setCompanyName("Tech Corp");
        placement.setJobRole("Software Engineer");
        placement.setLocation("New York");
        placement.setSalary(60000);
        placement.setStudent(student); // Associate with the saved student

        // Save and flush the placement to ensure it's persisted
        placement = placementRepository.saveAndFlush(placement);
    }

    @Test
    public void testFindById() {
        // Ensure the placement can be found by the auto-generated ID
        Optional<Placement> foundPlacement = placementRepository.findById(placement.getId());
        assertTrue(foundPlacement.isPresent(), "Placement should be present");
        assertEquals(placement.getCompanyName(), foundPlacement.get().getCompanyName());
        assertEquals(placement.getJobRole(), foundPlacement.get().getJobRole());
        assertEquals(placement.getLocation(), foundPlacement.get().getLocation());
    }

    @Test
    public void testSave() {
        College newCollege = new College();
        newCollege.setCollegeName("XYZ College");
        newCollege = collegeRepository.save(newCollege); // Save the college

        Student newStudent = new Student();
        newStudent.setName("Jane Doe");
        newStudent = studentRepository.save(newStudent); // Save the student

        Placement newPlacement = new Placement();
        newPlacement.setName("Data Science Placement");
        newPlacement.setCollege(newCollege); // Associate with the saved college
        newPlacement.setDate(LocalDate.of(2024, 1, 10));
        newPlacement.setQualification("M.Tech");
        newPlacement.setCompanyName("Innovate Ltd");
        newPlacement.setJobRole("Data Scientist");
        newPlacement.setLocation("San Francisco");
        newPlacement.setSalary(75000);
        newPlacement.setStudent(newStudent); // Associate with the saved student

        // Save and flush the new placement
        Placement savedPlacement = placementRepository.saveAndFlush(newPlacement);
        assertNotNull(savedPlacement.getId(), "Placement ID should be generated");
        assertEquals(newPlacement.getCompanyName(), savedPlacement.getCompanyName());
        assertEquals(newPlacement.getJobRole(), savedPlacement.getJobRole());
    }

    @Test
    public void testDelete() {
        // Delete the placement and check if it no longer exists
        placementRepository.delete(placement);
        Optional<Placement> deletedPlacement = placementRepository.findById(placement.getId());
        assertFalse(deletedPlacement.isPresent(), "Placement should be deleted");
    }

    @Test
    public void testUpdate() {
        // Update the placement company name
        placement.setCompanyName("Updated Tech Corp");
        Placement updatedPlacement = placementRepository.saveAndFlush(placement);
        assertEquals("Updated Tech Corp", updatedPlacement.getCompanyName());
    }
}
