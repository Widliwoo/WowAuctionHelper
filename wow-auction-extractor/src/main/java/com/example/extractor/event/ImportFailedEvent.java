package com.example.extractor.event;

import com.example.extractor.dto.ImportDto;

public class ImportFailedEvent extends ImportEvent {
    public ImportFailedEvent(Object source, ImportDto dto) {
        super(source, dto);
    }
}
