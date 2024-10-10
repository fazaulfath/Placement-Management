package com.tnsif.placement.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tnsif.placement.controller.StudentController;
import com.tnsif.placement.dto.CertificateDTO;
import com.tnsif.placement.dto.StudentDTO;
import com.tnsif.placement.service.StudentService;

@SpringBootTest
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testListAllStudents() throws Exception {
        // Arrange
        StudentDTO student1 = new StudentDTO();
        student1.setId(1L);
        student1.setName("John Doe");
        student1.setQualification("B.Tech");
        student1.setCourse("Computer Science");
        student1.setYearOfPassing(2023);
        student1.setHallTicketNumber(123456);
        student1.setRoll(789012);
        student1.setCollege("ABC College");

        List<StudentDTO> students = Arrays.asList(student1);
        when(studentService.listAll()).thenReturn(students);

        // Act
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/students"));

        // Assert
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("John Doe"));

        verify(studentService, times(1)).listAll();
    }

    @Test
    void testAddStudent() throws Exception {
        // Arrange
        StudentDTO newStudent = new StudentDTO();
        newStudent.setName("Jane Doe");
        newStudent.setQualification("M.Tech");
        newStudent.setCourse("Data Science");
        newStudent.setYearOfPassing(2024);
        newStudent.setHallTicketNumber(654321);
        newStudent.setRoll(210987);
        newStudent.setCollege("XYZ College");

        when(studentService.addStudent(any(StudentDTO.class))).thenReturn(newStudent);

        // Act
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/students/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStudent)));

        // Assert
        resultActions.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Jane Doe"));

        ArgumentCaptor<StudentDTO> argumentCaptor = ArgumentCaptor.forClass(StudentDTO.class);
        verify(studentService, times(1)).addStudent(argumentCaptor.capture());
        assertEquals("Jane Doe", argumentCaptor.getValue().getName());
    }

    @Test
    void testUpdateStudent() throws Exception {
        // Arrange
        // Create a certificate object (if CertificateDTO has more fields, make sure to set them as needed)
        CertificateDTO certificate = new CertificateDTO();
        certificate.setCertificateName("Completion Certificate"); // Example field, adjust according to actual fields
        certificate.setIssueDate("2023-09-10"); // Example field, adjust according to actual fields
        
        // Create the updated student DTO
        StudentDTO updatedStudent = new StudentDTO();
        updatedStudent.setId(1L);  // Set the ID
        updatedStudent.setName("John Smith");
        updatedStudent.setQualification("B.Tech");
        updatedStudent.setCourse("Computer Science");
        updatedStudent.setYearOfPassing(2023);
        updatedStudent.setHallTicketNumber(123456);
        updatedStudent.setRoll(789012);
        updatedStudent.setCollege("ABC College"); // Newly added field
        updatedStudent.setCertificate(certificate); // Set the certificate

        // Mock the service behavior to return the updated student when updateStudent is called
        when(studentService.updateStudent(any(StudentDTO.class))).thenReturn(updatedStudent);

        // Act
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/students/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedStudent)));

        // Assert
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Smith"))
                .andExpect(jsonPath("$.college").value("ABC College"))  // Verify the newly added field
                .andExpect(jsonPath("$.certificate.certificateName").value("Completion Certificate"));  // Verify certificate field

        // Capture and verify the argument passed to the service
        ArgumentCaptor<StudentDTO> argumentCaptor = ArgumentCaptor.forClass(StudentDTO.class);
        verify(studentService, times(1)).updateStudent(argumentCaptor.capture());
        
        // Verify the values set in the argument passed to the service
        assertEquals("John Smith", argumentCaptor.getValue().getName());
        assertEquals("ABC College", argumentCaptor.getValue().getCollege());
        assertEquals("Completion Certificate", argumentCaptor.getValue().getCertificate().getCertificateName());
    }
}
