package com.tnsif.placement.controller.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tnsif.placement.dto.AdminDTO;
import com.tnsif.placement.service.AdminService;

@SpringBootTest
public class AdminControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper; // For converting DTOs to JSON

    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testGetAllAdmins() throws Exception {
        AdminDTO admin1 = new AdminDTO();
        admin1.setId(1L);
        admin1.setAdminName("Admin 1");
        admin1.setEmail("admin1@example.com");

        AdminDTO admin2 = new AdminDTO();
        admin2.setId(2L);
        admin2.setAdminName("Admin 2");
        admin2.setEmail("admin2@example.com");

        List<AdminDTO> adminList = Arrays.asList(admin1, admin2);

        when(adminService.listAll()).thenReturn(adminList);

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].adminName").value("Admin 1"))
                .andExpect(jsonPath("$[1].adminName").value("Admin 2"));

        verify(adminService, times(1)).listAll();
    }

    @Test
    void testGetAdmin() throws Exception {
        AdminDTO admin = new AdminDTO();
        admin.setId(1L);
        admin.setAdminName("Admin 1");
        admin.setEmail("admin1@example.com");

        when(adminService.get(1L)).thenReturn(admin);

        mockMvc.perform(get("/admin/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adminName").value("Admin 1"));

        verify(adminService, times(1)).get(1L);
    }

    @Test
    void testGetAdminNotFound() throws Exception {
        when(adminService.get(anyLong())).thenThrow(new RuntimeException("Admin not found"));

        mockMvc.perform(get("/admin/{id}", 999L))
                .andExpect(status().isNotFound());

        verify(adminService, times(1)).get(999L);
    }

    @Test
    void testCreateAdmin() throws Exception {
        AdminDTO adminToCreate = new AdminDTO();
        adminToCreate.setAdminName("New Admin");
        adminToCreate.setEmail("new@example.com");

        when(adminService.createAdmin(any(AdminDTO.class))).thenReturn(adminToCreate);

        mockMvc.perform(post("/admin/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminToCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.adminName").value("New Admin"));

        verify(adminService, times(1)).createAdmin(any(AdminDTO.class));
    }

    @Test
    void testUpdateAdmin() throws Exception {
        AdminDTO updatedAdmin = new AdminDTO();
        updatedAdmin.setId(1L);
        updatedAdmin.setAdminName("Updated Admin");
        updatedAdmin.setEmail("updated@example.com");

        when(adminService.updateAdmin(anyLong(), any(AdminDTO.class))).thenReturn(updatedAdmin);

        mockMvc.perform(put("/admin/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedAdmin)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adminName").value("Updated Admin"));

        verify(adminService, times(1)).updateAdmin(anyLong(), any(AdminDTO.class));
    }

    @Test
    void testUpdateAdminNotFound() throws Exception {
        AdminDTO updatedAdmin = new AdminDTO();
        updatedAdmin.setAdminName("Updated Admin");
        updatedAdmin.setEmail("updated@example.com");

        // Ensure both arguments are using matchers
        when(adminService.updateAdmin(anyLong(), any(AdminDTO.class)))
                .thenThrow(new RuntimeException("Admin not found"));

        mockMvc.perform(put("/admin/{id}", 999L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedAdmin)))
                .andExpect(status().isNotFound());

        verify(adminService, times(1)).updateAdmin(anyLong(), any(AdminDTO.class));
    }

    @Test
    void testDeleteAdmin() throws Exception {
        doNothing().when(adminService).delete(1L);

        mockMvc.perform(delete("/admin/{id}", 1L))
                .andExpect(status().isOk());

        verify(adminService, times(1)).delete(1L);
    }

    @Test
    void testDeleteAdminNotFound() throws Exception {
        doThrow(new RuntimeException("Admin not found")).when(adminService).delete(999L);

        mockMvc.perform(delete("/admin/{id}", 999L))
                .andExpect(status().isNotFound());

        verify(adminService, times(1)).delete(999L);
    }
}
