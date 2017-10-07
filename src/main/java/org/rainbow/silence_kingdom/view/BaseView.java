package org.rainbow.silence_kingdom.view;

import org.rainbow.silence_kingdom.conts.ViewType;

import java.awt.*;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/9/22.
 * Time: 下午5:40.
 * Description:
 */
public abstract class BaseView {

    protected BaseFrame baseFrame;

    private BaseView() {

    }

    protected BaseView(BaseFrame baseFrame) {
        this.baseFrame = baseFrame;
    }

    abstract ViewType getViewType();

    abstract Container getView();
}
