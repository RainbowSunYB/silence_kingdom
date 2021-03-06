package org.rainbow.silence_kingdom.test;

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

    public void capture() {
        try {
            AudioFormat AUDIO_FORMAT = new AudioFormat(AudioFormat.Encoding.PCM_UNSIGNED,
                    8000f,
                    8,
                    1,
                    1,
                    8000f,
                    false);

            DataLine.Info targetInfo = new DataLine.Info(TargetDataLine.class, AUDIO_FORMAT);
            TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
            targetLine.open(AUDIO_FORMAT, 1000);
            targetLine.start();

            int numBytesRead;

            while (true) {
                byte[] targetData = new byte[targetLine.getBufferSize()];

                numBytesRead = targetLine.read(targetData, 0, targetData.length);

                if (numBytesRead == -1) {
                    break;
                }

                int currentRagePoints = 0;
                for (byte b : targetData) {
                    currentRagePoints += b;
                }

                currentRagePoint = currentRagePoints / targetData.length;
                totalRagePoints += currentRagePoints;
                sampleNum += targetData.length;

//                setAudioBuffer(targetData);
            }
        } catch (Exception e) {
            logger.error("capture audio info error", e);
        }
    }

    public int getCurrentRagePoint() {
        return currentRagePoint;
    }

    @Override public void run() {
        capture();
    }
}
