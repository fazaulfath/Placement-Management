package com.tnsif.placement.services.test;

import com.tnsif.placement.dto.CollegeDTO;
import com.tnsif.placement.model.College;
import com.tnsif.placement.model.AppUser; // Assuming AppUser is the type for the admin
import com.tnsif.placement.repository.CollegeRepository;
import com.tnsif.placement.service.CollegeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CollegeServiceTest {

    @Mock
    private CollegeRepository collegeRepository;

    @InjectMocks
    private CollegeService collegeService;

    private College college1;
    private College college2;
    private AppUser admin1; // College admin mock object
    private AppUser admin2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create mock college admins (AppUser objects)
        admin1 = new AppUser();
        admin1.setId(101L); // Assume AppUser has an ID field

        admin2 = new AppUser();
        admin2.setId(102L);

        // Setup some College test data
        college1 = new College();
        college1.setId(1L);
        college1.setCollegeName("ABC College");
        college1.setLocation("New York");
        college1.setAffiliation("ABC University");
        college1.setCollegeAdmin(admin1); // Set college admin as AppUser object for college1

        college2 = new College();
        college2.setId(2L);
        college2.setCollegeName("XYZ College");
        college2.setLocation("California");
        college2.setAffiliation("XYZ University");
        college2.setCollegeAdmin(admin2); // Set college admin as AppUser object for college2
    }

    @Test
    void testListAll() {
        // Arrange
        List<College> colleges = Arrays.asList(college1, college2);
        when(collegeRepository.findAll()).thenReturn(colleges);

        // Act
        List<CollegeDTO> result = collegeService.listAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("ABC College", result.get(0).getCollegeName());
        assertEquals("XYZ College", result.get(1).getCollegeName());
        assertEquals("New York", result.get(0).getLocation());
        assertEquals("California", result.get(1).getLocation());
        assertEquals("ABC University", result.get(0).getAffiliation());
        assertEquals("XYZ University", result.get(1).getAffiliation());
        assertEquals(101L, result.get(0).getCollegeAdminId()); // Assert college admin ID for college1
        assertEquals(102L, result.get(1).getCollegeAdminId()); // Assert college admin ID for college2
        verify(collegeRepository, times(1)).findAll();
    }

    @Test
    void testGetCollegeById() {
        // Arrange
        when(collegeRepository.findById(1L)).thenReturn(Optional.of(college1));

        // Act
        CollegeDTO result = collegeService.get(1L);

        // Assert
        assertNotNull(result);
        assertEquals("ABC College", result.getCollegeName());
        assertEquals("New York", result.getLocation());
        assertEquals("ABC University", result.getAffiliation());
        assertEquals(101L, result.getCollegeAdminId()); // Assert college admin ID
        verify(collegeRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCollegeByIdNotFound() {
        // Arrange
        when(collegeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        CollegeDTO result = collegeService.get(1L);

        // Assert
        assertNull(result);
        verify(collegeRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveCollege() {
        // Create a CollegeDTO to save
        CollegeDTO dto = new CollegeDTO();
        dto.setId(college1.getId());
        dto.setCollegeName(college1.getCollegeName());
        dto.setLocation(college1.getLocation());
        dto.setAffiliation(college1.getAffiliation()); // Include affiliation
        dto.setCollegeAdminId(admin1.getId()); // Set the college admin ID using AppUser's ID

        // Act
        collegeService.save(dto);

        // Assert
        verify(collegeRepository, times(1)).save(any(College.class)); // Adjust if needed
    }

    @Test
    void testDeleteCollege() {
        // Arrange: Mock the repository to return true for existsById
        when(collegeRepository.existsById(1L)).thenReturn(true); // Mock as existing

        // Act: Call the delete method
        collegeService.delete(1L);

        // Assert: Verify that deleteById was called on the repository
        verify(collegeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCollegeNotFound() {
        // Arrange: Mock the repository to return false for existsById
        when(collegeRepository.existsById(1L)).thenReturn(false); // Mock as not existing

        // Act & Assert: Verify that deleting throws an exception
        Exception exception = assertThrows(RuntimeException.class, () -> collegeService.delete(1L));

        // Assert: Verify the exception message
        assertEquals("College not found", exception.getMessage());

        // Verify that deleteById was not called
        verify(collegeRepository, times(0)).deleteById(1L);
    }
}
