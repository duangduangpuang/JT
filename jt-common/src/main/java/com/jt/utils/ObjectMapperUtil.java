package com.jt.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String toJSON(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static <T> T toObj(String json,Class<T> targetClass){
        T t=null;
        try {
            t = MAPPER.readValue(json,targetClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return t;
    }
}
