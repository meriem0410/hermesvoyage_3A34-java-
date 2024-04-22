package edu.esprit.gestionvoyage.services;

import edu.esprit.gestionvoyage.entities.Reservation;
import edu.esprit.gestionvoyage.entities.Voyage;
import edu.esprit.gestionvoyage.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements IReservationService {
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajout(Reservation res) throws SQLException {
        String req = "INSERT INTO `res`(`pays_id`, `user_id`, `confirmed`, `mail`) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, res.getPaysId());
            ps.setInt(2, res.getUserId());
            ps.setBoolean(3, res.isConfirmed());
            ps.setString(4, res.getMail());
            ps.executeUpdate();
            System.out.println("Réservation ajoutée avec succès !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void modif(Reservation res) {
        String req = "UPDATE res SET pays_id = ?, user_id = ?, confirmed = ?, mail = ? WHERE id = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, res.getPaysId());
            ps.setInt(2, res.getUserId());
            ps.setBoolean(3, res.isConfirmed());
            ps.setString(4, res.getMail());
            ps.setInt(5, res.getId());
            ps.executeUpdate();
            System.out.println("Réservation modifiée avec succès !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void supp(int id) {
        String req = "DELETE FROM res WHERE id = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Réservation supprimée avec succès !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Reservation> getAll() {
        List<Reservation> reservations = new ArrayList<>();
        String req = "SELECT * FROM res";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Reservation res = new Reservation();
                    res.setId(rs.getInt("id"));
                    res.setPaysId(rs.getInt("pays_id"));
                    res.setUserId(rs.getInt("user_id"));
                    res.setConfirmed(rs.getBoolean("confirmed"));
                    res.setMail(rs.getString("mail"));

                    reservations.add(res);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return reservations;
    }

    @Override
    public Reservation getById(int id) throws SQLException {
        String req = "SELECT * FROM res WHERE id = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Reservation res = new Reservation();
                    res.setId(rs.getInt("id"));
                    res.setPaysId(rs.getInt("paysId"));
                    res.setUserId(rs.getInt("userId"));
                    res.setConfirmed(rs.getBoolean("confirmed"));
                    res.setMail(rs.getString("mail"));
                    return res;
                } else {
                    System.out.println("Aucun voyage trouvé pour la destination donnée");
                }
            }
        }
        return null;
    }
}
