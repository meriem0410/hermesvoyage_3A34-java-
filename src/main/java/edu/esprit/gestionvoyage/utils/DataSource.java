package edu.esprit.gestionvoyage.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private final String url = "jdbc:mysql://localhost:3306/webhermes";
    private final String username = "root";
    private final String password = "";
    private Connection cnx;


    private static DataSource instance;

    private DataSource() {
        try {
            cnx = DriverManager.getConnection(url, username, password);
            System.out.println("connected to db ");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }


    public Connection getCnx() {
        return cnx;
    }

    public void setCnx(Connection cnx) {
        this.cnx = cnx;
    }

}
