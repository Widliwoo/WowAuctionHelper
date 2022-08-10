package com.example.extractor.event.listener;

import com.example.extractor.dto.ImportDto;
import com.example.extractor.entity.Import;
import com.example.extractor.entity.ImportStatus;
import com.example.extractor.event.ImportFinishedEvent;
import com.example.extractor.repository.ImportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class ImportFinishedEventListener implements ApplicationListener<ImportFinishedEvent> {

    @Autowired
    private ImportRepository importRepository;

    @Override
    public void onApplicationEvent(ImportFinishedEvent event) {
        log.info("#onApplicationEvent started.");
        ImportDto dto = event.getDto();
        Import entity = importRepository.findById(dto.getId()).get();
        entity.setEndDate(LocalDateTime.now());
        entity.setStatus(ImportStatus.FINISHED);
        importRepository.save(entity);
        log.info("#onApplicationEvent finished.");
    }
}
