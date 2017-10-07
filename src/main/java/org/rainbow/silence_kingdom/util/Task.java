package org.rainbow.silence_kingdom.util;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/9/23.
 * Time: 下午2:51.
 * Description:
 */
public interface Task {
    Task exec();

    long timestamp();
}
