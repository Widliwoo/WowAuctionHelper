package com.example.extractor.event.listener;

import com.example.extractor.dto.ImportDto;
import com.example.extractor.entity.Import;
import com.example.extractor.entity.ImportStatus;
import com.example.extractor.event.ImportCanceledEvent;
import com.example.extractor.repository.ImportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class ImportCanceledEventListener implements ApplicationListener<ImportCanceledEvent> {

    @Autowired
    private ImportRepository importRepository;

    @Override
    public void onApplicationEvent(ImportCanceledEvent event) {
        log.info("#onApplicationEvent canceling import...");
        ImportDto dto = event.getDto();
        Import entity = importRepository.findById(dto.getId()).get();
        entity.setStatus(ImportStatus.CANCELED);
        entity.setEndDate(LocalDateTime.now());
        importRepository.save(entity);
        log.info("#onApplicationEvent import canceled.");
    }
}
