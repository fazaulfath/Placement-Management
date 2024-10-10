package com.tnsif.placement.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tnsif.placement.controller.CertificateController;
import com.tnsif.placement.dto.CertificateDTO;
import com.tnsif.placement.service.CertificateService;

@SpringBootTest
public class CertificateControllerTest {

    @InjectMocks
    private CertificateController certificateController;

    @Mock
    private CertificateService certificateService;

    private CertificateDTO certificateDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        certificateDTO = new CertificateDTO();
        certificateDTO.setId(1L);
        certificateDTO.setCertificateName("Test Certificate");
        certificateDTO.setIssuingAuthority("Test Authority");
        certificateDTO.setIssueDate("2024-09-30");
        certificateDTO.setDescription("Test Description");
    }

    @Test
    public void testListAll() {
        when(certificateService.listAll()).thenReturn(Arrays.asList(certificateDTO));

        ResponseEntity<List<CertificateDTO>> response = certificateController.listAll();

        verify(certificateService, times(1)).listAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetCertificate() {
        when(certificateService.get(anyLong())).thenReturn(certificateDTO);

        ResponseEntity<CertificateDTO> response = certificateController.get(1L);

        verify(certificateService, times(1)).get(anyLong());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(certificateDTO, response.getBody());
    }

    @Test
    public void testGetCertificate_NotFound() {
        when(certificateService.get(anyLong())).thenReturn(null);

        ResponseEntity<CertificateDTO> response = certificateController.get(1L);

        verify(certificateService, times(1)).get(anyLong());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testAddCertificate() {
        doNothing().when(certificateService).save(any());

        ResponseEntity<Void> response = certificateController.addCertificate(certificateDTO);

        verify(certificateService, times(1)).save(any());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testUpdateCertificate() {
        // Mock the updateCertificate method
        when(certificateService.updateCertificate(anyLong(), any(CertificateDTO.class))).thenReturn(certificateDTO);

        ResponseEntity<Void> response = certificateController.update(1L, certificateDTO);

        // Verify that updateCertificate was called
        verify(certificateService, times(1)).updateCertificate(anyLong(), any(CertificateDTO.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteCertificate() {
        doNothing().when(certificateService).delete(anyLong());

        ResponseEntity<Void> response = certificateController.delete(1L);

        verify(certificateService, times(1)).delete(anyLong());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testListAllPaginated() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<CertificateDTO> page = new PageImpl<>(Collections.singletonList(certificateDTO));

        when(certificateService.listAllPaginated(pageable)).thenReturn(page);

        ResponseEntity<Page<CertificateDTO>> response = certificateController.listAllPaginated(pageable);

        verify(certificateService, times(1)).listAllPaginated(pageable);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getTotalElements());
    }
}
