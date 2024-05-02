package edu.esprit.Controllers;

import edu.esprit.entities.User;

public class UserSession {
    private static User loggedInUser;

    public static void setUser(User user) {
        loggedInUser = user;
    }

    public static User getUser() {
        return loggedInUser;
    }
}

