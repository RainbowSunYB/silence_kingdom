package org.rainbow.silence_kingdom.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/8/2.
 * Time: 下午3:15.
 * Description:
 */
public class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule simpleModule = new SimpleModule();
        MAPPER.registerModule(simpleModule);
    }

    public static String serialize(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("jackson format error: {}" + obj, e);
            return null;
        }
    }

    public static <T> T deserialize(String json, Class<T> type) {
        try {
            return (T) MAPPER.readValue(json, type);
        } catch (IOException e) {
            logger.error("jackson parse error : {}, {}", json, type, e);
            return null;
        }
    }

    public static <T> T deserialize(String json, TypeReference<T> valueTypeRef) {
        try {
            return (T) MAPPER.readValue(json, valueTypeRef);
        } catch (IOException e) {
            logger.error("jackson parse error : {}, {}", json, valueTypeRef, e);
            return null;
        }
    }
}
