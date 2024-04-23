package java.services;

import java.util.ArrayList;
import java.util.List;

import java.entity.Transport;

public class TransportService {

    // Simuler une source de données (par exemple, une liste en mémoire)
    private List<Transport> transportList;

    public TransportService() {
        // Initialisation de la liste des transports
        this.transportList = new ArrayList<>();

        // Ajouter quelques transports de test
        transportList.add(new Transport("Train", "Service de train rapide"));
        transportList.add(new Transport("Avion", "Service de transport aérien"));
        transportList.add(new Transport("Bus", "Service de transport terrestre"));
    }

    // Méthode pour ajouter un transport
    public void addTransport(Transport transport) {
        transportList.add(transport);
    }

    // Méthode pour récupérer tous les transports
    public List<Transport> getAllTransports() {
        return transportList;
    }

    // Méthode pour rechercher un transport par son nom
    public Transport findTransportByName(String name) {
        for (Transport transport : transportList) {
            if (transport.getTransport().equalsIgnoreCase(name)) {
                return transport;
            }
        }
        return null; // Retourne null si le transport n'est pas trouvé
    }

    // Méthode pour mettre à jour un transport
    public void updateTransport(Transport transport) {
        // Recherche du transport dans la liste par son nom
        Transport existingTransport = findTransportByName(transport.getTransport());
        if (existingTransport != null) {
            // Mise à jour des attributs du transport
            existingTransport.setDescription(transport.getDescription());
        }
    }

    // Méthode pour supprimer un transport
    public void deleteTransport(String name) {
        // Recherche du transport dans la liste par son nom
        Transport transportToRemove = findTransportByName(name);
        if (transportToRemove != null) {
            // Suppression du transport de la liste
            transportList.remove(transportToRemove);
        }
    }
}
