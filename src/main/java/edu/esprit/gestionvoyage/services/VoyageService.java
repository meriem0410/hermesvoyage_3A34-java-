package edu.esprit.gestionvoyage.services;

import edu.esprit.gestionvoyage.entities.Voyage;
import edu.esprit.gestionvoyage.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoyageService implements IVoyageService {
    Connection cnx= DataSource.getInstance().getCnx();
    @Override
    public void ajout(Voyage voyage) throws SQLException {
        String req = "INSERT INTO `voyage`(`destination`, `prix`, `date`, `type`) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, voyage.getDestination());
            ps.setDouble(2, voyage.getPrix());
            ps.setDate(3, voyage.getDate());
            ps.setString(4, voyage.getType());
            ps.executeUpdate();
            System.out.println("Voyage ajouté");
        }
        catch (SQLException e){
            System.out.println (e.getMessage());
        }
    }

    public void modif(Voyage voyage)  {
        String req = "UPDATE voyage SET destination = ?, prix = ?, date = ?, type = ? WHERE id = ?";
        Date sqlDate = new Date(voyage.getDate().getTime());
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, voyage.getDestination());
            ps.setDouble(2, voyage.getPrix());
            ps.setDate(3, sqlDate);
            ps.setString(4, voyage.getType());
            ps.setInt(5, voyage.getId());
            ps.executeUpdate();
            System.out.println("Voyage modifié avec succès !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Méthode pour supprimer un voyage de la base de données en fonction de son ID
    public void supp(int id) {
        String req = "DELETE FROM voyage WHERE id = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Voyage supprimé avec succès !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<Voyage> getAll() {
        List<Voyage> voyages = new ArrayList<>();
        String req = "SELECT * FROM voyage";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String destination = rs.getString("destination");
                    double prix = rs.getDouble("prix");
                    java.sql.Date date = rs.getDate("date");
                    String type = rs.getString("type");
                    int id = rs.getInt("id");
                    voyages.add(new Voyage(id,destination, prix, date, type));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return voyages;
    }

    @Override
    public Voyage getById(int id) throws SQLException {
        String req = "SELECT * FROM voyage WHERE id = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String destination = rs.getString("destination");
                    double prix = rs.getDouble("prix");
                    java.sql.Date date = rs.getDate("date");
                    String type = rs.getString("type");
                    return new Voyage(id,destination, prix, date, type);
                } else {
                    System.out.println("Aucun voyage trouvé pour la destination donnée");
                }
            }
        }
        return null;
    }


    @Override
    public Voyage getvoyBydestination(String destination) throws SQLException {
        String req = "SELECT * FROM voyage WHERE destination = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, destination);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    double prix = rs.getDouble("prix");
                    java.sql.Date date = rs.getDate("date");
                    String type = rs.getString("type");
                    return new Voyage(destination, prix, date, type);
                } else {
                    System.out.println("Aucun voyage trouvé pour la destination donnée");
                }
            }
        }
        return null;
    }
}
