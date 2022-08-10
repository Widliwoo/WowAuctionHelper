package com.example.extractor.event.listener;

import com.example.extractor.dto.ImportDto;
import com.example.extractor.entity.Import;
import com.example.extractor.entity.ImportStatus;
import com.example.extractor.event.ImportProcessingEvent;
import com.example.extractor.repository.ImportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ImportProcessingEventListener implements ApplicationListener<ImportProcessingEvent> {

    @Autowired
    private ImportRepository importRepository;

    @Override
    public void onApplicationEvent(ImportProcessingEvent event) {
        log.info("#onApplicationEvent processing import.");
        ImportDto dto = event.getDto();
        Import entity = importRepository.findById(dto.getId()).get();
        entity.setStatus(ImportStatus.PROCESSING);
        importRepository.save(entity);
        log.info("#onApplicationEvent finished.");
    }
}
