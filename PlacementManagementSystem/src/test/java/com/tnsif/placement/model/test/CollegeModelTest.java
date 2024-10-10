package com.tnsif.placement.model.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.tnsif.placement.model.Admin;
import com.tnsif.placement.model.AppUser;
import com.tnsif.placement.model.College;
import com.tnsif.placement.model.Student;

import java.util.ArrayList;
import java.util.List;

class CollegeModelTest {

    @Test
    void testCollegeGettersAndSetters() {
        // Arrange
        College college = new College();
        college.setId(1L);
        college.setCollegeName("National Institute of Technology");
        college.setLocation("Surat");
        college.setAffiliation("AICTE");

        // Mock data for new members
        List<Student> students = new ArrayList<>();
        students.add(new Student()); // Assuming Student class has a default constructor
        college.setStudents(students);

        Admin admin = new Admin(); // Assuming Admin class has a default constructor
        college.setAdmin(admin);

        AppUser collegeAdmin = new AppUser(); // Assuming AppUser class has a default constructor
        college.setCollegeAdmin(collegeAdmin);

        // Act & Assert
        assertEquals(1L, college.getId());
        assertEquals("National Institute of Technology", college.getCollegeName());
        assertEquals("Surat", college.getLocation());
        assertEquals("AICTE", college.getAffiliation());
        assertEquals(students, college.getStudents());
        assertEquals(admin, college.getAdmin());
        assertEquals(collegeAdmin, college.getCollegeAdmin());
    }

    @Test
    void testCollegeDefaultConstructor() {
        // Arrange
        College college = new College();

        // Act & Assert
        assertNull(college.getId());
        assertNull(college.getCollegeName());
        assertNull(college.getLocation());
        assertNull(college.getAffiliation());
        assertNull(college.getStudents());
        assertNull(college.getAdmin());
        assertNull(college.getCollegeAdmin());
    }
}
