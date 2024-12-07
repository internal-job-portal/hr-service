package com.ukg.hr_service.repository;

import com.ukg.hr_service.model.HR;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HRRepository extends JpaRepository<HR, Long> {
    Optional<HR> findByEmail(String email);
}
