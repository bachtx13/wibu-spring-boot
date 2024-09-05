package com.bachtx.mangaservice.components;

import com.bachtx.wibucommon.enums.ERecordStatus;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToRecordStatusTypeConverter implements Converter<String, ERecordStatus> {
    @Override
    public ERecordStatus convert(@Nullable String source) {
        try {
            if(source == null){
                return ERecordStatus.ENABLED;
            }
            return ERecordStatus.valueOf(source.toUpperCase());
        } catch(Exception e) {
            return ERecordStatus.ENABLED;
        }
    }
}
