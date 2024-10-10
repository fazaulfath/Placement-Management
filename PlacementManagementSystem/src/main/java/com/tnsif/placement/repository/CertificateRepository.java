package com.tnsif.placement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tnsif.placement.model.Certificate;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}
