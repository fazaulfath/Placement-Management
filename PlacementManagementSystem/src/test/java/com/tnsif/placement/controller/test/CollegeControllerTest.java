package com.tnsif.placement.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tnsif.placement.controller.CollegeController;
import com.tnsif.placement.dto.CollegeDTO;
import com.tnsif.placement.model.College;
import com.tnsif.placement.service.CollegeService;

public class CollegeControllerTest {

    @InjectMocks
    private CollegeController collegeController;

    @Mock
    private CollegeService collegeService;

    private College college;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        college = new College();
        college.setId(1L);
        college.setCollegeName("Test College");
        college.setLocation("Test Location");
        college.setAffiliation("Test Affiliation");
        // Removed the setAdmin since it doesn't exist
    }

    @Test
    public void testListAll() {
        List<CollegeDTO> colleges = new ArrayList<>();
        CollegeDTO collegeDTO = new CollegeDTO();
        collegeDTO.setId(1L);
        collegeDTO.setCollegeName("Test College");
        collegeDTO.setLocation("Test Location");
        collegeDTO.setAffiliation("Test Affiliation");
        collegeDTO.setCollegeAdminId(1L); // Updated to use collegeAdminId
        colleges.add(collegeDTO);
        
        when(collegeService.listAll()).thenReturn(colleges);

        ResponseEntity<List<CollegeDTO>> response = collegeController.listAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(colleges, response.getBody());
    }

    @Test
    public void testGetCollege() {
        CollegeDTO collegeDTO = new CollegeDTO();
        collegeDTO.setId(1L);
        collegeDTO.setCollegeName("Test College");
        collegeDTO.setLocation("Test Location");
        collegeDTO.setCollegeAdminId(1L); // Updated to use collegeAdminId

        when(collegeService.get(1L)).thenReturn(collegeDTO);

        ResponseEntity<CollegeDTO> response = collegeController.get(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(collegeDTO, response.getBody());
    }

    @Test
    public void testAddCollege() {
        CollegeDTO collegeDTO = new CollegeDTO();
        collegeDTO.setCollegeName("New College");
        collegeDTO.setLocation("New Location");
        collegeDTO.setCollegeAdminId(2L); // Updated to use collegeAdminId

        when(collegeService.addCollege(any(College.class))).thenReturn(college);

        ResponseEntity<College> response = collegeController.add(college);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(college, response.getBody());
    }

    @Test
    public void testUpdateCollege() {
        CollegeDTO dto = new CollegeDTO();
        dto.setId(1L);
        dto.setCollegeName("Updated College");
        dto.setLocation("Updated Location");
        dto.setCollegeAdminId(3L); // Updated to use collegeAdminId

        when(collegeService.existsById(1L)).thenReturn(true);
        // Assuming updateCollege() returns a value, we'll mock the return here
        when(collegeService.updateCollege(any(College.class))).thenReturn(college); 

        ResponseEntity<Void> response = collegeController.update(1L, dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateCollegeNotFound() {
        CollegeDTO dto = new CollegeDTO();
        dto.setId(1L);
        
        when(collegeService.existsById(1L)).thenReturn(false);

        ResponseEntity<Void> response = collegeController.update(1L, dto);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testSearchCollege() {
        when(collegeService.searchCollege(1L)).thenReturn(college);

        ResponseEntity<College> response = collegeController.searchCollege(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(college, response.getBody());
    }

    @Test
    public void testSearchCollegeNotFound() {
        when(collegeService.searchCollege(2L)).thenReturn(null);

        ResponseEntity<College> response = collegeController.searchCollege(2L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteCollege() {
        when(collegeService.deleteCollege(1L)).thenReturn(true);

        ResponseEntity<Void> response = collegeController.delete(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteCollegeNotFound() {
        when(collegeService.deleteCollege(2L)).thenReturn(false);

        ResponseEntity<Void> response = collegeController.delete(2L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testListAllPaginated() {
        Page<CollegeDTO> collegePage = mock(Page.class);
        when(collegeService.listAllPaginated(any(Pageable.class))).thenReturn(collegePage);

        ResponseEntity<Page<CollegeDTO>> response = collegeController.listAllPaginated(mock(Pageable.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(collegePage, response.getBody());
    }
}
