package com.tnsif.placement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tnsif.placement.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	List<Student> findByHallTicketNumber(long hallTicketNumber);
}