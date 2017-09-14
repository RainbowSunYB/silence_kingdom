package org.rainbow.silence_kingdom;

import org.rainbow.silence_kingdom.test.Audio;
import org.rainbow.silence_kingdom.test.Description;
import org.rainbow.silence_kingdom.test.WaveRecord;

import javax.swing.*;
import java.awt.*;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/8/12.
 * Time: 下午3:39.
 * Description:
 */
public class Main {

    public static final Font HUA_WEN_XIN_WEI = new Font("华文新魏", Font.BOLD, 40);

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame();

        Audio audio = new Audio();
        WaveRecord waveRecord = new WaveRecord(audio);
        frame.add(waveRecord, BorderLayout.CENTER);

        Description description = new Description("录音中");
        frame.add(description, BorderLayout.NORTH);

        frame.setSize(1000, 500);
        frame.setTitle("录音机");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

        new Thread(audio).start();
        new Thread(waveRecord).start();
    }
}
