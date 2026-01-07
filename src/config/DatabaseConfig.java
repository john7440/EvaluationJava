package config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Database configuration using Singleton pattern
 * Singleton is required to ensure a single shared configuration instance
 * across all DAO objects as per project specifications
 */
@SuppressWarnings("java:S6548")
public class DatabaseConfig {
    private static final Logger LOGGER = Logger.getLogger(DatabaseConfig.class.getName());
    private final Properties properties;

    private DatabaseConfig(){
        properties =  new Properties();
        loadProperties();
    }


    private static class SingletonHolder {
        private static final DatabaseConfig INSTANCE = new DatabaseConfig();
    }

    public static DatabaseConfig getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("database.properties")) {
            if (input == null) {
                LOGGER.log(Level.SEVERE, "Unable to find database.properties");
                throw new ConfigurationException("Configuration file not found");
            }
            properties.load(input);
            Class.forName(properties.getProperty("db.driver"));
            LOGGER.log(Level.INFO, "Database configuration loaded successfully");
        } catch (IOException | ClassNotFoundException e) {
            throw new ConfigurationException("Failed to load database configuration", e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public Connection getConnection() throws SQLException {
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        Connection connection = DriverManager.getConnection(url, user, password);
        LOGGER.log(Level.FINE, "Database connection established");
        return connection;
    }

}
