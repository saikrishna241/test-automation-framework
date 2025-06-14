package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("Properties/config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found in classpath!");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file.", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    // ✅ Add this method
    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}
