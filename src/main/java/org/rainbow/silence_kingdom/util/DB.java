package org.rainbow.silence_kingdom.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.logging.log4j.util.Strings;
import org.rainbow.silence_kingdom.models.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/9/24.
 * Time: 上午8:10.
 * Description:
 */
public class DB {

    private static final Logger logger = LoggerFactory.getLogger(DB.class);

    private static DataSource dataSource = makeDataSource("jdbc:sqlite:/tmp/silence_kingdom.db", Strings.EMPTY, Strings.EMPTY);


    public static void initTable() {
        File dir = new File("db/init");
        File[] subFiles = dir.listFiles();
        logger.info("subdirs: {}", subFiles);
        if (subFiles == null || subFiles.length == 0) {
            return;
        }

        for (File table : subFiles) {
            String sql = readFromFile(table);
            exec(sql);
        }
    }

    private static String readFromFile(File file) {
        StringBuilder result = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String s;
            while ((s = br.readLine()) != null) {
                result.append(System.lineSeparator()).append(s);
            }
        } catch (Exception e) {
            logger.error("read file content error", e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                logger.error("close buffer error", e);
            }
        }
        return result.toString();
    }

    private static DataSource makeDataSource(String url, String username, String password) {
        logger.info("make RDS data source {}, {}, {}", url, username, password);
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setMaxActive(30);
        dataSource.setMaxIdle(30);
        dataSource.setMaxWait(30);
        dataSource.setValidationQuery("select 1");
        dataSource.setTestOnBorrow(true);
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    public static List<Map<String, Object>> query(String sql) {
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            return convertList(statement.executeQuery());
        } catch (Exception e) {
            //            logger.error("exec sql [{}] error", sql, e);
            return null;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    logger.error("close connection error");
                }
            }
        }
    }

    public static void exec(String sql) {
        Connection connection = null;
        PreparedStatement statement;
        try {
            //            logger.info("exec sql: {}", sql);
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            statement.execute();
        } catch (Exception e) {
            //            logger.error("exec sql [{}] error", sql, e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    logger.error("close connection error");
                }
            }
        }
    }

    private static List<Card> queryCards(String sql) {
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            List<Card> cards = Lists.newArrayList();
            while (resultSet.next()) {
                Card card = new Card();
                card.setId(resultSet.getInt("id"));
                card.setCardName(resultSet.getString("card_name"));
                card.setImagePath(resultSet.getString("image_path"));
                card.setSmallImagePath(resultSet.getString("small_image_path"));
                card.setStatus(resultSet.getInt("status"));
                cards.add(card);
            }
            return cards;
        } catch (Exception e) {
            logger.error("exec sql error", e);
            return null;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    logger.error("close connection error");
                }
            }
        }
    }

    public static List<Card> queryAllCards() {
        return queryCards("select id, card_name, image_path, small_image_path, status from card");
    }

    public static Card queryCardById(int id) {
        List<Card> result = queryCards(String.format("select id, card_name, image_path, small_image_path, status from card where id = '%d'", id));
        if (result == null || result.size() != 1) {
            return null;
        }
        return result.get(0);
    }

    public static List<Card> queryUnacquiredCards() {
        return queryCards("select id, card_name, image_path, small_image_path, status from card where status = 0");
    }

    private static List<Map<String, Object>> convertList(ResultSet rs) throws SQLException {
        List<Map<String, Object>> list = Lists.newArrayList();
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();
        while (rs.next()) {
            Map<String, Object> rowData = Maps.newHashMap();
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(rowData);
        }
        return list;
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("PWD"));
    }
}
