package org.rainbow.silence_kingdom.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/8/12.
 * Time: 下午2:49.
 * Description:
 */
public class Audio implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Audio.class);

    private final ReentrantLock audioBufferLock = new ReentrantLock();

    private Condition condition = audioBufferLock.newCondition();

    private final LinkedBlockingQueue<byte[]> queue = new LinkedBlockingQueue<>();

    private byte audioBuffer[] = null;

    public void await() {
        try {
            condition.wait();
        } catch (InterruptedException e) {
            logger.error("wait error", e);
        }
    }

    public byte[] getAudioBuffer() {
        audioBufferLock.lock();
        try {
            return Arrays.copyOf(audioBuffer, audioBuffer.length);
        } finally {
            audioBufferLock.unlock();
        }
    }

    private void setAudioBuffer(byte[] audioBuffer) {
        audioBufferLock.lock();
        try {
            this.audioBuffer = audioBuffer;
        } finally {
            audioBufferLock.unlock();
        }
    }

    public void capture() {
        try {
            AudioFormat AUDIO_FORMAT = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
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

                //                logger.info("receive audio data size {}", numBytesRead);

                if (numBytesRead == -1) {
                    break;
                }

                setAudioBuffer(targetData);
            }
        } catch (Exception e) {
            logger.error("capture audio info error", e);
        }
    }

    @Override public void run() {
        capture();
    }
}
