package com.tnsif.placement.services.test;

import com.tnsif.placement.dto.PlacementDTO;
import com.tnsif.placement.model.College;
import com.tnsif.placement.model.Placement;
import com.tnsif.placement.repository.PlacementRepository;
import com.tnsif.placement.service.PlacementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlacementServiceTest {

    @Mock
    private PlacementRepository placementRepository;

    @InjectMocks
    private PlacementService placementService;

    private PlacementDTO placementDTO1;
    private PlacementDTO placementDTO2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create mock PlacementDTO objects for testing
        placementDTO1 = new PlacementDTO();
        placementDTO1.setId(1L);
        placementDTO1.setName("John Doe");
        placementDTO1.setCollegeName("ABC College");
        placementDTO1.setQualification("B.Tech");
        placementDTO1.setCompanyName("ABC Corp");
        placementDTO1.setJobRole("Software Engineer");
        placementDTO1.setLocation("New York");
        placementDTO1.setSalary(120000);
        placementDTO1.setDate("2024-08-15");

        placementDTO2 = new PlacementDTO();
        placementDTO2.setId(2L);
        placementDTO2.setName("Jane Smith");
        placementDTO2.setCollegeName("XYZ University");
        placementDTO2.setQualification("M.Sc.");
        placementDTO2.setCompanyName("XYZ Inc.");
        placementDTO2.setJobRole("Data Analyst");
        placementDTO2.setLocation("San Francisco");
        placementDTO2.setSalary(95000);
        placementDTO2.setDate("2024-07-30");
    }

    @Test
    void testListAllPlacements() {
        // Arrange
        College college = new College();
        college.setCollegeName("ABC College");

        Placement placement1 = new Placement();
        placement1.setId(1L);
        placement1.setName("John Doe");
        placement1.setCollege(college);  // Set the College object here
        placement1.setQualification("B.Tech");
        placement1.setCompanyName("ABC Corp");
        placement1.setJobRole("Software Engineer");
        placement1.setLocation("New York");
        placement1.setSalary(120000);
        placement1.setDate(LocalDate.of(2024, 8, 15));

        Placement placement2 = new Placement();
        placement2.setId(2L);
        placement2.setName("Jane Smith");
        college = new College();
        college.setCollegeName("XYZ University");
        placement2.setCollege(college);  // Set the College object here
        placement2.setQualification("M.Sc.");
        placement2.setCompanyName("XYZ Inc.");
        placement2.setJobRole("Data Analyst");
        placement2.setLocation("San Francisco");
        placement2.setSalary(95000);
        placement2.setDate(LocalDate.of(2024, 7, 30));

        List<Placement> placements = Arrays.asList(placement1, placement2);
        when(placementRepository.findAll()).thenReturn(placements);

        // Act
        List<PlacementDTO> result = placementService.listAll();

        // Assert
        assertEquals(2, result.size());
        verify(placementRepository, times(1)).findAll();
    }


    @Test
    void testGetPlacementById() {
        // Arrange
        Placement placement = new Placement();
        placement.setId(1L);
        placement.setName("John Doe");

        College college = new College();
        college.setCollegeName("ABC College");
        placement.setCollege(college);  // Set College object instead of String

        placement.setQualification("B.Tech");
        placement.setCompanyName("ABC Corp");
        placement.setJobRole("Software Engineer");
        placement.setLocation("New York");
        placement.setSalary(120000);
        placement.setDate(LocalDate.of(2024, 8, 15));  // Use LocalDate for date

        when(placementRepository.findById(1L)).thenReturn(Optional.of(placement));

        // Act
        PlacementDTO result = placementService.get(1L);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("ABC College", result.getCollegeName());  // Adjusted for College name
        assertEquals("B.Tech", result.getQualification());
        assertEquals("ABC Corp", result.getCompanyName());
        assertEquals("Software Engineer", result.getJobRole());
        assertEquals("New York", result.getLocation());
        assertEquals(120000, result.getSalary());
        assertEquals("2024-08-15", result.getDate());  // Comparing string format for date
        verify(placementRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPlacementByIdNotFound() {
        // Arrange
        when(placementRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        PlacementDTO result = placementService.get(1L);

        // Assert
        assertNull(result);
        verify(placementRepository, times(1)).findById(1L);
    }

    @Test
    void testSavePlacement() {
        // Act
        placementService.save(placementDTO1);

        // Assert
        verify(placementRepository, times(1)).save(any(Placement.class));  // Use 'any' for the Placement conversion
    }

    @Test
    void testDeletePlacement() {
        // Act
        placementService.delete(1L);

        // Assert
        verify(placementRepository, times(1)).deleteById(1L);
    }
}
