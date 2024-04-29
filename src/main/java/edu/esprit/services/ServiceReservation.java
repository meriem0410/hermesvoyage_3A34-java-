package edu.esprit.services;

//import javafx.scene.control.TextField;

import edu.esprit.entities.reservation;
import edu.esprit.utiles.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceReservation extends IServices<reservation> {
    private  Connection cnx;

    private Statement ste;
    public ServiceReservation() {
        cnx = DataSource.getInstance().getCon();
    }

    final Servicehebergement SE = new Servicehebergement();




    //@Override
    @Override
    public void ajouter(reservation reservation) throws SQLException {
        //cnx = DataSource.getInstance().getCon(); // Supprimez cette ligne car vous avez déjà initialisé la connexion dans le constructeur
       /* String req = "INSERT INTO reservation (checkin, nbguest, checkout, user_id, Hebergement_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setDate(1, new java.sql.Date(reservation.getCheckin().getTime())); // Convertissez la Date en java.sql.Date
            pre.setInt(2, reservation.getNbguest());
            pre.setDate(3, new java.sql.Date(reservation.getCheckout().getTime())); // Convertissez la Date en java.sql.Date
            pre.setString(4, reservation.getUser_id());
            pre.setString(5, reservation.getHebergement_id());

            pre.executeUpdate();
            System.out.println("Reservation ajoutée avec succès.");
        } catch (SQLException e) {
            System.err.println("Error adding Reservation: " + e.getMessage());
        }*/
        cnx = DataSource.getInstance().getCon(); // Get the connection here
        String req = "insert into reservation (checkin, checkout, nbguest, Hebergement_id, User_id) values('" + reservation.getCheckin() + "','" + reservation.getCheckout() + "','" + reservation.getNbguest() + "','" + reservation.getHebergement_id() + "','" + reservation.getUser_id() + "')";
        ste = cnx.createStatement();
        ste.executeUpdate(req);
    }


    @Override
    public void modifier(reservation reservation) throws SQLException {

        String req = "UPDATE reservation SET checkin=?, nbguest=?, checkout=?, Hebergement_id=?  WHERE id=?";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
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
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
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
        try (Statement ste = cnx.createStatement(); ResultSet res = ste.executeQuery(req)) {
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
