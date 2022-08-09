package com.example.extractor.repository;

import com.example.extractor.entity.FileEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileEntityRepository extends JpaRepository<FileEntity, Long> {

    @Query("select fe from FileEntity fe")
    Optional<FileEntity> findLast(Pageable pageable);
}
