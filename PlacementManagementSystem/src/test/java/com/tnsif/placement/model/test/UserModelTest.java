package com.tnsif.placement.model.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.tnsif.placement.model.AppUser;

class UserModelTest {

    @Test
    void testUserGettersAndSetters() {
        // Arrange
        AppUser user = new AppUser();
        user.setId(1L);
        user.setUsername("john_doe");
        user.setPassword("password123");
        user.setRole("admin");

        // Act and Assert
        assertEquals(1L, user.getId());
        assertEquals("john_doe", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("admin", user.getRole());
    }

    @Test
    void testUserDefaultConstructor() {
        // Arrange
        AppUser user = new AppUser();

        // Act and Assert
        assertEquals(null, user.getId());
        assertEquals(null, user.getUsername());
        assertEquals(null, user.getPassword());
        assertEquals(null, user.getRole());
    }
}
