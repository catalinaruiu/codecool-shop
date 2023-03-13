package com.codecool.shop.dao.jdbc;

import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class ShopDataManager {
    private static final Logger logger = LoggerFactory.getLogger(ShopDataManager.class);
    private static ShopDataManager instance = null;

    private ShopDataManager() {
    }

    public static ShopDataManager getInstance() {
        if (instance == null) {
            instance = new ShopDataManager();
        }
        return instance;
    }

    public DataSource connect() {
        PGSimpleDataSource dataSource = null;
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/connection.properties");
            Properties properties = new Properties();
            properties.load(fileInputStream);

            dataSource = new PGSimpleDataSource();
            String dbName = (String) properties.get("database");
            String user = (String) properties.get("user");
            String password = (String) properties.get("password");

            dataSource.setDatabaseName(dbName);
            dataSource.setUser(user);
            dataSource.setPassword(password);

            logger.info("Trying to connect");
            dataSource.getConnection().close();
            logger.info("Connection ok.");

        } catch (SQLException e) {
            logger.error("Cannot connect to Database {} to data source {}", e, dataSource);
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            logger.error("could not find the file", e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error("could not load file", e);
            throw new RuntimeException(e);
        }

        return dataSource;
    }
}
