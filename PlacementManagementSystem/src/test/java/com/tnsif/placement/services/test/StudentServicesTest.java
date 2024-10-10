package com.tnsif.placement.services.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.quality.Strictness;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.modelmapper.ModelMapper;

import com.tnsif.placement.dto.CertificateDTO;
import com.tnsif.placement.dto.StudentDTO;
import com.tnsif.placement.model.College;
import com.tnsif.placement.model.Student;
import com.tnsif.placement.repository.CollegeRepository;
import com.tnsif.placement.repository.StudentRepository;
import com.tnsif.placement.service.StudentService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class StudentServicesTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CollegeRepository collegeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private StudentService studentService;

    private Student student;
    private StudentDTO studentDTO;
    private College college;

    @BeforeEach
    public void setUp() {
        // Setup sample data for testing
        student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setHallTicketNumber(123456789);
        student.setYearOfPassing(2023);

        studentDTO = new StudentDTO();
        studentDTO.setId(1L);
        studentDTO.setName("John Doe");
        studentDTO.setHallTicketNumber(123456789);
        studentDTO.setYearOfPassing(2023);
        studentDTO.setCollege("Sample College");

        college = new College();
        college.setCollegeName("Sample College");
    }

    @Test
    public void testListAllStudents() {
        // Mock the student repository findAll method
        List<Student> studentList = new ArrayList<>();
        studentList.add(student);
        when(studentRepository.findAll()).thenReturn(studentList);

        // Mock the mapping from Student to StudentDTO
        when(modelMapper.map(any(Student.class), eq(StudentDTO.class))).thenReturn(studentDTO);

        // Call the service method
        List<StudentDTO> studentDTOList = studentService.listAll();

        // Assertions
        assertNotNull(studentDTOList);
        assertEquals(1, studentDTOList.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    public void testAddStudent() {
        // Mock the mapping from StudentDTO to Student
        when(modelMapper.map(any(StudentDTO.class), eq(Student.class))).thenReturn(student);
        when(collegeRepository.findByCollegeName(anyString())).thenReturn(college);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(modelMapper.map(any(Student.class), eq(StudentDTO.class))).thenReturn(studentDTO);

        // Call the service method
        StudentDTO savedStudentDTO = studentService.addStudent(studentDTO);

        // Assertions
        assertNotNull(savedStudentDTO);
        assertEquals(studentDTO.getName(), savedStudentDTO.getName());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    public void testUpdateStudent() {
        // Mock the findById and save methods
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(collegeRepository.findByCollegeName(anyString())).thenReturn(college);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        // Mock the mapping for updating
        when(modelMapper.map(any(StudentDTO.class), eq(Student.class))).thenReturn(student);
        when(modelMapper.map(any(Student.class), eq(StudentDTO.class))).thenReturn(studentDTO);

        // Call the service method
        StudentDTO updatedStudent = studentService.updateStudent(studentDTO);

        // Assertions
        assertNotNull(updatedStudent);
        assertEquals(studentDTO.getName(), updatedStudent.getName());
        verify(studentRepository, times(1)).findById(anyLong());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    public void testSearchStudentByHallTicket() {
        // Create a list with the student
        List<Student> studentList = new ArrayList<>();
        studentList.add(student);

        // Mock the repository method to return the list
        when(studentRepository.findByHallTicketNumber(anyLong())).thenReturn(studentList);
        when(modelMapper.map(any(Student.class), eq(StudentDTO.class))).thenReturn(studentDTO);

        // Call the service method
        StudentDTO foundStudent = studentService.searchStudentByHallTicket(123456789);

        // Assertions
        assertNotNull(foundStudent);
        assertEquals(studentDTO.getHallTicketNumber(), foundStudent.getHallTicketNumber());
        verify(studentRepository, times(1)).findByHallTicketNumber(anyLong());
    }


    @Test
    public void testAddCertificate() {
        // Mock the repository methods
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        CertificateDTO certificateDTO = new CertificateDTO();

        // Call the service method
        boolean isAdded = studentService.addCertificate(certificateDTO, student.getId());

        // Assertions
        assertTrue(isAdded);
        verify(studentRepository, times(1)).findById(anyLong());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    public void testDeleteStudent() {
        // Mock the repository methods
        when(studentRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(studentRepository).deleteById(anyLong());

        // Call the service method
        boolean isDeleted = studentService.deleteStudent(1L);

        // Assertions
        assertTrue(isDeleted);
        verify(studentRepository, times(1)).existsById(anyLong());
        verify(studentRepository, times(1)).deleteById(anyLong());
    }
}
