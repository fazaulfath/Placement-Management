package com.tnsif.placement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tnsif.placement.model.College; 

@Repository
public interface CollegeRepository extends JpaRepository<College, Long> {
	College findByCollegeName(String collegeName);
}
