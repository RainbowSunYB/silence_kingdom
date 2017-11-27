package org.rainbow.silence_kingdom.view;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.rainbow.silence_kingdom.conts.ViewType;
import org.rainbow.silence_kingdom.models.Card;
import org.rainbow.silence_kingdom.util.Crypto;
import org.rainbow.silence_kingdom.util.DB;
import org.rainbow.silence_kingdom.util.Meta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

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
public class ListView extends BaseView {

    private JScrollPane pane;

    public ListView(BaseFrame baseFrame) {
        super(baseFrame);

        JPanel panel = new JPanel();

        java.util.List<Card> cards = DB.queryAllCards();
        if (cards == null || cards.size() == 0) {
            return;
        }

        Set<Integer> unacquiredCardIds = Sets.newHashSet();
        java.util.List<Card> unacquiredCards = DB.queryUnacquiredCards();
        if (unacquiredCards != null) {
            for (Card item : unacquiredCards) {
                unacquiredCardIds.add(item.getId());
            }
        }

        java.util.List<java.util.List<Component>> vGroups = Lists.newArrayList();
        java.util.List<java.util.List<Component>> hGroups = Lists.newArrayList();
        for (int i = 0; i < 3; i++) {
            hGroups.add(Lists.newArrayList());
        }
        for (int i = 0; i < cards.size() / 3 + 1; i++) {
            vGroups.add(Lists.newArrayList());
        }

        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            ImageIcon imageIcon = null;
            if (unacquiredCardIds.contains(card.getId())) {
                imageIcon = new ImageIcon(Crypto.decode(Meta.IMG_DIR.getAbsolutePath() + "/default.jpg"));
            } else {
                imageIcon = new ImageIcon(Crypto.decode(card.getSmallImagePath()));
            }
            JButton button = new JButton(imageIcon);
            if (!unacquiredCardIds.contains(card.getId())) {
                button.addActionListener(new ActionListener() {
                    @Override public void actionPerformed(ActionEvent e) {
                        baseFrame.viewSwitch(new DetailView(baseFrame, card.getId()));
                    }
                });
            }
            button.setBounds(new Rectangle(imageIcon.getIconWidth(), imageIcon.getIconHeight()));
            int hGroupIndex = i % 3;
            int vGroupIndex = i / 3;
            if (hGroups.get(hGroupIndex) == null) {
                hGroups.set(hGroupIndex, Lists.newArrayList());
            }
            hGroups.get(hGroupIndex).add(button);
            if (vGroups.get(vGroupIndex) == null) {
                vGroups.set(vGroupIndex, Lists.newArrayList());
            }
            vGroups.get(vGroupIndex).add(button);
        }

        GroupLayout groupLayout = new GroupLayout(panel);
        panel.setLayout(groupLayout);

        GroupLayout.SequentialGroup hGroup = groupLayout.createSequentialGroup();
        groupLayout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = groupLayout.createSequentialGroup();
        groupLayout.setVerticalGroup(vGroup);

        for (List<Component> items : hGroups) {
            GroupLayout.ParallelGroup group = groupLayout.createParallelGroup();
            for (Component item : items) {
                group.addComponent(item);
            }
            hGroup.addGap(5);
            hGroup.addGroup(group);
        }

        for (List<Component> items : vGroups) {
            GroupLayout.ParallelGroup group = groupLayout.createParallelGroup();
            for (Component item : items) {
                group.addComponent(item);
            }
            vGroup.addGap(1);
            vGroup.addGroup(group);
        }

        pane = new JScrollPane(panel);
        pane.setPreferredSize(baseFrame.getDimension());
        pane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        pane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        pane.setVisible(true);
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
