package com.tnsif.placement.services.test;

import com.tnsif.placement.dto.UserDTO;  // Import the DTO class
import com.tnsif.placement.model.AppUser;
import com.tnsif.placement.repository.UserRepository;
import com.tnsif.placement.service.UserService;

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

public class UserServicesTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private AppUser user;
    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new AppUser();
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setRole("USER");

        // Initialize the DTO
        userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail("test@example.com"); // Set the email for the DTO
    }

    @Test
    public void testListAll() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<UserDTO> users = userService.listAll();  // Expecting UserDTO
        
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("testUser", users.get(0).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO foundUser = userService.get(1L);  // Expecting UserDTO
        
        assertNotNull(foundUser);
        assertEquals("testUser", foundUser.getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveUser() {
        userService.save(userDTO);  // Pass the DTO
        
        verify(userRepository, times(1)).save(any(AppUser.class));  // Check that save was called
    }

    @Test
    public void testDeleteUser() {
        userService.delete(1L);
        
        verify(userRepository, times(1)).deleteById(1L);
    }
}
