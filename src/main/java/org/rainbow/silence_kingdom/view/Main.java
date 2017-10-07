package org.rainbow.silence_kingdom.view;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/9/17.
 * Time: 下午5:40.
 * Description:
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        BaseFrame frame = new BaseFrame();
        frame.viewSwitch(new AudioRecordView(frame));
    }
}
