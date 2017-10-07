package org.rainbow.silence_kingdom.view;

import org.rainbow.silence_kingdom.conts.ViewType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/9/10.
 * Time: 上午7:27.
 * Description:
 */
public class ListView extends BaseView implements ActionListener {

    private JScrollPane pane;

    public ListView(BaseFrame baseFrame) {
        super(baseFrame);

        JPanel panel = new JPanel();

        ImageIcon image = new ImageIcon("img/sample.jpg");
        JButton imgButton1 = new JButton(image);
        imgButton1.setBounds(new Rectangle(imgButton1.getWidth(), imgButton1.getHeight()));
        imgButton1.addActionListener(this);

        JButton imgButton2 = new JButton(image);
        imgButton2.setBounds(new Rectangle(imgButton2.getWidth(), imgButton2.getHeight()));

        JButton imgButton3 = new JButton(image);
        imgButton3.setBounds(new Rectangle(imgButton3.getWidth(), imgButton3.getHeight()));

        JButton imgButton4 = new JButton(image);
        imgButton4.setBounds(new Rectangle(imgButton4.getWidth(), imgButton4.getHeight()));

        JButton imgButton5 = new JButton(image);
        imgButton5.setBounds(new Rectangle(imgButton5.getWidth(), imgButton5.getHeight()));

        JButton imgButton6 = new JButton(image);
        imgButton6.setBounds(new Rectangle(imgButton6.getWidth(), imgButton6.getHeight()));

        GroupLayout groupLayout = new GroupLayout(panel);
        panel.setLayout(groupLayout);

        GroupLayout.SequentialGroup hGroup = groupLayout.createSequentialGroup();
        groupLayout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = groupLayout.createSequentialGroup();
        groupLayout.setVerticalGroup(vGroup);

        hGroup.addGap(5);
        hGroup.addGroup(groupLayout.createParallelGroup().addComponent(imgButton1).addComponent(imgButton4));
        hGroup.addGap(5);
        hGroup.addGroup(groupLayout.createParallelGroup().addComponent(imgButton2).addComponent(imgButton5));
        hGroup.addGap(5);
        hGroup.addGroup(groupLayout.createParallelGroup().addComponent(imgButton3).addComponent(imgButton6));

        vGroup.addGap(10);
        vGroup.addGroup(groupLayout.createParallelGroup().addComponent(imgButton1).addComponent(imgButton2).addComponent(imgButton3));
        vGroup.addGap(10);
        vGroup.addGroup(groupLayout.createParallelGroup().addComponent(imgButton4).addComponent(imgButton5).addComponent(imgButton6));

        pane = new JScrollPane(panel);
        pane.setPreferredSize(baseFrame.getDimension());
        pane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        pane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        pane.setVisible(true);
    }

    @Override public void actionPerformed(ActionEvent e) {
        baseFrame.viewSwitch(new DetailView(baseFrame));
    }

    @Override public ViewType getViewType() {
        return ViewType.LIST;
    }

    @Override public Container getView() {
        return pane;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setVisible(true);

        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int x = (int) (toolkit.getScreenSize().getWidth() - frame.getWidth()) / 2;
        int y = (int) (toolkit.getScreenSize().getHeight() - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        frame.setLayout(new BorderLayout());
//        frame.add((new ListView()).getView());
    }
}
