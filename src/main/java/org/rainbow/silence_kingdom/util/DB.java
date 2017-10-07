package org.rainbow.silence_kingdom.util;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * Copyright (c) by Megvii.com.
 * Created by Rainbow Sun.
 * Date: 2017/9/24.
 * Time: 上午8:10.
 * Description:
 */
public class DB {

    private static final Logger logger = LoggerFactory.getLogger(DB.class);

    private static DataSource dataSource = makeDataSource(System.getProperty("db.url"), Strings.EMPTY, Strings.EMPTY);



    static {
        init();
    }

    private static void init() {
        connect();


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

    public static void connect() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            logger.info("Connection to SQLite has been established.");

            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {

        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }



    public static void main(String[] args) {
        connect();
    }
}
