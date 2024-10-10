package com.tnsif.placement.repository.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.tnsif.placement.model.Certificate;
import com.tnsif.placement.model.College; // Import the College class
import com.tnsif.placement.repository.CertificateRepository;
import com.tnsif.placement.repository.CollegeRepository;

import jakarta.persistence.EntityNotFoundException;

@DataJpaTest
public class CertificateRepositoryTest {

    @Autowired
    private CertificateRepository certificateRepository;
    
    @Autowired
    private CollegeRepository collegeRepository;

    private Certificate certificate;

    @BeforeEach
    public void setUp() {
        // Create and persist a College object
        College college = new College();
        college.setCollegeName("Test University"); // Set a mock name for the College object
        college = collegeRepository.save(college); // Save the College object

        // Initialize a sample Certificate object
        certificate = new Certificate();
        certificate.setCertificateName("Java Certification");
        certificate.setIssuingAuthority("Oracle");
        certificate.setIssueDate("2023-01-15");
        certificate.setCollege(college); // Associate the College object with the Certificate

        // Save and flush the certificate to ensure it's persisted
        certificate = certificateRepository.saveAndFlush(certificate);
    }


    @Test
    public void testFindById() {
        // Ensure the certificate can be found by the auto-generated ID
        Optional<Certificate> foundCertificate = certificateRepository.findById(certificate.getId());
        assertTrue(foundCertificate.isPresent(), "Certificate should be present");
        assertEquals(certificate.getCertificateName(), foundCertificate.get().getCertificateName());
        assertEquals(certificate.getCollege(), foundCertificate.get().getCollege()); // Check college association
    }

    @Test
    public void testSave() {
        // Create a new College object and persist it
        College college = new College();
        college.setCollegeName("Spring University"); // Set a mock name for the College object
        college = collegeRepository.save(college); // Save the College object to the database

        // Create a new certificate object
        Certificate newCertificate = new Certificate();
        newCertificate.setCertificateName("Spring Certification");
        newCertificate.setIssuingAuthority("Spring Framework");
        newCertificate.setCollege(college); // Associate the persisted College object

        // Save and flush the new certificate
        Certificate savedCertificate = certificateRepository.saveAndFlush(newCertificate);
        assertNotNull(savedCertificate.getId(), "Certificate ID should be generated");
        assertEquals(newCertificate.getCertificateName(), savedCertificate.getCertificateName());
        assertEquals(newCertificate.getCollege().getCollegeName(), savedCertificate.getCollege().getCollegeName()); // Verify college association
    }


    @Test
    public void testDelete() {
        // Delete the certificate and check if it no longer exists
        certificateRepository.delete(certificate);
        Optional<Certificate> deletedCertificate = certificateRepository.findById(certificate.getId());
        assertFalse(deletedCertificate.isPresent(), "Certificate should be deleted");
    }

    public void updateCertificate(Long certificateId, String certificateName, College college, String description, String issueDate, String issuingAuthority) {
        Certificate certificate = certificateRepository.findById(certificateId)
                .orElseThrow(() -> new EntityNotFoundException("Certificate not found"));
        certificate.setCertificateName(certificateName);
        certificate.setCollege(college); // Set the College object
        certificate.setDescription(description);
        certificate.setIssueDate(issueDate);
        certificate.setIssuingAuthority(issuingAuthority);
        
        certificateRepository.save(certificate);
    }



}
