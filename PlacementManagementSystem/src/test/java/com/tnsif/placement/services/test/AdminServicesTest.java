package com.tnsif.placement.services.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tnsif.placement.dto.AdminDTO;
import com.tnsif.placement.model.Admin;
import com.tnsif.placement.repository.AdminRepository;
import com.tnsif.placement.service.AdminService;

class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    private Admin admin1;
    private Admin admin2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup some Admin test data
        admin1 = new Admin();
        admin1.setId(1L);
        admin1.setAdminName("John Doe");
        admin1.setEmail("john.doe@example.com");

        admin2 = new Admin();
        admin2.setId(2L);
        admin2.setAdminName("Jane Smith");
        admin2.setEmail("jane.smith@example.com");
    }

    @Test
    void testListAll() {
        // Arrange
        List<Admin> admins = Arrays.asList(admin1, admin2);
        when(adminRepository.findAll()).thenReturn(admins);

        // Act
        List<AdminDTO> result = adminService.listAll();

        // Assert
        assertEquals(2, result.size());
        verify(adminRepository, times(1)).findAll();
    }

    @Test
    void testGetAdminById() {
        // Arrange
        when(adminRepository.findById(1L)).thenReturn(Optional.of(admin1));

        // Act
        AdminDTO result = adminService.get(1L);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getAdminName());
        assertEquals("john.doe@example.com", result.getEmail());
        verify(adminRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAdminByIdNotFound() {
        // Arrange
        when(adminRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            adminService.get(1L);  
        });

        assertEquals("Admin not found", exception.getMessage());
        verify(adminRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveAdmin() {
        // Arrange
        AdminDTO dto = new AdminDTO();
        dto.setId(admin1.getId());
        dto.setAdminName(admin1.getAdminName());
        dto.setEmail(admin1.getEmail());

        // Act
        adminService.saveAdmin(dto);

        // Assert
        // Verify that the repository save method was called once
        verify(adminRepository, times(1)).save(any(Admin.class));
        
        // You can also verify if the Admin created in save matches the expected values
        // by checking if the repository was called with an Admin object that has the same properties
        // You can set up an inline verification here
        verify(adminRepository).save(argThat(admin -> 
	        admin.getId().equals(dto.getId()) &&
	        admin.getAdminName().equals(dto.getAdminName()) &&
	        admin.getEmail().equals(dto.getEmail())
	    ));
    }


    @Test
    void testDeleteAdmin() {
        // Arrange
        when(adminRepository.existsById(1L)).thenReturn(true); // Mocking that the admin exists

        // Act
        adminService.delete(1L);

        // Assert
        verify(adminRepository, times(1)).deleteById(1L);
    }

}
