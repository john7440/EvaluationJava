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

    private DatabaseConfig() {
        loadProperties();
    }

    private void loadProperties() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("database.properties")) {
            if (input == null) {
                LOGGER.log(Level.SEVERE, "Unable to find database.properties");
                throw new RuntimeException("Configuration file not found");
            }
            properties.load(input);
            Class.forName(properties.getProperty("db.driver"));
            LOGGER.log(Level.INFO, "Database configuration loaded successfully");
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error loading database configuration", e);
            throw new RuntimeException("Failed to load database configuration", e);
        }
    }
}
