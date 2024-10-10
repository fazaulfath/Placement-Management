package com.tnsif.placement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tnsif.placement.dto.UserDTO;
import com.tnsif.placement.model.AppUser;
import com.tnsif.placement.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository repository;

    // DTO Conversion Methods
    private UserDTO convertToDTO(AppUser user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }

    private AppUser convertToEntity(UserDTO dto) {
        AppUser user = new AppUser();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        return user;
    }

    public List<UserDTO> listAll() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO get(Long id) {
        AppUser user = repository.findById(id).orElse(null);
        return (user != null) ? convertToDTO(user) : null;
    }

    public void save(UserDTO dto) {
        AppUser user = convertToEntity(dto);
        
        // Add password encoding here
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode("your-raw-password-here")); // Use the actual password from your DTO or input

        repository.save(user);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<UserDTO> listAllPaginated(Pageable pageable) {
        return repository.findAll(pageable).map(this::convertToDTO);
    }
}
