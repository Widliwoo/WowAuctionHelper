package com.example.extractor.dto;

import com.example.extractor.entity.ImportStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ImportDto {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ImportStatus status;
    private String sha256;
    private String md5;
}
