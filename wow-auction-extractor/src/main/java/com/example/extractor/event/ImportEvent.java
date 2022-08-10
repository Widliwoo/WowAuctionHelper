package com.example.extractor.event;

import com.example.extractor.dto.ImportDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public abstract class ImportEvent extends ApplicationEvent {
    private ImportDto dto;

    public ImportEvent(Object source, ImportDto dto) {
        super(source);
        this.dto = dto;
    }
}
