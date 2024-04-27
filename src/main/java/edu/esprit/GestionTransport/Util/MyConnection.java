package edu.esprit.GestionTransport.Util;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {

    private String url = "jdbc:mysql://localhost:3306/transport";
    private String login = "root";
    private String pwd = "";

    private static MyConnection instance;
    private Connection cnx;

    private MyConnection() {
        try {
            // Charger les pilotes JDBC (assurez-vous d'avoir importé le pilote JDBC MySQL dans votre projet)
            Class.forName("com.mysql.jdbc.Driver");

            // Établir la connexion à la base de données
            cnx = DriverManager.getConnection(url, login, pwd);
            System.out.println("Connexion établie avec la base de données.");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
    }

    public Connection getCnx() {
        return cnx;
    }

    public static MyConnection getInstance() {
        if (instance == null) {
            instance = new MyConnection();
        }
        return instance;
    }
}

