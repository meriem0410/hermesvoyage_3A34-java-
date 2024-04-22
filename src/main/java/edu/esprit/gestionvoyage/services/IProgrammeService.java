package edu.esprit.gestionvoyage.services;

import edu.esprit.gestionvoyage.entities.Programme;

import java.util.List;

public interface IProgrammeService extends IService<Programme> {
    public List<Programme> getProgrammesVoyage(int idVoyage);
}
