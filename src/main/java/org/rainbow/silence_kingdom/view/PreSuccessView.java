package org.rainbow.silence_kingdom.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.rainbow.silence_kingdom.conts.ViewType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/10/21.
 * Time: 下午4:52.
 * Description:
 */
public class PreSuccessView extends BaseView {
    private static final Logger logger = LoggerFactory.getLogger(PreSuccessView.class);

    private JPanel panel1;
    private JLabel label;

    protected PreSuccessView(BaseFrame baseFrame, int cardId) {
        super(baseFrame);

        new Thread(new Runnable() {
            @Override public void run() {
                try {
                    Thread.sleep(2000);
                    baseFrame.viewSwitch(new SuccessView(baseFrame, cardId));
                } catch (InterruptedException e) {
                    logger.error("sleep failed");
                }
            }
        }).start();
    }

    @Override ViewType getViewType() {
        return null;
    }

    @Override Container getView() {
        return panel1;
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null)
            return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    {
        // GUI initializer generated by IntelliJ IDEA GUI Designer
        // >>> IMPORTANT!! <<<
        // DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-16777216));
        label = new JLabel();
        label.setEnabled(true);
        Font labelFont = this.$$$getFont$$$("Apple Braille", Font.BOLD, 28, label.getFont());
        if (labelFont != null)
            label.setFont(labelFont);
        label.setForeground(new Color(-1));
        label.setText("它出现了!!!");
        panel1.add(label,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
