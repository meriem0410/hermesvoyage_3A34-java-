package edu.esprit.entities;

public class voyageur extends User{
    public voyageur() {
        // Default constructor
    }

    public voyageur(int id, String email, String password, String username, String role, boolean verified, boolean isBanned) {
        super(id, email, password, username,"voyageur" ,verified,isBanned);
    }
}
