package edu.esprit.GestionTransport.Service;



import edu.esprit.GestionTransport.Entity.Transport;
import edu.esprit.GestionTransport.Interfaces.ITransportService;
import edu.esprit.GestionTransport.Util.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransportService implements ITransportService<Transport> {

    @Override
    public void addTransport(Transport transport) {
        String query = "INSERT INTO transport(typeTransport, description) VALUES (?, ?)";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setString(1, transport.getTypeTransport());
            pst.setString(2, transport.getDescription());
            pst.executeUpdate();
            System.out.println("Transport ajouté avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du transport: " + e.getMessage());
        }
    }

    @Override
    public void removeTransport(int id) {
        String query = "DELETE FROM transport WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Transport supprimé avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du transport: " + e.getMessage());
        }
    }

    @Override
    public void updateTransport(Transport transport) {
        String query = "UPDATE transport SET typeTransport = ?, description = ? WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setString(1, transport.getTypeTransport());
            pst.setString(2, transport.getDescription());
            pst.setInt(3, transport.getId());
            pst.executeUpdate();
            System.out.println("Transport mis à jour avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du transport: " + e.getMessage());
        }
    }

    @Override
    public Transport getTransportById(int id) {
        String query = "SELECT * FROM transport WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Transport transport = new Transport();
                    transport.setId(rs.getInt("id"));
                    transport.setTypeTransport(rs.getString("typeTransport"));
                    transport.setDescription(rs.getString("description"));
                    return transport;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du transport: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Transport> getAllTransports() {
        List<Transport> transportList = new ArrayList<>();
        String query = "SELECT * FROM transport";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Transport transport = new Transport();
                    transport.setId(rs.getInt("id"));
                    transport.setTypeTransport(rs.getString("typeTransport"));
                    transport.setDescription(rs.getString("description"));
                    transportList.add(transport);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des transports: " + e.getMessage());
        }
        return transportList;
    }
}
