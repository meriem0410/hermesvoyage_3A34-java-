package edu.esprit.gestionvoyage.services;

import edu.esprit.gestionvoyage.entities.Voyage;

import java.sql.SQLException;
import java.util.Set;

public interface IVoyageService extends IService<Voyage> {
    public Voyage getvoyBydestination( String destination) throws SQLException;
}
