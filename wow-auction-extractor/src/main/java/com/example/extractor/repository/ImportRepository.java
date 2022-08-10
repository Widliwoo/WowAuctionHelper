package com.example.extractor.repository;

import com.example.extractor.entity.Import;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ImportRepository extends JpaRepository<Import, Long> {
    Import findOneByStartDate(LocalDateTime startDate);
}
