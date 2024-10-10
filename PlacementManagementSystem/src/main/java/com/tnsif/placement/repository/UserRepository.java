package com.tnsif.placement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tnsif.placement.model.AppUser;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
}
