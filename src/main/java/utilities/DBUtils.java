package utilities;

import java.sql.*;

public class DBUtils {

    private static Connection connection;

    public static void connect() throws Exception {
        String url = ConfigReader.get("db.url");
        String user = ConfigReader.get("db.username");
        String pass = ConfigReader.get("db.password");

        connection = DriverManager.getConnection(url, user, pass);
    }

    public static boolean userExists(String username) throws Exception {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt(1) > 0;
    }

    public static void disconnect() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
