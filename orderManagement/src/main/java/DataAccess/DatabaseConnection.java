package DataAccess;

import java.sql.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides a singleton-based utility for managing connections to the MySQL database.
 *
 * <p>This class loads the MySQL JDBC driver and handles creating and closing database connections,
 * statements, and result sets with proper resource management and logging of errors.
 *
 * <p>It uses the following connection parameters:
 * <ul>
 *   <li>Driver: {@code com.mysql.cj.jdbc.Driver}</li>
 *   <li>URL: {@code jdbc:mysql://localhost:3306/ordermanagementdb}</li>
 *   <li>User: {@code root}</li>
 *   <li>Password: {@code 13072004}</li>
 * </ul>
 *
 * <p>Clients should obtain a connection via {@link #getConnection()} and close resources using
 * the static {@code close()} methods to avoid resource leaks.
 *
 * <p><b>Example usage:</b>
 * <pre>
 * Connection conn = DatabaseConnection.getConnection();
 * try {
 *     // use connection
 * } finally {
 *     DatabaseConnection.close(conn);
 * }
 * </pre>
 */
public class DatabaseConnection {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/ordermanagementdb";
    private static final String USER = "root";
    private static final String PASS = "13072004";

    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());

    private static DatabaseConnection databaseConnection = new DatabaseConnection();

    private DatabaseConnection() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return databaseConnection.createConnection();
    }

    private Connection createConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error while connecting to the database");
            e.printStackTrace();
        }

        return connection;
    }

    //close functions for terminating
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the connection");
            }
        }
    }

    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the statement");
            }
        }
    }

    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the ResultSet");
            }
        }
    }
}
