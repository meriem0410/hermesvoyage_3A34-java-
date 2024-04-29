package edu.esprit.services;
import edu.esprit.entities.User;
import edu.esprit.entities.admin;
import edu.esprit.entities.voyageur;
import edu.esprit.utiles.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService {

    public User getUserByUsername(String username) {
        String query = "SELECT * FROM user WHERE username=?";
        User user = null;

        try {
            PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");

                switch (role) {
                    case "admin":
                        user = new admin(id, email, password, username, role,true,true);
                        break;
                    case "voyageur":
                        user = new voyageur(id, email, password, username, role,false,false);
                        break;
            }}
        } catch (SQLException e) {
            System.out.println("Error fetching user by username: " + e.getMessage());
        }

        return user;
    }


}