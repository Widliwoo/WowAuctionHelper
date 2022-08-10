package com.example.extractor.repository;

import com.example.extractor.entity.Import;
import com.example.extractor.entity.ImportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ImportRepository extends JpaRepository<Import, Long> {
    @Query("select i from Import i where i.startDate in (select max(i2.startDate) from Import i2 where i2.status = :status)")
    Import findLastByStatus(@Param("status") ImportStatus status);
}
