package com.tnsif.placement.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tnsif.placement.dto.PlacementDTO;
import com.tnsif.placement.model.Placement;
import com.tnsif.placement.repository.PlacementRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PlacementService {

    @Autowired
    private PlacementRepository repository;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public PlacementDTO convertToDTO(Placement placement) {
        PlacementDTO dto = new PlacementDTO();
        dto.setId(placement.getId());
        dto.setName(placement.getName());
        
        // Check if college is not null to avoid NullPointerException
        if (placement.getCollege() != null) {
            dto.setCollegeName(placement.getCollege().getCollegeName());
        } else {
            dto.setCollegeName("Unknown"); // or handle as needed
        }
        
        dto.setQualification(placement.getQualification());
        dto.setCompanyName(placement.getCompanyName());
        dto.setJobRole(placement.getJobRole());
        dto.setLocation(placement.getLocation());
        dto.setSalary(placement.getSalary());
        dto.setDate(placement.getDate().toString()); // Convert LocalDate to String

        return dto;
    }

    private Placement convertToEntity(PlacementDTO dto) {
        Placement placement = new Placement();
        placement.setId(dto.getId());
        placement.setName(dto.getName());
        // Assume you have a method to fetch the College entity based on the college name in DTO
        // placement.setCollege(collegeService.getCollegeByName(dto.getCollegeName()));
        placement.setDate(LocalDate.parse(dto.getDate(), formatter));
        placement.setQualification(dto.getQualification());
        placement.setCompanyName(dto.getCompanyName());
        placement.setJobRole(dto.getJobRole());
        placement.setLocation(dto.getLocation());
        placement.setSalary(dto.getSalary());
        return placement;
    }

    // List all placements
    public List<PlacementDTO> listAll() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get placement by id
    public PlacementDTO get(Long id) {
        Placement placement = repository.findById(id).orElse(null);
        return (placement != null) ? convertToDTO(placement) : null;
    }

    // Check if placement exists by id
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    // Save placement from DTO
    public void save(PlacementDTO dto) {
        Placement placement = convertToEntity(dto);
        repository.save(placement);
    }

    // Delete placement by id
    public void delete(Long id) {
        repository.deleteById(id);
    }

    // Paginated list of placements
    public Page<PlacementDTO> listAllPaginated(Pageable pageable) {
        return repository.findAll(pageable).map(this::convertToDTO);
    }

    // Add new placement
    public Placement addPlacement(Placement placement) {
        return repository.save(placement);
    }

    // Update existing placement
    public Placement updatePlacement(Placement placement) {
        if (repository.existsById(placement.getId())) {
            return repository.save(placement);
        } else {
            return null;
        }
    }

    // Search placement by id
    public Placement searchPlacement(long id) {
        return repository.findById(id).orElse(null);
    }
}
