package org.rainbow.silence_kingdom.view;

import org.rainbow.silence_kingdom.util.DB;
import org.rainbow.silence_kingdom.util.Meta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/9/17.
 * Time: 下午5:40.
 * Description:
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        //        DB.exec("delete from card");
        //        DB.exec("drop table history");
        //        DB.exec("drop table card");
        DB.initTable();
        Meta.loadCards();

        BaseFrame frame = new BaseFrame();
        frame.viewSwitch(new WelcomeView(frame));

        //        logger.info("{}", DB.query("select * from card").size());
    }
}
