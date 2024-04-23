package edu.esprit.entities;

public class admin extends User{

    public admin() {
        // Default constructor
    }

    public admin(int id, String email, String password, String username) {
        super(id, email, password, username, "admin");
    }
    public admin(int id, String email, String password, String username, String role) {
        super(id, email, password, username, role);
    }
}
