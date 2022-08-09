package com.example.extractor.service;

import com.example.extractor.dto.FileDto;
import com.example.extractor.entity.FileEntity;
import com.example.extractor.repository.FileEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FileService {

    @Autowired
    private FileEntityRepository fileEntityRepository;

    private final PageRequest DEFAULT_PAGEABLE =
            PageRequest.of(0, 1, Sort.Direction.DESC, FileEntity.Fields.scanDate);

    public boolean checkRelevance(FileDto dto) {
        Optional<FileEntity> last = fileEntityRepository.findLast(DEFAULT_PAGEABLE);
        if (last.isEmpty()) {
            return true;
        }
        if (last.map(FileDto::from).equals(dto)) {
            return false;
        }
        return true;
    }
}
