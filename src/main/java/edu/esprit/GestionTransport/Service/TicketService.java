package edu.esprit.GestionTransport.Service;

import edu.esprit.GestionTransport.Entity.Tickets;
import edu.esprit.GestionTransport.Interfaces.ITicketService;
import edu.esprit.GestionTransport.Util.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketService implements ITicketService<Tickets> {

    @Override
    public boolean addTicket(Tickets ticket) {
        String query = "INSERT INTO tickets(depart, arrive, prix, typeTransport) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setString(1, ticket.getDepart());
            pst.setString(2, ticket.getArrive());
            pst.setDouble(3, ticket.getPrix());
            pst.setString(4, ticket.getTypeTransport());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du ticket: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean removeTicket(int id) {
        String query = "DELETE FROM tickets WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du ticket: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Tickets> getAllTickets() {
        List<Tickets> ticketList = new ArrayList<>();
        String query = "SELECT * FROM tickets";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Tickets ticket = new Tickets();
                ticket.setId(rs.getInt("id"));
                ticket.setDepart(rs.getString("depart"));
                ticket.setArrive(rs.getString("arrive"));
                ticket.setPrix(rs.getDouble("prix"));
                ticket.setTypeTransport(rs.getString("typeTransport"));
                ticketList.add(ticket);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des tickets: " + e.getMessage());
        }
        return ticketList;
    }

    @Override
    public Tickets getTicketById(int id) {
        String query = "SELECT * FROM tickets WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Tickets ticket = new Tickets();
                    ticket.setId(rs.getInt("id"));
                    ticket.setDepart(rs.getString("depart"));
                    ticket.setArrive(rs.getString("arrive"));
                    ticket.setPrix(rs.getDouble("prix"));
                    ticket.setTypeTransport(rs.getString("typeTransport"));
                    return ticket;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du ticket: " + e.getMessage());
        }
        return null;
    }

    public boolean updateTicket(Tickets ticket) {
        String query = "UPDATE tickets SET depart = ?, arrive = ?, prix = ?, typeTransport = ? WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setString(1, ticket.getDepart());
            pst.setString(2, ticket.getArrive());
            pst.setDouble(3, ticket.getPrix());
            pst.setString(4, ticket.getTypeTransport());
            pst.setInt(5, ticket.getId());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du ticket: " + e.getMessage());
            return false;
        }
    }
}
