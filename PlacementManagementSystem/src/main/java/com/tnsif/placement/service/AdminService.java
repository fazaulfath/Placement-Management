package com.tnsif.placement.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tnsif.placement.dto.AdminDTO;
import com.tnsif.placement.model.Admin;
import com.tnsif.placement.repository.AdminRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AdminService {

    @Autowired
    private AdminRepository repository;

    // Mapping Admin entity to AdminDTO
    private AdminDTO convertToDTO(Admin admin) {
        AdminDTO dto = new AdminDTO();
        dto.setId(admin.getId());
        dto.setAdminName(admin.getAdminName());
        dto.setEmail(admin.getEmail());
        return dto;
    }

    // Convert AdminDTO to Admin entity
    public Admin convertToEntity(AdminDTO dto) {
        Admin admin = new Admin();
        admin.setId(dto.getId());
        admin.setAdminName(dto.getAdminName());
        admin.setEmail(dto.getEmail());
        return admin;
    }

    // Return list of AdminDTO instead of Admin entity
    public List<AdminDTO> listAll() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AdminDTO get(Long id) {
        // Fetch Admin entity and convert to AdminDTO
        return repository.findById(id).map(this::convertToDTO)
                         .orElseThrow(() -> new RuntimeException("Admin not found")); // Throw an exception if not found
    }


    // Create Admin from AdminDTO and return AdminDTO
    public AdminDTO createAdmin(AdminDTO dto) {
        Admin admin = convertToEntity(dto);
        Admin savedAdmin = repository.save(admin);
        return convertToDTO(savedAdmin); // Return saved Admin as DTO
    }

    public Optional<Admin> findById(Long id) {
        return repository.findById(id); // Fetch Admin by ID
    }

    public AdminDTO updateAdmin(Long id, AdminDTO adminDTO) {
        Admin admin = convertToEntity(adminDTO);
        admin.setId(id);
        return convertToDTO(repository.save(admin));
    }

    public void saveAdmin(AdminDTO dto) {
        Admin admin = convertToEntity(dto);
        repository.save(admin);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Admin not found");
        }
        repository.deleteById(id);
    }

    public Page<AdminDTO> listAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(this::convertToDTO); // Apply pagination and sorting
    }
}
