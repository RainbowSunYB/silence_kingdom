package org.rainbow.silence_kingdom.view;

import javax.swing.*;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/9/10.
 * Time: 上午7:31.
 * Description:
 */
public class Base extends JFrame {

    private JPanel basePanel;

    public Base() {
        initBaseFrame();
        initBasePanel();
    }

    private void initBaseFrame() {

    }

    private void initBasePanel() {
        this.basePanel = new JPanel();

        GroupLayout groupLayout = new GroupLayout(basePanel);
    }
}
