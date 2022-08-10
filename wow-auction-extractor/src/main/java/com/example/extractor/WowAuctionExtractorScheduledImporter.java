package com.example.extractor;

import com.example.extractor.dto.ImportDto;
import com.example.extractor.dto.RealmDto;
import com.example.extractor.event.ImportCanceledEvent;
import com.example.extractor.event.ImportFailedEvent;
import com.example.extractor.event.ImportFinishedEvent;
import com.example.extractor.event.ImportStartedEvent;
import com.example.extractor.service.ImportService;
import com.example.extractor.service.LuaExecutor;
import com.example.extractor.service.RealmFacade;
import com.example.extractor.util.ChecksumUtills;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class WowAuctionExtractorScheduledImporter {

    @Autowired
    private LuaExecutor luaExecutor;

    @Autowired
    private RealmFacade realmFacade;

    @Autowired
    private ImportService importService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    /**
     * Задача запускает процесс импорта данных о ценах на аукционах альянас и орды.
     */
    @Scheduled(fixedDelay = 1000 * 60 * 5)
    public void importData() {
        File source = luaExecutor.getWowLuaFile();
        ImportDto importDto = createImportFrom(source);
        eventPublisher.publishEvent(new ImportStartedEvent(this, importDto));
        boolean isRelevant = importService.checkRelevanceFor(importDto);
        if (isRelevant) {
            List<RealmDto> realms = null;
            try {
                realms = luaExecutor.importRealms();
            } catch (JsonProcessingException e) {
                eventPublisher.publishEvent(new ImportFailedEvent(this, importDto));
                log.error("#importRealms error reading json. Reason is {}.", e.getMessage());
                throw new RuntimeException(e);
            }
            realmFacade.saveRealms(realms, importDto);
            eventPublisher.publishEvent(new ImportFinishedEvent(this, importDto));
        } else {
            eventPublisher.publishEvent(new ImportCanceledEvent(this, importDto));
            log.info("#importData interrupted. Reason is: irrelevant file.");
        }
        log.info("#importData execution completed.");
    }

    private ImportDto createImportFrom(File source) {
        ImportDto dto = new ImportDto();
        dto.setStartDate(LocalDateTime.now());
        try {
            dto.setMd5(ChecksumUtills.md5(source));
            dto.setSha256(ChecksumUtills.sha256(source));
        } catch (IOException e) {
            log.error("WowAuctionExtractorScheduledImporter #calculateCheckSums cannot calculate sum for file: {}.Reason is: {}",
                    source.getAbsolutePath(),
                    e.getMessage());
            throw new RuntimeException(e);
        }
        return dto;
    }

}
