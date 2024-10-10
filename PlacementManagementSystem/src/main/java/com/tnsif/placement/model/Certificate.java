package com.tnsif.placement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "certificate")
public class Certificate {
	
    public Certificate() {
		super();
	}

	public Certificate(Long id, String certificateName, String issuingAuthority, String issueDate, String description,
			College college) {
		super();
		this.id = id;
		this.certificateName = certificateName;
		this.issuingAuthority = issuingAuthority;
		this.issueDate = issueDate;
		this.description = description;
		this.college = college;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String certificateName;
    private String issuingAuthority;
    private String issueDate;
    private String description;

    @ManyToOne // Assuming multiple certificates can be linked to a college
    @JoinColumn(name = "college_id")
    private College college; // Add the college field

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

    public College getCollege() { // Add getter for college
        return college;
    }

    public void setCollege(College college) { // Add setter for college
        this.college = college;
    }
}
