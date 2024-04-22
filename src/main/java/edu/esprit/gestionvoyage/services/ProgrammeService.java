package edu.esprit.gestionvoyage.services;

import edu.esprit.gestionvoyage.entities.Programme;
import edu.esprit.gestionvoyage.entities.Voyage;
import edu.esprit.gestionvoyage.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ProgrammeService implements IProgrammeService{
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajout(Programme programme) throws SQLException {
        String req = "INSERT INTO `programme`(`voyage_id`, `activite`, `dateDepart`, `dateRetour`, `description`) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, programme.getVoyageId());
            ps.setString(2, programme.getActivite());
            ps.setDate(3, new java.sql.Date(programme.getDateDepart().getTime()));
            ps.setDate(4, new java.sql.Date(programme.getDateRetour().getTime()));
            ps.setString(5, programme.getDescription());
            ps.executeUpdate();
            System.out.println("Programme ajouté avec succès !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void modif(Programme programme) {
        String req = "UPDATE programme SET activite = ?, dateDepart = ?, dateRetour = ?, description = ? WHERE id = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, programme.getActivite());
            ps.setDate(2, new java.sql.Date(programme.getDateDepart().getTime()));
            ps.setDate(3, new java.sql.Date(programme.getDateRetour().getTime()));
            ps.setString(4, programme.getDescription());
            ps.setInt(5, programme.getId());
            ps.executeUpdate();
            System.out.println("Programme modifié avec succès !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void supp(int id) {
        String req = "DELETE FROM programme WHERE id = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Programme supprimé avec succès !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Programme> getAll() {
        List<Programme> programmes = new ArrayList<>();
        String req = "SELECT * FROM programme";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Programme programme = new Programme();
                    programme.setId(rs.getInt("id"));
                    programme.setVoyageId(rs.getInt("voyage_id"));
                    programme.setActivite(rs.getString("activite"));
                    programme.setDateDepart(rs.getDate("dateDepart"));
                    programme.setDateRetour(rs.getDate("dateRetour"));
                    programme.setDescription(rs.getString("description"));

                    programmes.add(programme);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return programmes;
    }

    @Override
    public Programme getById(int voyageId) throws SQLException {
        String req = "SELECT * FROM programme WHERE voyageId = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, voyageId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Programme programme = new Programme();
                    programme.setId(rs.getInt("id"));
                    programme.setVoyageId(rs.getInt("voyageId"));
                    programme.setActivite(rs.getString("activite"));
                    programme.setDateDepart(rs.getDate("dateDepart"));
                    programme.setDateRetour(rs.getDate("dateRetour"));
                    programme.setDescription(rs.getString("description"));
                    return programme;
                } else {
                    System.out.println("Aucun programme trouvé pour le voyage donné");
                }
            }
        }
        return null;
    }

    @Override
    public List<Programme> getProgrammesVoyage(int idVoyage) {
        List<Programme> programmes = new ArrayList<>();
        String req = "SELECT * FROM programme WHERE voyage_id = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {

            ps.setInt(1, idVoyage);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Programme programme = new Programme();
                    programme.setId(rs.getInt("id"));
                    programme.setVoyageId(rs.getInt("voyage_id"));
                    programme.setActivite(rs.getString("activite"));
                    programme.setDateDepart(rs.getDate("dateDepart"));
                    programme.setDateRetour(rs.getDate("dateRetour"));
                    programme.setDescription(rs.getString("description"));

                    programmes.add(programme);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return programmes;
    }
}
