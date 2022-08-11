package com.example.extractor;

import com.example.extractor.dto.ImportResult;
import com.example.extractor.entity.Import;
import com.example.extractor.service.ImportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@Slf4j
public class WowAuctionExtractorScheduledExporter {

    @Autowired
    private WebClient webClient;

    @Autowired
    private ImportService importService;

    @Scheduled(fixedDelay = 1000 * 60 * 5, initialDelay = 1000)
    public void exportData() {
        List<Import> unsentImports = importService.getUnsentImports();
        for (Import i : unsentImports) {
            webClient.post()
                    .uri("/import")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(i)
                    .retrieve()
                    .toEntity(ImportResult.class)
                    .subscribe(this::onSuccess, this::onError);
        }
    }

    private void onSuccess(ResponseEntity<ImportResult> dtoResponse) {
        ImportResult dto = dtoResponse.getBody();
        if (dto != null && dtoResponse.getStatusCode().equals(HttpStatus.OK) && dto.getSuccess()) {
            importService.setSent(dto.getImportId());
            log.info("#exportData chunk processed. Processed id is {}.", dto.getImportId());
        } else {
            log.error("#exportData failed to process chunk! Response status is {}", dtoResponse.getStatusCode());
        }
    }

    private void onError(Throwable error) {
        log.error("#exportData failed. Reason is: {}", error.getMessage());
    }


}
