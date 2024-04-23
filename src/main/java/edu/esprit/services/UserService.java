package edu.esprit.services;

import edu.esprit.entities.User;
import edu.esprit.entities.admin;
import edu.esprit.entities.voyageur;
import edu.esprit.interfaces.EService;
import edu.esprit.utiles.MyConnection;


import java.sql.*;
import java.util.*;


public class UserService implements EService<User> {

    @Override
    public void addEntity(User user) {
        String requete = "INSERT INTO `user`(`username`, `email`, `password`,`role`,`verified`,`is_banned`) VALUES (?,?,?,?,0,0)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getPassword());
            pst.setString(4, user.getRole());




            pst.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addEntity(admin admin) {
        addEntity((User) admin);
    }

    public void addEntity(voyageur voyageur) {
        addEntity((User) voyageur);
    }



    @Override
    public void deleteEntity(User user) {
        String requete = "DELETE FROM `user` WHERE `id` = ?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, user.getId());
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("User deleted!!");
            } else {
                System.out.println("User with id " + user.getId() + " does not exist.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public User getUserByEmail(String email) {
        for (User user : getAllData()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null; // User not found
    }

    @Override
    public Set<User> getAllData() {
        Set<User> data = new HashSet<>();
        String requete = "SELECT * FROM user";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                User user;
                String role = rs.getString("role");
                if ("admin".equals(role)) {
                    user = new admin();
                } else if ("voyageur".equals(role)) {
                    user = new voyageur();
                } else {
                    user = new User();
                }
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(role);
                user.setVerified(rs.getBoolean("verified"));
                user.setBanned(rs.getBoolean("is_banned"));
                data.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }







}

