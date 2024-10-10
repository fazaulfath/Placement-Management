package com.tnsif.placement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tnsif.placement.dto.CollegeDTO;
import com.tnsif.placement.model.College;
import com.tnsif.placement.repository.CollegeRepository;
import com.tnsif.placement.service.CollegeService;

@RestController
@RequestMapping("/colleges")
public class CollegeController {

    @Autowired
    private CollegeService collegeService;

    @GetMapping
    public ResponseEntity<List<CollegeDTO>> listAll() {
        List<CollegeDTO> colleges = collegeService.listAll();
        return new ResponseEntity<>(colleges, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollegeDTO> get(@PathVariable Long id) {
        CollegeDTO college = collegeService.get(id);
        return college != null ? new ResponseEntity<>(college, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<College> add(@RequestBody College college) {
        College savedCollege = collegeService.addCollege(college);
        return new ResponseEntity<>(savedCollege, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody CollegeDTO collegeDTO) {
        // Check if the college exists using the service method
        if (!collegeService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 if not found
        }
        collegeDTO.setId(id);
        collegeService.updateCollege(collegeService.convertToEntity(collegeDTO)); // Use the update method
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<College> searchCollege(@PathVariable long id) {
        College college = collegeService.searchCollege(id);
        return college != null ? new ResponseEntity<>(college, HttpStatus.OK) 
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = collegeService.deleteCollege(id);
        return deleted ? ResponseEntity.noContent().build() // Returns 204 No Content
                : ResponseEntity.notFound().build(); // Returns 404 Not Found
    }

    @GetMapping("/page")
    public ResponseEntity<Page<CollegeDTO>> listAllPaginated(Pageable pageable) {
        Page<CollegeDTO> colleges = collegeService.listAllPaginated(pageable);
        return new ResponseEntity<>(colleges, HttpStatus.OK);
    }
}
