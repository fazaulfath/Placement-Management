package com.tnsif.placement.model.test;

import org.junit.jupiter.api.Test;

import com.tnsif.placement.model.Admin;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdminModelTest {

    @Test
    void testAdminGettersAndSetters() {
        // Arrange
        Admin admin = new Admin();
        admin.setId(1L);
        admin.setAdminName("John Doe");
        admin.setEmail("john.doe@example.com");

        // Act and Assert
        assertEquals(1L, admin.getId());
        assertEquals("John Doe", admin.getAdminName());
        assertEquals("john.doe@example.com", admin.getEmail());
    }

    @Test
    void testAdminDefaultConstructor() {
        // Arrange
        Admin admin = new Admin();

        // Act and Assert
        assertEquals(null, admin.getId());
        assertEquals(null, admin.getAdminName());
        assertEquals(null, admin.getEmail());
    }
}
