package edu.esprit.GestionTransport.Interfaces;

import java.util.List;

public interface ITicketService<T> {
    boolean addTicket(T t);
    boolean removeTicket(int id);
    List<T> getAllTickets();
    T getTicketById(int id);
}
