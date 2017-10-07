package org.rainbow.silence_kingdom.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/9/24.
 * Time: 上午8:02.
 * Description:
 */
public class Meta {

    private static final String META_FILE_LOCATION = System.getProperty("meta.file.path");

    private static Meta SINGLETON = null;

    public synchronized static Meta getInstance() {
        if (SINGLETON == null) {
            SINGLETON = newInstance(META_FILE_LOCATION);
        }
        return SINGLETON;
    }

    private static Meta newInstance(String metaFileLocation) {
        File metaFile = null;
        BufferedReader bufferedReader = null;
        try {
            metaFile = new File(metaFileLocation);
            bufferedReader = new BufferedReader(new FileReader(metaFile));

            String context = bufferedReader.readLine();



        } catch (Exception e) {

        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
    }

    private int version;
    private String dataDir;
    private int totalMinutes;

    public int getVersion() {
        return version;
    }

    public String getDataDir() {
        return dataDir;
    }

    public int getTotalMinutes() {
        return totalMinutes;
    }
}
