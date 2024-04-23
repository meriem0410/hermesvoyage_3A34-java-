package edu.esprit.entities;

public class voyageur extends User{
    public voyageur() {
        // Default constructor
    }

    public voyageur(int id, String email, String password, String username) {
        super(id, email, password, username, "voyageur");
    }
    public voyageur(int id, String email, String password, String username, String role) {
        super(id, email, password, username, role);
    }
}
