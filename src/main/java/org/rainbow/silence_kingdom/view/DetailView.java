package org.rainbow.silence_kingdom.view;

import org.rainbow.silence_kingdom.conts.ViewType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/9/17.
 * Time: 下午5:11.
 * Description:
 */
public class DetailView extends BaseView implements ActionListener {

    private JPanel panel = new JPanel();

    public DetailView(BaseFrame baseFrame) {
        super(baseFrame);

        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(baseFrame.getDimension());
        panel.setVisible(true);

        ImageIcon image = new ImageIcon("img/sample.jpg");
        JButton imgButton1 = new JButton(image);
        imgButton1.setBounds(new Rectangle(imgButton1.getWidth(), imgButton1.getHeight()));
        imgButton1.setVisible(true);
        imgButton1.addActionListener(this);
        panel.add(imgButton1, BorderLayout.CENTER);
    }

    @Override public ViewType getViewType() {
        return ViewType.DETAIL;
    }

    @Override public Container getView() {
        return panel;
    }

    @Override public void actionPerformed(ActionEvent e) {
        baseFrame.viewSwitch(new AudioRecordView(baseFrame));
    }
}
