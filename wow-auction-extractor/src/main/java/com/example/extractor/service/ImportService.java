package com.example.extractor.service;

import com.example.extractor.dto.ImportDto;
import com.example.extractor.entity.Import;
import com.example.extractor.entity.ImportStatus;
import com.example.extractor.repository.ImportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImportService {

    private final PageRequest FIRST_BY_DATE =
            PageRequest.of(0, 2, Sort.Direction.DESC, Import.Fields.startDate);
    @Autowired
    private ImportRepository importRepository;

    public boolean checkRelevanceFor(ImportDto importDto) {
        Import entity = importRepository.findLastByStatus(ImportStatus.FINISHED);
        if (entity == null) {
            return true;
        }
        return !isEqualHashesFor(entity, importDto);
    }

    private boolean isEqualHashesFor(Import imp, ImportDto dto) {
        return imp.getMd5().equals(dto.getMd5()) && imp.getSha256().equals(dto.getSha256());
    }

    public List<Import> getUnsentImports() {
        return importRepository.findAllBySentIsFalseAndStatusIs(ImportStatus.FINISHED);
    }

    public void setSent(Long importId) {
        Import entity = importRepository.getReferenceById(importId);
        entity.setSent(Boolean.TRUE);
        importRepository.save(entity);
    }
}
