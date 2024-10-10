package com.tnsif.placement.services.test;

import com.tnsif.placement.dto.CertificateDTO;
import com.tnsif.placement.dto.CollegeDTO; // Make sure to import CollegeDTO
import com.tnsif.placement.model.Certificate;
import com.tnsif.placement.model.College; 
import com.tnsif.placement.repository.CertificateRepository;
import com.tnsif.placement.service.CertificateService;

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

class CertificateServiceTest {

    @Mock
    private CertificateRepository certificateRepository;

    @InjectMocks
    private CertificateService certificateService;

    private Certificate certificate1;
    private Certificate certificate2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        // Create test data
        College college1 = new College();
        college1.setId(1L);
        college1.setCollegeName("Test University");

        College college2 = new College();
        college2.setId(2L);
        college2.setCollegeName("Spring University");

        certificate1 = new Certificate(1L, "Java Certification", "Oracle", "2022-01-01", "Java basics certification", college1);
        certificate2 = new Certificate(2L, "AWS Certification", "Amazon", "2023-02-02", "AWS cloud certification", college2);
    }

    @Test
    void testListAll() {
        // Arrange
        List<Certificate> certificates = Arrays.asList(certificate1, certificate2);
        when(certificateRepository.findAll()).thenReturn(certificates);

        // Act
        List<CertificateDTO> result = certificateService.listAll();

        // Assert
        assertEquals(2, result.size());
        verify(certificateRepository, times(1)).findAll();
    }

    @Test
    void testGetCertificateById() {
        // Arrange
        when(certificateRepository.findById(1L)).thenReturn(Optional.of(certificate1));

        // Act
        CertificateDTO result = certificateService.get(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Java Certification", result.getCertificateName());
        assertEquals(certificate1.getCollege().getCollegeName(), result.getCollege().getCollegeName()); // Verify college name
        verify(certificateRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCertificateByIdNotFound() {
        // Arrange
        when(certificateRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        CertificateDTO result = certificateService.get(1L);

        // Assert
        assertNull(result);
        verify(certificateRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveCertificate() {
        // Arrange
        College college = new College();
        college.setId(1L);
        college.setCollegeName("Test University");

        // Create CollegeDTO instead of College
        CollegeDTO collegeDTO = new CollegeDTO();
        collegeDTO.setId(college.getId());
        collegeDTO.setCollegeName(college.getCollegeName());

        CertificateDTO dto = new CertificateDTO();
        dto.setId(certificate1.getId());
        dto.setCertificateName(certificate1.getCertificateName());
        dto.setDescription(certificate1.getDescription());
        dto.setCollege(collegeDTO); // Set the CollegeDTO object in DTO

        // Act
        certificateService.save(dto);

        // Assert
        verify(certificateRepository, times(1)).save(any(Certificate.class)); // Verify that save was called with a Certificate object
    }

    @Test
    void testDeleteCertificate() {
        // Act
        certificateService.delete(1L);

        // Assert
        verify(certificateRepository, times(1)).deleteById(1L);
    }
}
