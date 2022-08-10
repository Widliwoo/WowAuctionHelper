package com.example.extractor.event;

import com.example.extractor.dto.ImportDto;

public class ImportCanceledEvent extends ImportEvent {
    public ImportCanceledEvent(Object source, ImportDto dto) {
        super(source, dto);
    }
}
