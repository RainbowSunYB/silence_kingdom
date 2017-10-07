package org.rainbow.silence_kingdom.test;

import javax.swing.*;
import java.awt.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/9/11.
 * Time: 下午7:21.
 * Description:
 */
public class MultiPanel {

    public static void main(String[] args) throws InterruptedException {
        MultiPanel multiPanel = new MultiPanel();
    }

    public MultiPanel() throws InterruptedException {
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setSize(800, 600);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int x = (int) (toolkit.getScreenSize().getWidth() - frame.getWidth()) / 2;
        int y = (int) (toolkit.getScreenSize().getHeight() - frame.getHeight()) / 2;
        frame.setLocation(x, y);
        frame.setResizable(false);

        JButton btn1 = new JButton("按钮1");
        JButton btn2 = new JButton("按钮2");
        JButton btn3 = new JButton("按钮3");
        JButton btn4 = new JButton("按钮4");
        JButton btn5 = new JButton("按钮5");
        JButton btn6 = new JButton("按钮6");

        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.LEFT, 3, 3));
        panel1.add(btn1);
        panel1.add(btn3);

        JPanel panel2 = new JPanel();
        panel2.add(btn2, BorderLayout.EAST);
        panel2.add(btn4);

        JPanel panel3 = new JPanel();
        panel3.add(btn5, BorderLayout.EAST);
        panel3.add(btn6);

        JPanel mainPanel = new JPanel();

        // ScrollPaneLayout会覆盖component的size,这里添加一层panel处理
        JPanel panel4 = new JPanel();

        GroupLayout groupLayout = new GroupLayout(panel4);
        panel4.setLayout(groupLayout);

        //        List<JButton> jButtons = Lists.newArrayList();
        //        for (int i = 0; i < 6; i++) {
        //
        //            jButtons.add(imgButton);
        //        }

        ImageIcon image = new ImageIcon("img/sample.jpg");
        JButton imgButton1 = new JButton(image);
        imgButton1.setBounds(new Rectangle(imgButton1.getWidth(), imgButton1.getHeight()));

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

        JScrollPane scrollPane = new JScrollPane(panel4);
        scrollPane.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
        scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        //        pane.setLayout(new ScrollPaneLayout());

        mainPanel.add(scrollPane);

        frame.add(panel1, BorderLayout.NORTH);
        frame.add(panel2, BorderLayout.SOUTH);
        frame.add(mainPanel, BorderLayout.CENTER);

        Thread.sleep(5000);

        frame.remove(panel2);
        frame.add(panel3, BorderLayout.SOUTH);

        //        frame.pack();

        //        SwingUtilities.invokeLater(new Runnable() {
        //            @Override public void run() {
        //                scrollPane.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
        //                frame.getContentPane().revalidate();
        //            }
        //        });

        frame.getContentPane().revalidate();
    }
}
