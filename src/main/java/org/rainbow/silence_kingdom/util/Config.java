package org.rainbow.silence_kingdom.util;

import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/9/11.
 * Time: 下午7:01.
 * Description:
 */
public class Config {

    private static final Logger logger = LoggerFactory.getLogger(Config.class);

    private static final Properties PROPERTIES = new Properties();

    private static String CONFIG_FILE_PATH;

    static {
        try {
            CONFIG_FILE_PATH = System.getProperty("silence.kingdom.config.file.path");
            InputStream in = Config.class.getResourceAsStream(CONFIG_FILE_PATH);
            PROPERTIES.load(in);
        } catch (Exception e) {
            logger.error("load config error", e);
        }
    }

    public static void printConfigs() {
        Set<String> keys = Sets.newTreeSet(PROPERTIES.stringPropertyNames());
        logger.info("==========");
        for (String name : keys) {
            logger.info("| config | {} = {} |", name, PROPERTIES.getProperty(name));
        }
        logger.info("==========");
    }

    public static String getConfig(String key, String defaultValue) {
        String value = (String) PROPERTIES.get(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }
}
