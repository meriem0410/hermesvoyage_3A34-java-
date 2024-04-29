package edu.esprit.entities;

public class admin extends User{

    public admin() {
        // Default constructor
    }

    public admin(int id, String email, String password, String username, String role, boolean verified, boolean isBanned) {
        super(id, email, password, username,"admin" ,verified,isBanned);
    }
}
