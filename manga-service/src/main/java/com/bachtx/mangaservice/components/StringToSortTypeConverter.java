package com.bachtx.mangaservice.components;

import com.bachtx.wibucommon.enums.ESortType;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSortTypeConverter implements Converter<String, ESortType> {
    @Override
    public ESortType convert(@Nullable String source) {
        try {
            if(source == null){
                return ESortType.ASC;
            }
            return ESortType.valueOf(source.toUpperCase());
        } catch(Exception e) {
            return ESortType.ASC;
        }
    }
}
