package com.tnsif.placement.repository.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tnsif.placement.model.Student;
import com.tnsif.placement.repository.StudentRepository;

@DataJpaTest // Use this annotation for repository tests
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    private Student student;

    @BeforeEach
    public void setUp() {
        // Initialize a sample Student object
        student = new Student();
        student.setName("Alice Johnson");
        student.setQualification("BE");
        student.setCourse("ABC College");
        student.setYearOfPassing(2024);
        student.setHallTicketNumber(123457); // Initialize hallTicketNumber
        student.setRoll(20230301); // Initialize roll number
        
        // Save the student to ensure persistence
        student = studentRepository.save(student);
    }

    @Test
    public void testFindById() {
        // Fetch the student by the generated ID
        Optional<Student> foundStudent = studentRepository.findById(student.getId());
        assertTrue(foundStudent.isPresent(), "Student should be present");
        assertEquals(student.getName(), foundStudent.get().getName());
    }

    @Test
    public void testSave() {
        // Create a new student
        Student newStudent = new Student();
        newStudent.setName("Bob Smith");
        newStudent.setQualification("BE");
        newStudent.setCourse("XYZ University");
        newStudent.setYearOfPassing(2025);
        newStudent.setHallTicketNumber(654322); // Set hall ticket number
        newStudent.setRoll(20230302); // Set roll number

        // Save the new student
        Student savedStudent = studentRepository.save(newStudent);
        assertNotNull(savedStudent.getId(), "Student ID should be generated");
        assertEquals(newStudent.getName(), savedStudent.getName());
        assertEquals(newStudent.getHallTicketNumber(), savedStudent.getHallTicketNumber());
        assertEquals(newStudent.getRoll(), savedStudent.getRoll());
    }

    @Test
    public void testDelete() {
        // Delete the student created in setUp()
        studentRepository.delete(student);
        
        // Check if the student is actually deleted
        Optional<Student> deletedStudent = studentRepository.findById(student.getId());
        assertFalse(deletedStudent.isPresent(), "Student should be deleted");
    }

    @Test
    public void testUpdate() {
        // Update the student's name and hall ticket number
        student.setName("Updated Alice Johnson");
        student.setHallTicketNumber(987654); // Update hall ticket number
        Student updatedStudent = studentRepository.save(student);

        // Verify the updated student details
        assertEquals("Updated Alice Johnson", updatedStudent.getName());
        assertEquals(987654, updatedStudent.getHallTicketNumber()); // Verify updated hall ticket number
    }

    @Test
    public void testFindByHallTicketNumber() {
        // Fetch the list of students by hall ticket number
        List<Student> foundStudents = studentRepository.findByHallTicketNumber(student.getHallTicketNumber());
        
        // Check that the list is not empty
        assertFalse(foundStudents.isEmpty(), "Students should be found by hall ticket number");
        
        // Verify the details of the first student in the list
        Student foundStudent = foundStudents.get(0);
        assertNotNull(foundStudent, "Student should not be null");
        assertEquals(student.getName(), foundStudent.getName());
    }
}
