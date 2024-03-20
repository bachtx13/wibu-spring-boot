package org.bachtx.wibuspringboot.utils;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Component
public class HashMapUtil {
    public Map<String, Object> objectToHashMap(Object object) {
        try {
            return convertUsingReflection(object);
        } catch (IllegalAccessException ex) {
            return new HashMap<>();
        }
    }

    private Map<String, Object> convertUsingReflection(Object object) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(object));
        }
        return map;
    }
}
