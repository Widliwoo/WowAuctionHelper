package com.example.extractor.repository;

import com.example.extractor.entity.Import;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileEntityRepository extends JpaRepository<Import, Long> {

}