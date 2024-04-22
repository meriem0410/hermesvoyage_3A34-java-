package edu.esprit.gestionvoyage.services;

import edu.esprit.gestionvoyage.entities.Voyage;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface IService<T> {
    public void ajout(T t) throws SQLException;
    public void modif(T t);
    public void supp(int id );
    public List<T> getAll();
    public T getById( int id) throws SQLException;
}
