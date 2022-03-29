package com.redeyefrog.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    /**
     * Object to Json
     * @param obj
     * @return
     * @throws JsonProcessingException
     */
    public static String toJson(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    /**
     * Json to Object
     * @param json
     * @param clazz
     * @param <T>
     * @return
     * @throws JsonProcessingException
     */
    public static <T> T toBean(String json, Class<T> clazz) throws JsonProcessingException {
        return mapper.readValue(json, clazz);
    }

    /**
     * Object to Object
     * @param obj
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T convert(Object obj, Class<T> clazz) {
        return mapper.convertValue(obj, clazz);
    }

}
