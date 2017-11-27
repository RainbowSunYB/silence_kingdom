package org.rainbow.silence_kingdom.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.apache.commons.lang3.math.NumberUtils;
import org.rainbow.silence_kingdom.audio.Audio;
import org.rainbow.silence_kingdom.conts.ViewType;
import org.rainbow.silence_kingdom.models.Card;
import org.rainbow.silence_kingdom.models.History;
import org.rainbow.silence_kingdom.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import static org.rainbow.silence_kingdom.conts.Constants.*;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/10/15.
 * Time: 下午8:36.
 * Description:
 */
public class AudioView extends BaseView {
    private static final Logger logger = LoggerFactory.getLogger(AudioView.class);
    private static final String REMAINING_TIME_TEMPLATE = "%d分%d秒";

    private JPanel panel;
    private JTextField remainingTimeField;
    private JTextField averageRagePointField;
    private JButton startButton;
    private JButton resetButton;
    private JPanel currentRagePanel;
    private JTextField averageField;

    private int totalMinutes = NumberUtils.toInt(Meta.getConfig(META_TOTAL_MINUTES_KEY, META_TOTAL_MINUTES_DEFAULT));
    private int threshold = NumberUtils.toInt(Meta.getConfig(META_VOICE_THRESHOLD_KEY, META_VOICE_THRESHOLD_DEFAULT));
    private long startTime = 0;
    private long endTime = 0;
    private int remainingSeconds = 0;
    private int remainingMinutes = totalMinutes;
    private int lastAveragePoint = 0;
    private int averageRagePoint = 0;

    private boolean started;
    private boolean stopped;

    private Dashboard dashBoard;
    private Audio audio = new Audio();
    private Refresher refresher = new Refresher();

    protected AudioView(BaseFrame baseFrame) {
        super(baseFrame);

        $$$setupUI$$$();
        startButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                start();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                baseFrame.viewSwitch(new AudioView(baseFrame));
            }
        });

        this.remainingTimeField.setText(String.format(REMAINING_TIME_TEMPLATE, this.remainingMinutes, this.remainingSeconds));
        this.averageRagePointField.setText(Integer.toString(averageRagePoint));
        this.averageField.setText(Integer.toString(averageRagePoint));

//        refresher.addTask(new DashboardTask(System.currentTimeMillis(), this));
        refresher.start();

        audio.start();
    }

    private void createUIComponents() {
        panel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon(Crypto.decode(Meta.IMG_DIR.getAbsolutePath() + "/pre_start.jpg"));
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, icon.getIconWidth(),
                        icon.getIconHeight(), icon.getImageObserver());
            }
        };

        currentRagePanel = new JPanel() {
            public Insets getInsets() {
                return new Insets(20, 20, 30, 10);
            }
        };

        BorderLayout centerLayout = new BorderLayout();
        centerLayout.setHgap(20);
        currentRagePanel.setLayout(centerLayout);
        currentRagePanel.setMinimumSize(new Dimension(500, 200));

        // TODO: place custom component creation code here
        dashBoard = new Dashboard.Builder()
                .from(0)
                .to(100)
                .major(10)
                .minor(1)
                .value(0)
                .unit("")
                .type("semi_circle180")
                .textColor(Color.white)
                .valueColor(Color.white)
                .pointerColor(Color.white)
                .majorScaleColor(Color.WHITE)
                .minorScaleColor(Color.WHITE)
                .foregroundColor(Color.BLUE)
                .backgroundColor(Color.WHITE)
                //                .size(new Dimension(300, 300))
                .build();
        dashBoard.setOpaque(false);
        currentRagePanel.add(dashBoard, BorderLayout.CENTER);
    }

    public void refreshDashBoard() {
        int value = audio.getCurrentRagePoint();
        dashBoard.updateValue(value);
    }

    public boolean refreshText() {
        if (endTime < System.currentTimeMillis()) {
            stop(true);
            return false;
        }

        int remainingSeconds = (int) ((endTime - System.currentTimeMillis()) / 1000);
        this.remainingMinutes = remainingSeconds / 60;
        this.remainingSeconds = remainingSeconds % 60;
        int avr = audio.getAverageRagePoint();
        this.averageRagePoint = (threshold - avr) * 100 / threshold;
        if (this.averageRagePoint >= 0) {
            averageRagePointField.setForeground(new Color(0x0DBB2C));
        } else {
            averageRagePointField.setForeground(new Color(-4520417));
        }
        this.remainingTimeField.setText(String.format(REMAINING_TIME_TEMPLATE, this.remainingMinutes, this.remainingSeconds));
        this.averageRagePointField.setText(Integer.toString(averageRagePoint));
        this.averageField.setText(Integer.toString(avr));
        return true;
    }

    public void start() {
        if (stopped || started) {
            return;
        }
        started = true;
        startTime = System.currentTimeMillis();
        endTime = startTime + this.remainingMinutes * 60 * 1000;
        audio.startRecord();
//        refresher.addTask(new TimerTask(this));
    }

    public void stop(boolean complete) {
        if (!started || stopped) {
            return;
        }
        stopped = true;
        audio.stopRecord();
        History history = new History();
        history.setTotalMinutes(totalMinutes);
        history.setRagePointThreshold(threshold);
        history.setAverageRagePoint(audio.getAverageRagePoint());
        history.setComplete(complete ? HISTORY_COMPLETE : HISTORY_INCOMPLETE);
        if (complete && audio.getAverageRagePoint() < threshold) {
            java.util.List<Card> cards = DB.queryUnacquiredCards();
            if (cards == null) {
                history.setCardId(0);
            } else if (cards.size() == 0) {
                history.setCardId(-1);
            } else {
                int random = (int) System.currentTimeMillis() % cards.size();
                history.setCardId(cards.get(random).getId());
            }
            history.setSuccess(HISTORY_SUCCESS);
        } else {
            history.setSuccess(HISTORY_FAIL);
        }
        DB.exec(String.format("update card set status = 1 where id = '%s'", history.getCardId()));
        DB.exec(String
                .format("insert into history (total_minutes, rage_point_threshold, average_rage_point, card_id, is_succeed, is_completed, created_time) values ('%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                        history.getTotalMinutes(), history.getRagePointThreshold(), history.getAverageRagePoint(), history.getCardId(), history.getSuccess(), history.getComplete(), new Date()));

        if (history.getSuccess() == HISTORY_SUCCESS) {
            if (history.getCardId() == -1) {
                baseFrame.viewSwitch(new NoMoreCardView(baseFrame));
            } else {
                baseFrame.viewSwitch(new PreSuccessView(baseFrame, history.getCardId()));
            }
        } else {
            baseFrame.viewSwitch(new FailView(baseFrame));
        }
    }

    public boolean isStopped() {
        return stopped;
    }

    @Override ViewType getViewType() {
        return ViewType.AUDIO_RECORD;
    }

    @Override Container getView() {
        return panel;
    }

    @Override protected void destroy() {
        stop(false);
        refresher.astop();
        audio.astop();
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

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panel.setLayout(new GridLayoutManager(9, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel.setOpaque(true);
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, Font.BOLD, 22, label1.getFont());
        if (label1Font != null)
            label1.setFont(label1Font);
        label1.setForeground(new Color(-1));
        label1.setText("剩余时间");
        panel.add(label1,
                new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        remainingTimeField = new JTextField();
        remainingTimeField.setEditable(false);
        Font remainingTimeFieldFont = this.$$$getFont$$$(null, -1, 28, remainingTimeField.getFont());
        if (remainingTimeFieldFont != null)
            remainingTimeField.setFont(remainingTimeFieldFont);
        remainingTimeField.setText("");
        panel.add(remainingTimeField,
                new GridConstraints(2, 2, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
                        new Dimension(200, -1), new Dimension(200, -1), 0, false));
        final JLabel label2 = new JLabel();
        label2.setFocusTraversalPolicyProvider(false);
        Font label2Font = this.$$$getFont$$$(null, Font.BOLD, 22, label2.getFont());
        if (label2Font != null)
            label2.setFont(label2Font);
        label2.setForeground(new Color(-1));
        label2.setText("成功指数");
        panel.add(label2,
                new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        averageRagePointField = new JTextField();
        averageRagePointField.setEditable(false);
        Font averageRagePointFieldFont = this.$$$getFont$$$(null, Font.BOLD, 28, averageRagePointField.getFont());
        if (averageRagePointFieldFont != null)
            averageRagePointField.setFont(averageRagePointFieldFont);
        averageRagePointField.setForeground(new Color(-4520417));
        averageRagePointField.setText("");
        panel.add(averageRagePointField,
                new GridConstraints(4, 2, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
                        new Dimension(200, -1), new Dimension(200, -1), 0, false));
        currentRagePanel.setOpaque(false);
        panel.add(currentRagePanel,
                new GridConstraints(0, 0, 9, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel.add(spacer1, new GridConstraints(1, 2, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel.add(spacer2, new GridConstraints(8, 2, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel.add(spacer3, new GridConstraints(0, 2, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$(null, Font.BOLD, 22, label3.getFont());
        if (label3Font != null)
            label3.setFont(label3Font);
        label3.setForeground(new Color(-1));
        label3.setText("平均值");
        panel.add(label3,
                new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
                        false));
        averageField = new JTextField();
        averageField.setEditable(false);
        Font averageFieldFont = this.$$$getFont$$$(null, Font.BOLD, 28, averageField.getFont());
        if (averageFieldFont != null)
            averageField.setFont(averageFieldFont);
        averageField.setText("");
        panel.add(averageField,
                new GridConstraints(3, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
                        new Dimension(200, -1), new Dimension(200, -1), 0, false));
        startButton = new JButton();
        Font startButtonFont = this.$$$getFont$$$(null, -1, 20, startButton.getFont());
        if (startButtonFont != null)
            startButton.setFont(startButtonFont);
        startButton.setText("开始");
        panel.add(startButton,
                new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, -1), new Dimension(50, -1), 0, false));
        resetButton = new JButton();
        Font resetButtonFont = this.$$$getFont$$$(null, -1, 20, resetButton.getFont());
        if (resetButtonFont != null)
            resetButton.setFont(resetButtonFont);
        resetButton.setText("重置");
        panel.add(resetButton,
                new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, -1), new Dimension(50, -1), 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }
}
