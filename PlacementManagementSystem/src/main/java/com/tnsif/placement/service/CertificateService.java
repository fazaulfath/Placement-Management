package com.tnsif.placement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tnsif.placement.dto.CertificateDTO;
import com.tnsif.placement.dto.CollegeDTO; // Import CollegeDTO
import com.tnsif.placement.model.Certificate;
import com.tnsif.placement.model.College; // Import College model
import com.tnsif.placement.repository.CertificateRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CertificateService {

    @Autowired
    private CertificateRepository repository;

    // Convert College entity to CollegeDTO
    private CollegeDTO convertCollegeToDTO(College college) {
        if (college == null) return null; // Handle null case
        CollegeDTO collegeDTO = new CollegeDTO();
        collegeDTO.setId(college.getId());
        collegeDTO.setCollegeName(college.getCollegeName());
        collegeDTO.setLocation(college.getLocation());
        collegeDTO.setAffiliation(college.getAffiliation());
        return collegeDTO;
    }

    // Convert Certificate entity to CertificateDTO
    private CertificateDTO convertToDTO(Certificate certificate) {
        CertificateDTO dto = new CertificateDTO();
        dto.setId(certificate.getId());
        dto.setCertificateName(certificate.getCertificateName());
        dto.setIssuingAuthority(certificate.getIssuingAuthority());
        dto.setIssueDate(certificate.getIssueDate());
        dto.setDescription(certificate.getDescription());
        dto.setCollege(convertCollegeToDTO(certificate.getCollege())); // Convert college to DTO
        return dto;
    }

    // Convert CertificateDTO to Certificate entity
    private Certificate convertToEntity(CertificateDTO dto) {
        Certificate certificate = new Certificate();
        certificate.setId(dto.getId());
        certificate.setCertificateName(dto.getCertificateName());
        certificate.setIssuingAuthority(dto.getIssuingAuthority());
        certificate.setIssueDate(dto.getIssueDate());
        certificate.setDescription(dto.getDescription());

        // Convert CollegeDTO back to College if necessary
        if (dto.getCollege() != null) {
            College college = new College();
            college.setId(dto.getCollege().getId());
            college.setCollegeName(dto.getCollege().getCollegeName());
            college.setLocation(dto.getCollege().getLocation());
            college.setAffiliation(dto.getCollege().getAffiliation());
            certificate.setCollege(college);
        }
        
        return certificate;
    }

    public List<CertificateDTO> listAll() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CertificateDTO get(Long id) {
        Certificate certificate = repository.findById(id).orElse(null);
        return (certificate != null) ? convertToDTO(certificate) : null;
    }

    public void save(CertificateDTO dto) {
        Certificate certificate = convertToEntity(dto);
        repository.save(certificate);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<CertificateDTO> listAllPaginated(Pageable pageable) {
        return repository.findAll(pageable).map(this::convertToDTO);
    }

    public CertificateDTO updateCertificate(Long id, CertificateDTO dto) {
        return repository.findById(id)
            .map(certificate -> {
                // Update fields
                certificate.setCertificateName(dto.getCertificateName());
                certificate.setIssuingAuthority(dto.getIssuingAuthority());
                certificate.setIssueDate(dto.getIssueDate());
                certificate.setDescription(dto.getDescription());
                
                // Handle college information
                if (dto.getCollege() != null) {
                    College college = new College();
                    college.setId(dto.getCollege().getId());
                    college.setCollegeName(dto.getCollege().getCollegeName());
                    college.setLocation(dto.getCollege().getLocation());
                    college.setAffiliation(dto.getCollege().getAffiliation());
                    certificate.setCollege(college);
                }

                // Save updated certificate and convert to DTO
                return convertToDTO(repository.save(certificate));
            })
            .orElse(null); // Return null if the certificate was not found
    }

    public Certificate searchCertificate(Long id) {
        return repository.findById(id).orElse(null);
    }
}
