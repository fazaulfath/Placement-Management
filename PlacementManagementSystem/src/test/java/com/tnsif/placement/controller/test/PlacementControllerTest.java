package com.tnsif.placement.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.tnsif.placement.controller.PlacementController;
import com.tnsif.placement.dto.PlacementDTO;
import com.tnsif.placement.service.PlacementService;

public class PlacementControllerTest {

    @Mock
    private PlacementService placementService;

    @InjectMocks
    private PlacementController placementController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        MockMvcBuilders.standaloneSetup(placementController).build();
    }

    @Test
    public void testListAll() {
        // Given
        PlacementDTO placement1 = createPlacementDTO(1L, "John Doe", "ABC College", "2024-09-25", "B.Tech", "XYZ Corp", "Software Engineer", "New York", 70000.0);
        PlacementDTO placement2 = createPlacementDTO(2L, "Jane Doe", "XYZ College", "2024-09-28", "MCA", "ABC Corp", "Data Analyst", "San Francisco", 80000.0);
        List<PlacementDTO> placements = Arrays.asList(placement1, placement2);
        when(placementService.listAll()).thenReturn(placements);

        // When
        ResponseEntity<List<PlacementDTO>> response = placementController.listAll();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(placementService, times(1)).listAll();
    }

    @Test
    public void testGetPlacementFound() {
        // Given
        Long id = 1L;
        PlacementDTO placement = createPlacementDTO(id, "John Doe", "ABC College", "2024-09-25", "B.Tech", "XYZ Corp", "Software Engineer", "New York", 70000.0);
        when(placementService.get(id)).thenReturn(placement);

        // When
        ResponseEntity<PlacementDTO> response = placementController.get(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(placement, response.getBody());
        verify(placementService, times(1)).get(id);
    }

    @Test
    public void testGetPlacementNotFound() {
        // Given
        Long id = 1L;
        when(placementService.get(id)).thenReturn(null); // Simulate that the placement is not found

        // When
        ResponseEntity<PlacementDTO> response = placementController.get(id);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); // Check that the status is NOT_FOUND
        verify(placementService, times(1)).get(id); // Verify that the get method was called
    }

    @Test
    public void testAddPlacement() {
        // Given
        PlacementDTO placement = createPlacementDTO(1L, "John Doe", "ABC College", "2024-09-25", "B.Tech", "XYZ Corp", "Software Engineer", "New York", 70000.0);
        doNothing().when(placementService).save(any(PlacementDTO.class));

        // When
        ResponseEntity<Void> response = placementController.save(placement); // Correctly call save method

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(placementService, times(1)).save(placement);
    }

    @Test
    public void testUpdatePlacementFound() {
        // Given
        Long id = 1L;
        PlacementDTO placement = createPlacementDTO(id, "John Doe", "ABC College", "2024-09-25", "B.Tech", "XYZ Corp", "Software Engineer", "New York", 70000.0);
        when(placementService.existsById(id)).thenReturn(true); // Placement exists
        doNothing().when(placementService).save(any(PlacementDTO.class));

        // When
        ResponseEntity<Void> response = placementController.update(id, placement);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(placementService, times(1)).existsById(id); // Check existsById was called
        verify(placementService, times(1)).save(placement); // Check save was called
    }

    @Test
    public void testUpdatePlacementNotFound() {
        // Given
        Long id = 1L;
        PlacementDTO placement = createPlacementDTO(id, "John Doe", "ABC College", "2024-09-25", "B.Tech", "XYZ Corp", "Software Engineer", "New York", 70000.0);
        when(placementService.existsById(id)).thenReturn(false); // Placement does not exist

        // When
        ResponseEntity<Void> response = placementController.update(id, placement);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(placementService, times(1)).existsById(id); // Verify existsById was called
        verify(placementService, never()).save(any(PlacementDTO.class)); // Ensure save was never called
    }

    @Test
    public void testDeletePlacement() {
        // Given
        Long id = 1L;
        when(placementService.existsById(id)).thenReturn(true); // Ensure the placement exists
        doNothing().when(placementService).delete(id);

        // When
        ResponseEntity<Void> response = placementController.delete(id); // Call the delete method

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(placementService, times(1)).delete(id);
    }

    private PlacementDTO createPlacementDTO(Long id, String name, String collegeName, String date, String qualification, String companyName, String jobRole, String location, double salary) {
        PlacementDTO placementDTO = new PlacementDTO();
        placementDTO.setId(id);
        placementDTO.setName(name);
        placementDTO.setCollegeName(collegeName);
        placementDTO.setDate(date);
        placementDTO.setQualification(qualification);
        placementDTO.setCompanyName(companyName);
        placementDTO.setJobRole(jobRole);
        placementDTO.setLocation(location);
        placementDTO.setSalary(salary);
        return placementDTO;
    }
}
