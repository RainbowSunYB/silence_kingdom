package org.rainbow.silence_kingdom.test;

import javax.swing.*;
import java.awt.*;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/8/12.
 * Time: 下午4:11.
 * Description:
 */
public class Description extends JPanel {

    public static final Font HUA_WEN_XIN_WEI = new Font("华文新魏", Font.BOLD, 40);

    public Description(String text) {
        JLabel label = new JLabel(text);
        label.setFont(HUA_WEN_XIN_WEI);
        label.setForeground(Color.RED);
        add(label);
    }
}
