package org.rainbow.silence_kingdom.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/10/17.
 * Time: 下午9:03.
 * Description:
 */
public class MainMenuAction implements ActionListener {

    private BaseFrame baseFrame;

    public MainMenuAction(BaseFrame baseFrame) {
        this.baseFrame = baseFrame;
    }

    @Override public void actionPerformed(ActionEvent e) {
        baseFrame.viewSwitch(new WelcomeView(baseFrame));
    }
}
