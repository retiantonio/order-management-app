package DataAccess;

import java.sql.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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
