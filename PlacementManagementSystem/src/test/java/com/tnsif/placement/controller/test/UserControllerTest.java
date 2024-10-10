package com.tnsif.placement.controller.test;

import com.tnsif.placement.controller.UserController;
import com.tnsif.placement.dto.UserDTO;
import com.tnsif.placement.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testListAll() {
        // Given
        UserDTO user1 = new UserDTO();
        UserDTO user2 = new UserDTO();
        List<UserDTO> users = Arrays.asList(user1, user2);
        when(userService.listAll()).thenReturn(users);

        // When
        ResponseEntity<List<UserDTO>> response = userController.listAll();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(userService, times(1)).listAll();
    }

    @Test
    public void testGetUserFound() {
        // Given
        Long id = 1L;
        UserDTO user = new UserDTO();
        when(userService.get(id)).thenReturn(user);

        // When
        ResponseEntity<UserDTO> response = userController.get(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).get(id);
    }

    @Test
    public void testGetUserNotFound() {
        // Given
        Long id = 1L;
        when(userService.get(id)).thenReturn(null);

        // When
        ResponseEntity<UserDTO> response = userController.get(id);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).get(id);
    }

    @Test
    public void testAddUser() {
        // Given
        UserDTO user = new UserDTO();
        doNothing().when(userService).save(any(UserDTO.class));

        // When
        userController.save(user);

        // Then
        verify(userService, times(1)).save(user);
    }

    @Test
    public void testUpdateUserFound() {
        // Given
        Long id = 1L;
        UserDTO user = new UserDTO();
        when(userService.get(id)).thenReturn(user);
        doNothing().when(userService).save(any(UserDTO.class));

        // When
        ResponseEntity<Void> response = userController.update(id, user);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).get(id);
        verify(userService, times(1)).save(user);
    }

    @Test
    public void testUpdateUserNotFound() {
        // Given
        Long id = 1L;
        UserDTO user = new UserDTO();
        when(userService.get(id)).thenReturn(null); // Mock userService to return null

        // When
        ResponseEntity<Void> response = userController.update(id, user);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).get(id); // Verify that userService.get() was called once
        verify(userService, never()).save(any(UserDTO.class)); // Verify that save() was never called
    }

    @Test
    public void testDeleteUser() {
        // Given
        Long id = 1L;
        doNothing().when(userService).delete(id);

        // When
        userController.delete(id);

        // Then
        verify(userService, times(1)).delete(id);
    }
}
