package com.tnsif.placement.dto;

public class CertificateDTO {
    private Long id;
    private String certificateName;
    private String issuingAuthority; 
    private String issueDate; 
    private String description; 
    private CollegeDTO college; // Use CollegeDTO instead of College

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getIssuingAuthority() {
        return issuingAuthority;
    }

    public void setIssuingAuthority(String issuingAuthority) {
        this.issuingAuthority = issuingAuthority;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CollegeDTO getCollege() { 
        return college; 
    }

    public void setCollege(CollegeDTO college) { 
        this.college = college; 
    }
}
