package edu.esprit.services;

//import javafx.scene.control.TextField;

import edu.esprit.entities.reservation;
import edu.esprit.utiles.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceReservation extends IServices<reservation> {
    private Connection con;


    // Initialize the connection
    public ServiceReservation() {
        con = DataSource.getInstance().getCon(); // Initialize the connection
    }
    //private Connection cnx= DataSource.getInstance().getCon(); // Utilisez la connexion initialisée dans DataSource






    //@Override
    @Override
    public void ajouter(reservation reservation) throws SQLException {
        System.out.println("Début de l'exécution de la méthode ajouter...");
        System.out.println("État de la connexion avant l'exécution de la méthode ajouter : " + con.isClosed());
        //con = DataSource.getInstance().getCon(); // Get the connection here

        String req = "INSERT INTO reservation (checkin, checkout, nbguest, Hebergement_id, User_id) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pre = con.prepareStatement(req);
            System.out.println("Fin de l'exécution de la méthode ajouter.");
            System.out.println("État de la connexion après l'exécution de la méthode ajouter : " + con.isClosed());
            pre.setDate(1, reservation.getCheckin());
            pre.setDate(2, reservation.getCheckout());
            pre.setInt(3, reservation.getNbguest());
            pre.setString(4, reservation.getHebergement_id());
            pre.setString(5, reservation.getUser_id());

            pre.executeUpdate();
            System.out.println("Reservation ajoutée avec succès.");

    }


    @Override
    public void modifier(reservation reservation) throws SQLException {

        String req = "UPDATE reservation SET checkin=?, nbguest=?, checkout=?, Hebergement_id=?  WHERE id=?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setDate(1, reservation.getCheckin());
            pre.setInt(2, reservation.getNbguest());
            pre.setDate(3, reservation.getCheckout());
            pre.setString(4, reservation.getUser_id());
            pre.setString(4, reservation.getHebergement_id());
            pre.setInt(5,reservation.getId());
            pre.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error modifying reservation: " + e.getMessage());
        }

    }

    @Override
    public void supprimer(reservation reservation) throws SQLException {

        String req = "DELETE FROM reservation WHERE id=?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, reservation.getId());
            pre.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting reservation: " + e.getMessage());
        }

    }

    @Override
    public List<reservation> afficher() throws SQLException {
        //String req = "SELECT * FROM reservation";
        /*String req = "SELECT reservation.*, Heberegement.id AS Heberegement_id " +
                "FROM reservation " +
                "INNER JOIN Heberegement ON reservation.Heberegement_id = Heberegement.id";*/

      /*  String req = "SELECT reservation.*, Heberegement.id " +
                "FROM reservation " +
                " INNER JOIN Heberegement  ON reservation.Heberegement_id = Heberegement.id";*/

        String req = "SELECT reservation.*, Hebergement.id " +
                "FROM reservation " +
                " INNER JOIN Hebergement  ON reservation.Hebergement_id = Hebergement.id";
        List<reservation> list = new ArrayList<>();
        try (Statement ste = con.createStatement(); ResultSet res = ste.executeQuery(req)) {
            while (res.next()) {
                reservation reservation = new reservation();
                reservation.setId(res.getInt("id"));
                reservation.setCheckin(res.getDate("checkin"));
                reservation.setNbguest(res.getInt("nbguest"));
                reservation.setCheckout(res.getDate("checkout"));
                reservation.setUser_id(res.getString("user_id"));
                reservation.setHebergement_id(res.getString("Hebergement_id"));
                //reservation.setResdate(res.getTimestamp("resdate").toLocalDateTime());

                //Heberegement.setUser_id(res.getTimestamp(5).toLocalDateTime());

                list.add(reservation);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving Reservations: " + e.getMessage());
        }
        return list;
    }
}
