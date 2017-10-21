package org.rainbow.silence_kingdom.util;

import org.rainbow.silence_kingdom.view.AudioRecordView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/9/23.
 * Time: 下午3:34.
 * Description:
 */
public class TimerTask implements Task {

    private static final Logger logger = LoggerFactory.getLogger(TimerTask.class);

    private static final long REFRESH_INTERVAL = 1000;

    private long timestamp;
    private AudioRecordView audioRecordView;

    public TimerTask(AudioRecordView audioRecordView) {
        this.timestamp = System.currentTimeMillis();
        this.audioRecordView = audioRecordView;
    }

    @Override public Task exec() {
        if (audioRecordView.isStopped()) {
            return null;
        }
        audioRecordView.refreshText();

        timestamp = timestamp + REFRESH_INTERVAL;
        return this;
    }

    @Override public long timestamp() {
        return timestamp;
    }
}
