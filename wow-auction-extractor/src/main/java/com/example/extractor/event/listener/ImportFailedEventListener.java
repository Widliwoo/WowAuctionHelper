package com.example.extractor.event.listener;

import com.example.extractor.dto.ImportDto;
import com.example.extractor.entity.Import;
import com.example.extractor.entity.ImportStatus;
import com.example.extractor.event.ImportFailedEvent;
import com.example.extractor.repository.ImportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class ImportFailedEventListener implements ApplicationListener<ImportFailedEvent> {

    @Autowired
    private ImportRepository importRepository;

    @Override
    public void onApplicationEvent(ImportFailedEvent event) {
        log.info("#onApplicationEvent import failed.");
        ImportDto dto = event.getDto();
        Import entity = importRepository.findById(dto.getId()).get();
        entity.setStatus(ImportStatus.FAILED);
        entity.setEndDate(LocalDateTime.now());
        importRepository.save(entity);
        log.info("#onApplicationEvent finished.");
    }
}
