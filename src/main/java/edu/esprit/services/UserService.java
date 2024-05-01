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
    public String getEmailByUsername(String username) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String email = null;

        try {
            conn = MyConnection.getInstance().getCnx();
            String query = "SELECT email FROM user WHERE username = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            if (rs.next()) {
                email = rs.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException
        } finally {
            // Close resources
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle SQLException while closing resources
            }
        }

        return email;
    }
    public boolean doesEmailExist(String email) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            conn = MyConnection.getInstance().getCnx();
            String sql = "SELECT COUNT(*) FROM user WHERE email = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                exists = count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
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

    public boolean updatePassword(String newPassword,String email) {
        String query = "UPDATE user SET password = ? where email= ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setString(1, newPassword);
            pst.setString(2, email);
            int rowsUpdated = pst.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Error updating password: " + e.getMessage());
            return false;
        }
    }

    public boolean updateban(boolean isBanned, String email) {
        String query = "UPDATE user SET is_banned = ? WHERE email = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setBoolean(1, isBanned);
            pst.setString(2, email);
            int rowsUpdated = pst.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Error updating is_banned status: " + e.getMessage());
            return false;
        }
    }


    public void updateOTP(String email, String otp) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = MyConnection.getInstance().getCnx();
            String sql = "UPDATE user SET OTP = ? WHERE email = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, otp);
            stmt.setString(2, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String getOTP(String email) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String otp = null;

        try {
            conn = MyConnection.getInstance().getCnx();
            String sql = "SELECT otp FROM user WHERE email = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            if (rs.next()) {
                otp = rs.getString("otp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return otp;
    }


    public boolean getVerified(String email) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean verified=false;

        try {
            conn = MyConnection.getInstance().getCnx();
            String sql = "SELECT verified FROM user WHERE email = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            if (rs.next()) {
                verified = rs.getBoolean("verified");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return verified;
    }
    public boolean updateverified(boolean verified , String email) {
        String query = "UPDATE user SET verified = ? WHERE email = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setBoolean(1, verified);
            pst.setString(2, email);
            int rowsUpdated = pst.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Error updating verified status: " + e.getMessage());
            return false;
        }
    }














}

