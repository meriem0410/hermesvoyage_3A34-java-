package edu.esprit.interfaces;

import java.util.Set;

public interface EService <T>{

    void addEntity(T t);
    void deleteEntity(T t);
    Set<T> getAllData();
}
