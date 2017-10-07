package org.rainbow.silence_kingdom.test;

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

        //        GroupLayout groupLayout = new GroupLayout(center);
        //        center.setLayout(groupLayout);
        //
        //        GroupLayout.SequentialGroup hGroup = groupLayout.createSequentialGroup();
        //        groupLayout.setHorizontalGroup(hGroup);
        //
        //        GroupLayout.SequentialGroup vGroup = groupLayout.createSequentialGroup();
        //        groupLayout.setVerticalGroup(vGroup);
        //
        //        hGroup.addGap(5);
        //        hGroup.addGroup(groupLayout.createParallelGroup()
        //                .addComponent(dashBoard, GroupLayout.Alignment.CENTER, 400, 400, Integer.MAX_VALUE));
        //        hGroup.addGap(5);
        //        hGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
        //                .addComponent(textField, 200, 200, Integer.MAX_VALUE));
        //
        //        vGroup.addGap(10);
        //        vGroup.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        //                .addComponent(textField, 100, 100, 100));
        //        vGroup.addGap(10);
        //        vGroup.addGroup(groupLayout.createParallelGroup().addComponent(audioAverageValueField, 100, 100, 100));

    }
}
