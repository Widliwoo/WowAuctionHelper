package com.example.extractor.event;

import com.example.extractor.dto.ImportDto;

public class ImportProcessingEvent extends ImportEvent {
    public ImportProcessingEvent(Object source, ImportDto dto) {
        super(source, dto);
    }
}
