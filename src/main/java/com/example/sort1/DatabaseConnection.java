package com.example.sort1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/QuickSort";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";
    private static Connection conn;

    public static Connection connect() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    static void createDatabase() throws SQLException {
        Connection conn = connect();
        Statement stmt = conn.createStatement() ;

            String sql = "CREATE TABLE IF NOT EXISTS arrays (" +
                    "id SERIAL PRIMARY KEY," +
                    "arrayContent VARCHAR(100) NOT NULL" +
                    ")";
            stmt.execute(sql);

        }
    // Метод для закрытия соединения
    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }
