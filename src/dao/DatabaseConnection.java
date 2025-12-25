package dao;

import java.sql.*;

public class DatabaseConnection {
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/bus_db",
            "root",
            "root"
        );
    }
}
