package com.example.toigether;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private Connection conn;

    public boolean open() {
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@eventsproject_high?TNS_ADMIN=C:/Users/Mika/important/projects/toigether-android-app/app/wallet","admin","Maily1993456");
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }
}
