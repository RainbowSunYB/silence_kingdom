package org.rainbow.silence_kingdom.util;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/9/23.
 * Time: 下午2:50.
 * Description:
 */
public class Refresher extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(Refresher.class);

    private PriorityQueue<Task> queue = new PriorityQueue<Task>(new Comparator<Task>() {
        @Override public int compare(Task o1, Task o2) {
            return (int) (o1.timestamp() - o2.timestamp());
        }
    });

    public void addTask(Task task) {
        queue.add(task);
    }

    @Override public void run() {
        while (true) {
//            logger.info("new loop");
            try {
                boolean goon;
                List<Task> taskList = Lists.newArrayList();
                do {
                    Task task = queue.peek();
                    if (task != null && task.timestamp() < System.currentTimeMillis()) {
//                        logger.info("got task");
                        goon = true;
                        queue.remove(task);
                        taskList.add(task);
                    } else {
//                        logger.info("no task");
                        goon = false;
                    }
                } while (goon);

                if (taskList.size() != 0) {
                    for (Task task : taskList) {
                        Task newTask = task.exec();
                        if (newTask != null) {
                            queue.add(newTask);
                        }
                    }
                }

                Thread.sleep(50);
            } catch (Exception e) {

            } finally {
            }
        }
    }
}
