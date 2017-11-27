package org.rainbow.silence_kingdom.audio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/8/12.
 * Time: 下午2:49.
 * Description:
 */
public class Audio extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(Audio.class);

    private long totalRagePoints;

    private long sampleNum;

    private int currentRagePoint;

    private boolean isRecording;

    private boolean running = true;

    public void astop() {
        running = false;
    }

    public void startRecord() {
        isRecording = true;
    }

    public void stopRecord() {
        isRecording = false;
    }

    public void capture() {
        try {
            AudioFormat AUDIO_FORMAT = new AudioFormat(AudioFormat.Encoding.PCM_UNSIGNED,
                    8000f,
                    8,
                    1,
                    1,
                    8000f,
                    true);

            DataLine.Info targetInfo = new DataLine.Info(TargetDataLine.class, AUDIO_FORMAT);
            TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
            targetLine.open(AUDIO_FORMAT, 1000);
            targetLine.start();

            int numBytesRead;

            while (running) {
                try {
                    byte[] targetData = new byte[targetLine.getBufferSize()];

                    numBytesRead = targetLine.read(targetData, 0, targetData.length);

                    if (numBytesRead == -1 && targetData.length <= 0) {
                        Thread.sleep(100);
                        continue;
                    }

                    int currentRagePoints = 0;
                    for (byte b : targetData) {
                        currentRagePoints += b;
                    }
                    currentRagePoints = currentRagePoints * 100 / 256 + 50 * targetData.length;

                    if (isRecording) {
                        totalRagePoints += currentRagePoints;
                        sampleNum += targetData.length;
                    }
                    currentRagePoint = currentRagePoints / targetData.length;
                    //                logger.info("current rage point, {}", currentRagePoint);
                } catch (Exception e) {
                    logger.error("something bad happens...", e);
                }
            }
        } catch (Exception e) {
            logger.error("capture audio info error", e);
        }
    }

    public int getCurrentRagePoint() {
        return currentRagePoint;
    }

    public int getAverageRagePoint() {
        if (totalRagePoints == 0 || sampleNum == 0) {
            return 0;
        }
        return (int) (totalRagePoints / sampleNum);
    }

    @Override public void run() {
        capture();
    }
}

