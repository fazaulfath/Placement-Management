package com.tnsif.placement.model.test;

import org.junit.jupiter.api.Test;

import com.tnsif.placement.model.Certificate;
import com.tnsif.placement.model.College; // Import the College class

import static org.junit.jupiter.api.Assertions.assertEquals;

class CertificateModelTest {

    @Test
    void testCertificateGettersAndSetters() {
        // Arrange
        College college = new College(); // Create a College object for testing
        college.setId(1L); // Assuming the College class has an ID field
        college.setCollegeName("Test University"); // Assuming there's a name field in College

        Certificate certificate = new Certificate();
        certificate.setId(1L);
        certificate.setCertificateName("Java Certification");
        certificate.setIssuingAuthority("Oracle");
        certificate.setIssueDate("2023-01-15");
        certificate.setCollege(college); // Set the college

        // Act and Assert
        assertEquals(1L, certificate.getId());
        assertEquals("Java Certification", certificate.getCertificateName());
        assertEquals("Oracle", certificate.getIssuingAuthority());
        assertEquals("2023-01-15", certificate.getIssueDate());
        assertEquals(college, certificate.getCollege()); // Verify the college is set correctly
    }

    @Test
    void testCertificateDefaultConstructor() {
        // Arrange
        Certificate certificate = new Certificate();

        // Act and Assert
        assertEquals(null, certificate.getId());
        assertEquals(null, certificate.getCertificateName());
        assertEquals(null, certificate.getIssuingAuthority());
        assertEquals(null, certificate.getIssueDate());
        assertEquals(null, certificate.getCollege()); // Check if college is null by default
    }
}
