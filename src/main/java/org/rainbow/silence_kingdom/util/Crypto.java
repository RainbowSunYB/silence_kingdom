package org.rainbow.silence_kingdom.util;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/10/22.
 * Time: 下午10:36.
 * Description:
 */
public class Crypto {

    private static final Logger logger = LoggerFactory.getLogger(Crypto.class);

    public static void encode() throws IOException {
        String fromBase = "/Users/rainbow/Src/java/github/silence_kingdom/src/main/resources/img-1/";
        String toBase = "/Users/rainbow/Src/java/github/silence_kingdom/src/main/resources/img/";
        List<String> paths = Lists.newArrayList();
        paths.add("card-1/image.jpg");
        paths.add("card-1/small-image.jpg");
        paths.add("card-2/image.jpg");
        paths.add("card-2/small-image.jpg");
        paths.add("card-3/image.jpg");
        paths.add("card-3/small-image.jpg");
        paths.add("default.jpg");
        paths.add("description.jpg");
        paths.add("background.jpg");
        paths.add("pre_start.jpg");
        paths.add("text_background.jpg");

        for (String item : paths) {
            String fromPath = fromBase + item;
            String toPath = toBase + item;

            File fromFile = new File(fromPath);
            File toFile = new File(toPath);

            byte[] bytes = Files.toByteArray(fromFile);
            String encodingData = encode(bytes);

            BufferedWriter writer = new BufferedWriter(new FileWriter(toFile));
            writer.write(encodingData);
            writer.flush();
            writer.close();
        }
    }

    public static String encode(byte[] input) {
        return Base64.getEncoder().encodeToString(input);
    }

    public static byte[] decode(File file) {
        try {
            byte[] bytes = Files.toByteArray(file);
            return Base64.getDecoder().decode(bytes);
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] decode(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        return decode(file);
    }
}
