package edu.esprit.gestionvoyage.entities;

import java.sql.Date;

public class Voyage {
    private int id;
    private String destination;
    private  double prix;
    private Date date;
    private String type;

    public Voyage() {
    }

    public Voyage(int id, String destination, double prix, Date date, String type) {
        this.id = id;
        this.destination = destination;
        this.prix = prix;
        this.date = date;
        this.type = type;
    }

    public Voyage(String destination, double prix, Date date, String type) {
        this.destination = destination;
        this.prix = prix;
        this.date = date;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Voyage{" +
                "destination='" + destination + '\'' +
                ", prix=" + prix +
                ", date=" + date +
                ", type='" + type + '\'' +
                '}';
    }
}
