package org.rainbow.silence_kingdom.util;

import org.rainbow.silence_kingdom.view.AudioRecordView;

import java.util.Random;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/9/23.
 * Time: 下午3:16.
 * Description:
 */
public class DashboardTask implements Task {

    private static final long REFRESH_INTERVAL = 20;

    private static Random random = new Random(47);

    private long timestamp;
    private AudioRecordView audioRecordView;

    public DashboardTask(long timestamp, AudioRecordView audioRecordView) {
        this.timestamp = timestamp;
        this.audioRecordView = audioRecordView;
    }

    @Override public Task exec() {
        audioRecordView.refreshDashBoard();
        this.timestamp = timestamp + REFRESH_INTERVAL;
        return this;
    }

    @Override public long timestamp() {
        return timestamp;
    }
}
