package com.example.extractor.event;

import com.example.extractor.dto.ImportDto;

public class ImportStartedEvent extends ImportEvent {
    public ImportStartedEvent(Object source, ImportDto dto) {
        super(source, dto);
    }
}
