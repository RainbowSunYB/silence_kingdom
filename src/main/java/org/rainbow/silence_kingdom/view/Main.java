package org.rainbow.silence_kingdom.view;

import org.rainbow.silence_kingdom.util.DB;
import org.rainbow.silence_kingdom.util.Meta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/9/17.
 * Time: 下午5:40.
 * Description:
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException, IOException {
        DB.initTable();
        Meta.loadCards();

        logger.info("{}", DB.query("select * from card"));

        BaseFrame frame = new BaseFrame();
        frame.viewSwitch(new WelcomeView(frame));
    }

}

