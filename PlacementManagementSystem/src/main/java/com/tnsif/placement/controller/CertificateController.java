package com.tnsif.placement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tnsif.placement.dto.CertificateDTO;
import com.tnsif.placement.service.CertificateService;

@RestController
@RequestMapping("/certificates")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @GetMapping
    public ResponseEntity<List<CertificateDTO>> listAll() {
        List<CertificateDTO> certificates = certificateService.listAll();
        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificateDTO> get(@PathVariable Long id) {
        CertificateDTO certificate = certificateService.get(id);
        return certificate != null 
            ? new ResponseEntity<>(certificate, HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Void> addCertificate(@RequestBody CertificateDTO certificateDTO) {
        if (certificateDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        certificateService.save(certificateDTO); // This will remain as void
        return new ResponseEntity<>(HttpStatus.CREATED); // Return created status without an object
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody CertificateDTO certificateDTO) {
        System.out.println("Received update request for ID: " + id);
        System.out.println("CertificateDTO: " + certificateDTO); // This should log the body content.
        
        if (certificateDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CertificateDTO updatedCertificate = certificateService.updateCertificate(id, certificateDTO);
        return updatedCertificate != null ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        certificateService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<CertificateDTO>> listAllPaginated(Pageable pageable) {
        Page<CertificateDTO> certificates = certificateService.listAllPaginated(pageable);
        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }
}
