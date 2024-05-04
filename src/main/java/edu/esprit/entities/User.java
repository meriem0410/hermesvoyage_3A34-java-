package edu.esprit.entities;

public class User {
    private int id;
    private String email;
    private String password;
    private String username;
    private String role;
    private boolean verified;
    private boolean isBanned;
    private byte[] pictureData;

    // Constructors
    public User() {
        // Default constructor
    }

    public User(int id, String email, String password, String username, String role, boolean verified, boolean isBanned) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.role = role;
        this.verified = verified;
        this.isBanned = isBanned;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }


    // Constructor, getters, setters, and other methods

    public byte[] getPictureData() {
        return pictureData;
    }

    public void setPictureData(byte[] pictureData) {
        this.pictureData = pictureData;
    }

    // toString method
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", verified=" + verified +
                ", isBanned=" + isBanned +
                '}';
    }
}

