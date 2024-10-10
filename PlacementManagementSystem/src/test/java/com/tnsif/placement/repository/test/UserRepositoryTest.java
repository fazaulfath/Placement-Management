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

import com.tnsif.placement.model.AppUser;
import com.tnsif.placement.repository.UserRepository;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private AppUser user;

    @BeforeEach
    public void setUp() {
        // Initialize a sample AppUser object
        user = new AppUser();
        user.setUsername("john_doe");
        user.setRole("SDE");
        user.setPassword("securepassword");

        // Save the user and retrieve the auto-generated ID
        user = userRepository.save(user);
    }

    @Test
    public void testFindById() {
        // Use the auto-generated ID to find the user
        Optional<AppUser> foundUser = userRepository.findById(user.getId());
        assertTrue(foundUser.isPresent());
        assertEquals(user.getUsername(), foundUser.get().getUsername());
    }

    @Test
    public void testSave() {
        AppUser newUser = new AppUser();
        newUser.setUsername("jane_doe");
        newUser.setRole("SDE2");
        newUser.setPassword("anothersecurepassword");

        AppUser savedUser = userRepository.save(newUser);
        assertNotNull(savedUser.getId());
        assertEquals(newUser.getUsername(), savedUser.getUsername());
    }

    @Test
    public void testDelete() {
        userRepository.delete(user);
        Optional<AppUser> deletedUser = userRepository.findById(user.getId());
        assertFalse(deletedUser.isPresent());
    }

    @Test
    public void testUpdate() {
        user.setUsername("updated_john_doe");
        AppUser updatedUser = userRepository.save(user);
        assertEquals("updated_john_doe", updatedUser.getUsername());
    }
}
