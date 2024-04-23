package java.services;

import java.entity.tickets;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketService {

    // Simuler une source de données (par exemple, une liste en mémoire)
    private List<tickets> ticketList;

    public TicketService() {
        // Initialisation de la liste des tickets
        this.ticketList = new ArrayList<>();

        // Ajouter quelques tickets de test
        ticketList.add(new tickets("Paris", "London", 100.0f, new Date()));
        ticketList.add(new tickets("New York", "Tokyo", 800.0f, new Date()));
        ticketList.add(new tickets("Rome", "Berlin", 150.0f, new Date()));
    }

    // Méthode pour ajouter un ticket
    public void addTicket(tickets ticket) {
        ticketList.add(ticket);
    }

    // Méthode pour récupérer tous les tickets
    public List<tickets> getAllTickets() {
        return ticketList;
    }

    // Méthode pour rechercher un ticket par ses détails (par exemple, départ et arrivée)
    public tickets findTicket(String depart, String arrive) {
        for (tickets ticket : ticketList) {
            if (ticket.getDepart().equalsIgnoreCase(depart) && ticket.getArrive().equalsIgnoreCase(arrive)) {
                return ticket;
            }
        }
        return null; // Retourne null si le ticket n'est pas trouvé
    }

    // Méthode pour mettre à jour un ticket
    public void updateTicket(tickets ticket) {
        // Recherche du ticket dans la liste par ses détails (départ et arrivée)
        tickets existingTicket = findTicket(ticket.getDepart(), ticket.getArrive());
        if (existingTicket != null) {
            // Mise à jour des attributs du ticket
            existingTicket.setPrix(ticket.getPrix());
            existingTicket.setDate(ticket.getDate());
        }
    }

    // Méthode pour supprimer un ticket
    public void deleteTicket(String depart, String arrive) {
        // Recherche du ticket dans la liste par ses détails (départ et arrivée)
        tickets ticketToRemove = findTicket(depart, arrive);
        if (ticketToRemove != null) {
            // Suppression du ticket de la liste
            ticketList.remove(ticketToRemove);
        }
    }
}
