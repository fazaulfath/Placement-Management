package com.tnsif.placement.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tnsif.placement.dto.CollegeDTO;
import com.tnsif.placement.model.College;
import com.tnsif.placement.model.Placement; // Import your Placement model
import com.tnsif.placement.repository.CollegeRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CollegeService {

    @Autowired
    private CollegeRepository repository;

    // DTO Conversion Methods
    private CollegeDTO convertToDTO(College college) {
        CollegeDTO dto = new CollegeDTO();
        dto.setId(college.getId());
        dto.setCollegeName(college.getCollegeName());
        dto.setLocation(college.getLocation());
        dto.setAffiliation(college.getAffiliation()); // Include affiliation in DTO conversion
        if (college.getCollegeAdmin() != null) {
            dto.setCollegeAdminId(college.getCollegeAdmin().getId()); // Assuming you want to return admin ID
        }
        return dto;
    }

    public College convertToEntity(CollegeDTO dto) {
        College college = new College();
        college.setId(dto.getId());
        college.setCollegeName(dto.getCollegeName());
        college.setLocation(dto.getLocation());
        college.setAffiliation(dto.getAffiliation()); // Set affiliation from DTO
        return college;
    }

    public List<CollegeDTO> listAll() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CollegeDTO get(Long id) {
        College college = repository.findById(id).orElse(null);
        return (college != null) ? convertToDTO(college) : null;
    }
    
    public boolean existsById(Long id) {
        return repository.existsById(id); 
    }

    public void save(CollegeDTO dto) {
        College college = convertToEntity(dto);
        repository.save(college);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("College not found"); // This should match your test logic
        }
        repository.deleteById(id);
    }

    public Page<CollegeDTO> listAllPaginated(Pageable pageable) {
        return repository.findAll(pageable).map(this::convertToDTO);
    }

    // New Methods

    public College addCollege(College college) {
        return repository.save(college);
    }

    public College updateCollege(College college) {
        if (!repository.existsById(college.getId())) {
            throw new RuntimeException("College not found"); // Handle not found case
        }
        return repository.save(college);
    }

    public College searchCollege(long id) {
        return repository.findById(id).orElse(null); // Return college if found, else null
    }

    public boolean deleteCollege(long id) {
        if (!repository.existsById(id)) {
            return false; // College not found
        }
        repository.deleteById(id);
        return true; // Successful deletion
    }

    public boolean schedulePlacement(Placement placement) {
        // Implement logic for scheduling placement
        // This will depend on how you want to handle the Placement model
        // For now, return true to indicate success (replace with actual logic)
        return true;
    }
}
