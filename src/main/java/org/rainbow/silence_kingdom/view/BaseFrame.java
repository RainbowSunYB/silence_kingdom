package org.rainbow.silence_kingdom.view;

import javax.swing.*;
import java.awt.*;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/9/10.
 * Time: 上午7:31.
 * Description:
 */
public class BaseFrame extends JFrame {

    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel mainPanel;

    private BaseView baseView;

    public BaseFrame() {
        initBaseFrame();
        initBasePanel();
    }

    private void initBaseFrame() {
        this.setVisible(true);

        this.setSize(1280, 720);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int x = (int) (toolkit.getScreenSize().getWidth() - this.getWidth()) / 2;
        int y = (int) (toolkit.getScreenSize().getHeight() - this.getHeight()) / 2;
        this.setLocation(x, y);

        this.setLayout(new BorderLayout());
    }

    private void initBasePanel() {
        //        JMenuBar menuBar = new JMenuBar();
        //
        //        JMenu fileMenu = new JMenu("File");
        //        fileMenu.setMnemonic(KeyEvent.VK_F);
        //        menuBar.add(fileMenu);
        //
        //        JMenuItem newMenuItem = new JMenuItem("New", KeyEvent.VK_N);
        //        fileMenu.add(newMenuItem);
        //
        //        JCheckBoxMenuItem caseMenuItem = new JCheckBoxMenuItem("Case Sensitive");
        //        caseMenuItem.setMnemonic(KeyEvent.VK_C);
        //        fileMenu.add(caseMenuItem);

        this.topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        //        topPanel.add(menuBar);
        this.add(topPanel, BorderLayout.NORTH);

        JButton btn2 = new JButton("返回主菜单");
        btn2.addActionListener(new MainMenuAction(this));
        this.bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(btn2);
        this.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel = new JPanel() {
        };
        mainPanel.setLayout(new BorderLayout());
        this.add(mainPanel, BorderLayout.CENTER);
    }

    public void viewSwitch(BaseView newView) {
        mainPanel.removeAll();
        mainPanel.repaint();
        if (baseView != null) {
            baseView.destroy();
        }
        baseView = newView;
        mainPanel.add(newView.getView(), BorderLayout.CENTER);
        mainPanel.revalidate();
    }

    public Dimension getDimension() {
        return new Dimension(this.getWidth(), this.getHeight());
    }
}
