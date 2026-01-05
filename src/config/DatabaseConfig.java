package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Database configuration using Singleton pattern
 */
public class DatabaseConfig {
    private static final Logger LOGGER = Logger.getLogger(DatabaseConfig.class.getName());
    private static DatabaseConfig instance;
    private Properties properties;

    private DatabaseConfig() throws IOException, ClassNotFoundException {
        loadProperties();
    }

    public static DatabaseConfig getInstance() throws IOException, ClassNotFoundException {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }

    private void loadProperties() throws IOException, ClassNotFoundException {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("database.properties")) {
            if (input == null) {
                LOGGER.log(Level.SEVERE, "Unable to find database.properties");
                throw new RuntimeException("Configuration file not found");
            }
            assert properties != null;
            properties.load(input);
            Class.forName(properties.getProperty("db.driver"));
            LOGGER.log(Level.INFO, "Database configuration loaded successfully");
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
