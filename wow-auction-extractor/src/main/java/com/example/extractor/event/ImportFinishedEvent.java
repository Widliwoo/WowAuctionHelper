package com.example.extractor.event;

import com.example.extractor.dto.ImportDto;

public class ImportFinishedEvent extends ImportEvent {
    public ImportFinishedEvent(Object source, ImportDto dto) {
        super(source, dto);
    }
}
