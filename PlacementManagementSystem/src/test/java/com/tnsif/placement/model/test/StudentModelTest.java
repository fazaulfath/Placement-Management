package com.tnsif.placement.model.test;

import org.junit.jupiter.api.Test;

import com.tnsif.placement.model.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentModelTest {

    @Test
    void testStudentGettersAndSetters() {
        // Arrange
        Student student = new Student();
        student.setId(1L);
        student.setName("Alice Johnson");
        student.setQualification("Bachelor of Science");
        student.setCourse("Computer Science");
        student.setYearOfPassing(2024);
        student.setHallTicketNumber(123456); // Changed to long type
        student.setRoll(20230301); // Assuming a roll number for demonstration

        // Act and Assert
        assertEquals(1L, student.getId());
        assertEquals("Alice Johnson", student.getName());
        assertEquals("Bachelor of Science", student.getQualification());
        assertEquals("Computer Science", student.getCourse());
        assertEquals(2024, student.getYearOfPassing());
        assertEquals(123456, student.getHallTicketNumber()); // Changed to long type
        assertEquals(20230301, student.getRoll()); // Assert roll number
    }

    @Test
    void testStudentDefaultConstructor() {
        // Arrange
        Student student = new Student();

        // Act and Assert
        assertEquals(null, student.getId());
        assertEquals(null, student.getName());
        assertEquals(null, student.getQualification());
        assertEquals(null, student.getCourse());
        assertEquals(0, student.getYearOfPassing());
        assertEquals(0, student.getHallTicketNumber()); // Changed to long type
        assertEquals(0, student.getRoll()); // Assert roll number default value
    }
}
