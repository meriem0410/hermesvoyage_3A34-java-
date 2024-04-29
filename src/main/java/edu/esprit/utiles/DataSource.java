package edu.esprit.utiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

    private final String URL = "jdbc:mysql://localhost:3306/hermes1";

    private final String USER = "";

    private final String PASSWD ="";

    private Connection cnx ;
    private Connection con;


    public DataSource() {
        try {
            con = DriverManager.getConnection(URL , USER, PASSWD);
            System.out.println("connected to DB");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public Connection getCon() {
        return con;
    }
    public static DataSource instance;
    public static DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }
}
