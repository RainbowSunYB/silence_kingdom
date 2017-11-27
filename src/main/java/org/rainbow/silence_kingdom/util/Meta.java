package org.rainbow.silence_kingdom.util;

import org.apache.logging.log4j.util.Strings;
import org.rainbow.silence_kingdom.models.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/9/24.
 * Time: 上午8:02.
 * Description:
 */
public class Meta {

    private static final Logger logger = LoggerFactory.getLogger(Meta.class);

    private static final String CARD_RESOURCE_DIR_PREFIX = "card-";

    public static File IMG_DIR;

    public static void loadCards() {
        try {
            IMG_DIR = new File("img/");
            File[] subDirs = IMG_DIR.listFiles();
            if (subDirs == null || subDirs.length == 0) {
                return;
            }

            for (File dir : subDirs) {
                if (dir.isDirectory() && dir.getName().startsWith(CARD_RESOURCE_DIR_PREFIX)) {
                    Card card = new Card();
                    card.setCardName(dir.getName().replaceFirst(CARD_RESOURCE_DIR_PREFIX, Strings.EMPTY));
                    card.setImagePath(dir.getAbsolutePath() + "/image.jpg");
                    card.setSmallImagePath(dir.getAbsolutePath() + "/small-image.jpg");
                    logger.info("{}", card.getImagePath());

                    List<Map<String, Object>> result = DB.query(String.format("select id from card where card_name = '%s'", card.getCardName()));
                    boolean existed = result != null && result.size() > 0;
                    if (existed) {
                        DB.exec(String
                                .format("update card set `image_path` = '%s', `small_image_path` = '%s' where card_name = '%s'", card.getImagePath(), card.getSmallImagePath(), card.getCardName()));
                    } else {
                        DB.exec(String.format("insert into card (`card_name`, `image_path`, `small_image_path`) values ('%s', '%s', '%s')", card.getCardName(), card.getImagePath(),
                                card.getSmallImagePath()));
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    public static String getConfig(String key, String defaultValue) {
        List<Map<String, Object>> result = DB.query(String.format("select key, value from config where key = '%s'", key));
        if (result == null || result.size() == 0) {
            return defaultValue;
        }
        return (String) result.get(0).get("value");
    }

    public static void addConfig(String key, String value) {
        DB.exec(String.format("replace into config (key, value) values ('%s', '%s')", key, value));
    }
}
