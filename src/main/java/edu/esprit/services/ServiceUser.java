package edu.esprit.services;

import edu.esprit.utiles.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServiceUser {
     Connection con = DataSource.getInstance().getCon();
    public List<String> getRoleByUserId() throws SQLException {
        List<String> userIds = new ArrayList<>();
        String query = "SELECT id FROM user"; // Assuming 'id' is the column name for user IDs

             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int userId = rs.getInt("id");
                userIds.add(String.valueOf(userId));
            }

        return userIds;
    }
}
