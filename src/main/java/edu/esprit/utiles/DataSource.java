package edu.esprit.utiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

    private final String URL = "jdbc:mysql://localhost:3306/hermes2";

    private final String USER = "root";

    private final String PASSWD ="";

    private Connection cnx ;
    private static DataSource instance;

    // Constructeur privé pour empêcher l'instanciation directe depuis l'extérieur de la classe
    private DataSource() {
        // Initialisation de la connexion ici
        try {
            cnx = DriverManager.getConnection(URL  , USER, PASSWD);
            System.out.println("Connected to DB ");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Méthode statique pour récupérer l'instance unique de la classe
    public static synchronized DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }

    // Méthode pour récupérer la connexion à la base de données
    public Connection getCon() {
        return cnx;
    }


}
