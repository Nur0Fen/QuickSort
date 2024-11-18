package com.example.sort1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOperations {

    // Create
    public static void insertData(String array) throws SQLException {
        String sql = "INSERT INTO arrays (arrayContent) VALUES (?)";
        Connection conn = DatabaseConnection.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql) ;
        pstmt.setString(1, array);
        pstmt.executeUpdate();

    }

    // Read
    public static List<Array> getAllArrays() throws SQLException {
        List<Array> arrays = new ArrayList<>();
        String sql = "SELECT * FROM arrays";
        Connection conn = DatabaseConnection.connect();
        Statement stmt = conn.createStatement() ;
        ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Array array = new Array(
                        rs.getInt("id"),
                        rs.getString("arrayContent")
                );
                arrays.add(array);
            }
        return arrays;
    }

    // Update
    public static void updateArray(int id, String array) throws SQLException {
        String sql = "UPDATE arrays SET arrayContent = ? WHERE id = ?";

        Connection conn = DatabaseConnection.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql) ;

            pstmt.setString(1, array);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();


    }

    // Delete
    public static void deleteArray(int id) throws SQLException {
        String sql = "DELETE FROM arrays WHERE id = ?";

        Connection conn = DatabaseConnection.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql) ;

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

    }
    // Delete
    public static void truncArrayDb() {
        String sql = "TRUNCATE TABLE arrays RESTART IDENTITY";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}