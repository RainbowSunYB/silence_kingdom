//package org.rainbow.silence_kingdom.view;
//
//import org.apache.commons.lang3.math.NumberUtils;
//import org.rainbow.silence_kingdom.audio.Audio;
//import org.rainbow.silence_kingdom.conts.ViewType;
//import org.rainbow.silence_kingdom.util.DashboardTask;
//import org.rainbow.silence_kingdom.util.Meta;
//import org.rainbow.silence_kingdom.util.Refresher;
//import org.rainbow.silence_kingdom.util.TimerTask;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import static org.rainbow.silence_kingdom.conts.Constants.META_TOTAL_MINUTES_DEFAULT;
//import static org.rainbow.silence_kingdom.conts.Constants.META_TOTAL_MINUTES_KEY;
//
///**
// * Copyright (c) by Megvii.com.
// * Created by Rainbow Sun.
// * Date: 2017/9/10.
// * Time: 上午7:27.
// * Description:
// */
//public class AudioRecordView1 extends BaseView {
//
//    private static final Logger logger = LoggerFactory.getLogger(AudioRecordView1.class);
//    private static final String TEXT_TEMPLATE = "<html>剩余时间: %d分%d秒<br>平均愤怒值: %d</html>";
//
//    private long startTime = 0;
//    private long endTime = 0;
//    private int remainingSeconds = 0;
//    private int remainingMinutes = NumberUtils.toInt(Meta.getConfig(META_TOTAL_MINUTES_KEY, META_TOTAL_MINUTES_DEFAULT));
//
//    private int averageRagePoint = 0;
//
//    private boolean start;
//    private boolean stop;
//
//    private Audio audio = new Audio();
//    private Refresher refresher = new Refresher();
//
//    private JPanel panel = new JPanel();
//
//    private Dashboard dashBoard;
//    private JLabel textField;
//
//    private JButton startBtn;
//    private JButton endBtn;
//
//    public AudioRecordView1(BaseFrame baseFrame) {
//        super(baseFrame);
//
//        panel.setLayout(new BorderLayout());
//        panel.setPreferredSize(baseFrame.getDimension());
//        panel.setVisible(true);
//
//        JPanel center = new JPanel() {
//            @Override public Insets getInsets() {
//                return new Insets(20, 50, 30, 50);
//            }
//        };
//        BorderLayout centerLayout = new BorderLayout();
//        centerLayout.setHgap(20);
//        center.setLayout(centerLayout);
//
//        textField = new JLabel();
//        textField.setText(compose());
//        dashBoard = new Dashboard.Builder()
//                .from(0)
//                .to(100)
//                .major(10)
//                .minor(1)
//                .value(0)
//                .unit("愤怒指数")
//                .type("semi_circle180")
//                .textColor(Color.black)
//                .valueColor(Color.black)
//                .pointerColor(Color.black)
//                .majorScaleColor(Color.BLACK)
//                .minorScaleColor(Color.DARK_GRAY)
//                .foregroundColor(Color.BLUE)
//                .backgroundColor(Color.WHITE)
//                .size(new Dimension(300, 300))
//                .build();
//        center.add(dashBoard, BorderLayout.CENTER);
//        center.add(textField, BorderLayout.EAST);
//
//        panel.add(center, BorderLayout.CENTER);
//
//        JPanel bottom = new JPanel();
//        bottom.setLayout(new FlowLayout(FlowLayout.CENTER));
//        startBtn = new JButton("开始");
//        startBtn.addActionListener(new ActionListener() {
//            @Override public void actionPerformed(ActionEvent e) {
//                start();
//            }
//        });
//        endBtn = new JButton("结束");
//        endBtn.addActionListener(new ActionListener() {
//            @Override public void actionPerformed(ActionEvent e) {
//                stop();
//            }
//        });
//        bottom.add(startBtn);
//        bottom.add(endBtn);
//        panel.add(bottom, BorderLayout.SOUTH);
//
//        refresher.addTask(new DashboardTask(System.currentTimeMillis(), this));
//        refresher.start();
//
//        audio.start();
//    }
//
//    private String compose() {
//        return String.format(TEXT_TEMPLATE, remainingMinutes, remainingSeconds, averageRagePoint);
//    }
//
//    public void refreshDashBoard() {
//        dashBoard.updateValue(audio.getCurrentRagePoint());
//    }
//
//    public void refreshText() {
//        if (endTime < System.currentTimeMillis()) {
//            stop();
//        }
//
//        int remainingSeconds = (int) ((endTime - System.currentTimeMillis()) / 1000);
//        this.remainingMinutes = remainingSeconds / 60;
//        this.remainingSeconds = remainingSeconds % 60;
//        this.averageRagePoint = audio.getAverageRagePoint();
//        textField.setText(compose());
//        textField.repaint();
//    }
//
//    public void start() {
//        startTime = System.currentTimeMillis();
//        endTime = startTime + 60 * 60 * 1000;
//        audio.startRecord();
//        refresher.addTask(new TimerTask(this));
//    }
//
//    public void stop() {
//        this.stop = true;
//        audio.stopRecord();
//    }
//
//    @Override ViewType getViewType() {
//        return ViewType.AUDIO_RECORD;
//    }
//
//    @Override Container getView() {
//        return panel;
//    }
//}
