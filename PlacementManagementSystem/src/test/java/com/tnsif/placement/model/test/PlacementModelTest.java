package com.tnsif.placement.model.test;

import com.tnsif.placement.model.College;
import com.tnsif.placement.model.Placement;
import com.tnsif.placement.model.Student;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PlacementModelTest {

    @Test
    void testPlacementGettersAndSetters() {
        // Arrange
        Placement placement = new Placement();
        College college = new College();  // Mock college object
        college.setCollegeName("ABC College");

        Student student = new Student();  // Mock student object
        student.setName("John Doe");

        placement.setId(1L);
        placement.setName("Software Placement");
        placement.setCollege(college);
        placement.setDate(LocalDate.of(2023, 6, 1));
        placement.setQualification("B.Tech");
        placement.setCompanyName("Tech Corp");
        placement.setJobRole("Software Engineer");
        placement.setLocation("New York");
        placement.setSalary(80000.00);
        placement.setStudent(student);

        // Act and Assert
        assertEquals(1L, placement.getId());
        assertEquals("Software Placement", placement.getName());
        assertEquals(college, placement.getCollege());
        assertEquals(LocalDate.of(2023, 6, 1), placement.getDate());
        assertEquals("B.Tech", placement.getQualification());
        assertEquals("Tech Corp", placement.getCompanyName());
        assertEquals("Software Engineer", placement.getJobRole());
        assertEquals("New York", placement.getLocation());
        assertEquals(80000.00, placement.getSalary());
        assertEquals(student, placement.getStudent());
    }

    @Test
    void testPlacementDefaultConstructor() {
        // Arrange
        Placement placement = new Placement();

        // Act and Assert
        assertNull(placement.getId());
        assertNull(placement.getName());
        assertNull(placement.getCollege());
        assertNull(placement.getDate());
        assertNull(placement.getQualification());
        assertNull(placement.getCompanyName());
        assertNull(placement.getJobRole());
        assertNull(placement.getLocation());
        assertEquals(0.0, placement.getSalary());
        assertNull(placement.getStudent());
    }
}
