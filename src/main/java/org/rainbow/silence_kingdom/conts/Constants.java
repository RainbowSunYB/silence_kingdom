package org.rainbow.silence_kingdom.conts;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/9/10.
 * Time: 上午7:31.
 * Description:
 */
public class Constants {
    public static final byte HISTORY_SUCCESS = 1;
    public static final byte HISTORY_FAIL = 0;
    public static final byte HISTORY_COMPLETE = 1;
    public static final byte HISTORY_INCOMPLETE = 0;

    public static final String META_VOICE_THRESHOLD_KEY = "voice.threshold";
    public static final String META_VOICE_THRESHOLD_DEFAULT = "20";

    public static final String META_TOTAL_MINUTES_KEY = "total.minutes";
    public static final String META_TOTAL_MINUTES_DEFAULT = "45";

    public static final String META_CARD_DIR_PATH_KEY = "card.dir.path";
    public static final String META_CARD_DIR_PATH_DEFAULT = System.getProperty("PWD") + "/img";

    public static final String META_UNACQUIRED_CARD_IMAGE_KEY = "unacquired.card.image.path";
    public static final String META_UNACQUIRED_CARD_IMAGE_DEFAULT = "img/default.jpg";

    public static final String META_OTHER_KEY = "other";
    public static final String META_OTHER_DEFAULT = "other";
}
