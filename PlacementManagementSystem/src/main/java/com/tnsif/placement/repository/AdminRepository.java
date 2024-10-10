package com.tnsif.placement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tnsif.placement.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
}
