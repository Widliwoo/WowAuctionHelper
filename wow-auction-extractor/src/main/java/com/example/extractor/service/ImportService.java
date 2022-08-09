package com.example.extractor.service;

import com.example.extractor.dto.ImportDto;
import com.example.extractor.entity.Import;
import com.example.extractor.repository.FileEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ImportService {

    private final PageRequest FIRST_BY_DATE =
            PageRequest.of(0, 1, Sort.Direction.DESC, Import.Fields.startDate);
    @Autowired
    private FileEntityRepository importRepository;

    public boolean checkRelevanceFor(ImportDto importDto) {
        Page<Import> page = importRepository.findAll(FIRST_BY_DATE);
        if (page.isEmpty()) {
            return true;
        }
        Import freshImport = page.iterator().next();
        return !isEqualHashesFor(freshImport, importDto);
    }

    private boolean isEqualHashesFor(Import imp, ImportDto dto) {
        return imp.getMd5().equals(dto.getMd5()) && imp.getSha256().equals(dto.getSha256());
    }
}
