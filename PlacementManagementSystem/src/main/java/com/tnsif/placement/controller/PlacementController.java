package com.tnsif.placement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tnsif.placement.dto.PlacementDTO;
import com.tnsif.placement.service.PlacementService;

@RestController
@RequestMapping("/placements")
public class PlacementController {

    @Autowired
    private PlacementService placementService;

    // List all placements
    @GetMapping
    public ResponseEntity<List<PlacementDTO>> listAll() {
        List<PlacementDTO> placements = placementService.listAll();
        return new ResponseEntity<>(placements, HttpStatus.OK);
    }

    // Get a single placement by ID
    @GetMapping("/{id}")
    public ResponseEntity<PlacementDTO> get(@PathVariable Long id) {
        PlacementDTO placement = placementService.get(id);
        return placement != null ? new ResponseEntity<>(placement, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Add a new placement
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody PlacementDTO placementDTO) {
        placementService.save(placementDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Update an existing placement by ID
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody PlacementDTO placementDTO) {
        if (!placementService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        placementDTO.setId(id);  // Make sure the ID is set for the update operation
        placementService.save(placementDTO);  // Save will either update or insert
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Delete a placement by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!placementService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        placementService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // List all placements with pagination
    @GetMapping("/page")
    public ResponseEntity<Page<PlacementDTO>> listAllPaginated(Pageable pageable) {
        Page<PlacementDTO> placements = placementService.listAllPaginated(pageable);
        return new ResponseEntity<>(placements, HttpStatus.OK);
    }
}
