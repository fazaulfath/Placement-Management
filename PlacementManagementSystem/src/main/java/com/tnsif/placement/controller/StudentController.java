package com.tnsif.placement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tnsif.placement.dto.CertificateDTO;
import com.tnsif.placement.dto.StudentDTO;
import com.tnsif.placement.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Get all students
    @GetMapping
    public ResponseEntity<List<StudentDTO>> listAll() {
        List<StudentDTO> students = studentService.listAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // Add a new student
    @PostMapping("/add")
    public ResponseEntity<StudentDTO> addStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO savedStudent = studentService.addStudent(studentDTO);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

 // Update an existing student
    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
        studentDTO.setId(id); // Ensure the ID in the DTO matches the path variable
        StudentDTO updatedStudent = studentService.updateStudent(studentDTO);
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    // Search student by hall ticket number
    @GetMapping("/search/{hallTicketNumber}")
    public ResponseEntity<StudentDTO> searchStudentByHallTicket(@PathVariable long hallTicketNumber) {
        StudentDTO student = studentService.searchStudentByHallTicket(hallTicketNumber);
        if (student != null) {
            return new ResponseEntity<>(student, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Add a certificate to a student
    @PostMapping("/{studentId}/certificate")
    public ResponseEntity<Void> addCertificate(@PathVariable Long studentId, @RequestBody CertificateDTO certificateDTO) {
        boolean isAdded = studentService.addCertificate(certificateDTO, studentId);
        if (isAdded) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Update a student's certificate
    @PutMapping("/{studentId}/certificate")
    public ResponseEntity<Void> updateCertificate(@PathVariable Long studentId, @RequestBody CertificateDTO certificateDTO) {
        boolean isUpdated = studentService.updateCertificate(certificateDTO, studentId);
        if (isUpdated) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete a student by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        boolean isDeleted = studentService.deleteStudent(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
