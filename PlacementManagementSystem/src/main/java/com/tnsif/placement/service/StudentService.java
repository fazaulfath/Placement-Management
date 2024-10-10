package com.tnsif.placement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tnsif.placement.dto.CertificateDTO;
import com.tnsif.placement.dto.StudentDTO;
import com.tnsif.placement.model.Certificate;
import com.tnsif.placement.model.College;
import com.tnsif.placement.model.Student;
import com.tnsif.placement.repository.CollegeRepository;
import com.tnsif.placement.repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CollegeRepository collegeRepository; // Assuming you have this repository

    // Convert Student to StudentDTO
    private StudentDTO convertToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setQualification(student.getQualification());
        dto.setCourse(student.getCourse());
        dto.setYearOfPassing(student.getYearOfPassing());
        dto.setHallTicketNumber(student.getHallTicketNumber());
        dto.setRoll(student.getRoll());
        dto.setCollege(student.getCollege() != null ? student.getCollege().getCollegeName() : null);
        dto.setCertificate(student.getCertificate() != null ? convertToCertificateDTO(student.getCertificate()) : null);
        return dto;
    }

    // Convert Certificate to CertificateDTO
    private CertificateDTO convertToCertificateDTO(Certificate certificate) {
        CertificateDTO dto = new CertificateDTO();
        // Set fields in the DTO (assuming you have a CertificateDTO class)
        dto.setId(certificate.getId());
        dto.setCertificateName(certificate.getCertificateName());
        // Add other fields...
        return dto;
    }

    // Convert StudentDTO to Student
    private Student convertToEntity(StudentDTO dto) {
        Student student = new Student();
        student.setId(dto.getId());
        student.setName(dto.getName());
        student.setQualification(dto.getQualification());
        student.setCourse(dto.getCourse());
        student.setYearOfPassing(dto.getYearOfPassing());
        student.setHallTicketNumber(dto.getHallTicketNumber());
        student.setRoll(dto.getRoll());
        // Handle college retrieval
        College college = collegeRepository.findByCollegeName(dto.getCollege());
        student.setCollege(college);
        // If there is a certificate, convert it as well (if needed)
        return student;
    }

    // List all students as DTOs
    public List<StudentDTO> listAll() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Add a new student
    public StudentDTO addStudent(StudentDTO studentDTO) {
        Student student = convertToEntity(studentDTO);
        Student savedStudent = studentRepository.save(student);
        return convertToDTO(savedStudent);
    }

    // Update an existing student
    public StudentDTO updateStudent(StudentDTO studentDTO) {
        Student student = studentRepository.findById(studentDTO.getId()).orElseThrow();
        student.setName(studentDTO.getName());
        student.setQualification(studentDTO.getQualification());
        student.setCourse(studentDTO.getCourse());
        student.setYearOfPassing(studentDTO.getYearOfPassing());
        student.setHallTicketNumber(studentDTO.getHallTicketNumber());
        student.setRoll(studentDTO.getRoll());
        // Update college if exists
        College college = collegeRepository.findByCollegeName(studentDTO.getCollege());
        student.setCollege(college);
        Student updatedStudent = studentRepository.save(student);
        return convertToDTO(updatedStudent);
    }

    public StudentDTO searchStudentByHallTicket(long hallTicketNumber) {
        // Assuming the repository method returns a List<Student>
        List<Student> students = studentRepository.findByHallTicketNumber(hallTicketNumber);
        
        if (students.isEmpty()) {
            throw new RuntimeException("Student not found with hall ticket number: " + hallTicketNumber);
        }
        
        // Assuming you want to return the first student found
        return convertToDTO(students.get(0));
    }


    // Add a certificate to a student
    public Boolean addCertificate(CertificateDTO certificateDTO, Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student != null) {
            Certificate certificate = convertToCertificate(certificateDTO);
            student.setCertificate(certificate);
            studentRepository.save(student);
            return true;
        }
        return false;
    }

    // Convert CertificateDTO to Certificate
    private Certificate convertToCertificate(CertificateDTO dto) {
        Certificate certificate = new Certificate();
        certificate.setId(dto.getId());
        certificate.setCertificateName(dto.getCertificateName());
        // Set other fields...
        return certificate;
    }

    // Update an existing certificate for a student
    public Boolean updateCertificate(CertificateDTO certificateDTO, Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student != null) {
            Certificate certificate = convertToCertificate(certificateDTO);
            student.setCertificate(certificate);
            studentRepository.save(student);
            return true;
        }
        return false;
    }

    // Delete a student by ID
    public boolean deleteStudent(long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
