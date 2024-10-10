package com.tnsif.placement.repository.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.tnsif.placement.model.Admin;
import com.tnsif.placement.repository.AdminRepository;

@DataJpaTest
public class AdminRepositoryTest {

    @Autowired
    private AdminRepository adminRepository;

    private Admin admin;

    @BeforeEach
    public void setUp() {
        // Initialize a sample Admin object without setting ID manually
        admin = new Admin();
        admin.setAdminName("John Doe");
        admin.setEmail("john.doe@example.com");
        
        // Save and flush to ensure persistence
        admin = adminRepository.saveAndFlush(admin);
    }

    @Test
    public void testFindById() {
        // Find admin by the generated ID
        Optional<Admin> foundAdmin = adminRepository.findById(admin.getId());
        assertTrue(foundAdmin.isPresent(), "Admin should be present");
        assertEquals(admin.getAdminName(), foundAdmin.get().getAdminName());
    }

    @Test
    public void testSave() {
        // Create a new admin
        Admin newAdmin = new Admin();
        newAdmin.setAdminName("Jane Smith");
        newAdmin.setEmail("jane.smith@example.com");

        // Save the new admin
        Admin savedAdmin = adminRepository.saveAndFlush(newAdmin);
        assertNotNull(savedAdmin.getId(), "Admin ID should be generated");
        assertEquals(newAdmin.getAdminName(), savedAdmin.getAdminName());
    }

    @Test
    public void testDelete() {
        // Delete the admin created in setUp()
        adminRepository.delete(admin);
        
        // Check if the admin is deleted
        Optional<Admin> deletedAdmin = adminRepository.findById(admin.getId());
        assertFalse(deletedAdmin.isPresent(), "Admin should be deleted");
    }

    @Test
    public void testUpdate() {
        // Update admin's name
        admin.setAdminName("Updated Name");
        Admin updatedAdmin = adminRepository.saveAndFlush(admin);

        // Verify the updated details
        assertEquals("Updated Name", updatedAdmin.getAdminName());
    }
}
