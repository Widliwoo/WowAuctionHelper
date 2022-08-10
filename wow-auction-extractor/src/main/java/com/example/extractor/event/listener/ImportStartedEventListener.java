package com.example.extractor.event.listener;

import com.example.extractor.dto.ImportDto;
import com.example.extractor.entity.Import;
import com.example.extractor.entity.ImportStatus;
import com.example.extractor.event.ImportStartedEvent;
import com.example.extractor.repository.ImportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class ImportStartedEventListener implements ApplicationListener<ImportStartedEvent> {

    @Autowired
    private ImportRepository importRepository;

    @Override
    public void onApplicationEvent(ImportStartedEvent event) {
        log.info("#onApplicationEvent started. StartDate is {}", event.getDto().getStartDate());
        ImportDto dto = event.getDto();
        Import entity = new Import();
        entity.setMd5(dto.getMd5());
        entity.setSha256(dto.getSha256());
        entity.setStartDate(LocalDateTime.now());
        entity.setStatus(ImportStatus.STARTED);
        Import save = importRepository.save(entity);
        dto.setId(save.getId());
        log.info("#onApplicationEvent finished. Created import id is {}", save.getId());
    }
}
