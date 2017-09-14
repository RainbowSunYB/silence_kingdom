package org.rainbow.silence_kingdom.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/8/12.
 * Time: 下午3:16.
 * Description:
 */
public class WaveRecord extends JPanel implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(WaveRecord.class);

    private Audio audio;

    public WaveRecord(Audio audio) {
        this.audio = audio;
    }

    public void paint(Graphics g) {
        super.paint(g);
        byte[] audioData = audio.getAudioBuffer();
        g.fillRect(this.getX(), this.getY(), 1000, 380);
        if (audioData != null) {
            //            logger.info("abc");
            g.drawLine(this.getWidth() / 256, 700, this.getWidth() / 256, 700);
            for (int i = 0; i < audioData.length - 1; i++) {
                g.setColor(Color.RED);
                g.drawLine(i,
                        (int) audioData[i] + 200,
                        (i + 1),
                        (int) audioData[i + 1] + 200);
            }
        }
    }

    @Override public void run() {
        while (true) {
            try {
                Thread.sleep(50);
                long start = System.currentTimeMillis();
                paint(getGraphics());
                logger.info("draw time, {}", System.currentTimeMillis() - start);
            } catch (Exception e) {
                logger.error("wave record error", e);
            }
        }
    }
}
