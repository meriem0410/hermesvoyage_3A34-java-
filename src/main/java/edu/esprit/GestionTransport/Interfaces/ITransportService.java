package edu.esprit.GestionTransport.Interfaces;


import java.util.List;

public interface ITransportService<T> {
    void addTransport(T t); // Ajouter un transport
    void removeTransport(int id); // Supprimer un transport par ID
    void updateTransport(T t); // Mettre à jour les détails d'un transport
    T getTransportById(int id); // Récupérer un transport par son ID
    List<T> getAllTransports(); // Récupérer la liste de tous les transports
}

