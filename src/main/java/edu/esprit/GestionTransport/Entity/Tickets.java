package edu.esprit.GestionTransport.Entity;

public class Tickets {
    private int id;
    private String depart;
    private String arrive;
    private double prix;
    private String typeTransport;

    public Tickets() {
    }

    public Tickets(int id, String depart, String arrive, double prix, String typeTransport) {
        this.id = id;
        this.depart = depart;
        this.arrive = arrive;
        this.prix = prix;
        this.typeTransport = typeTransport;
    }

    public Tickets(String depart, String arrive, double prix, String typeTransport) {
        this.depart = depart;
        this.arrive = arrive;
        this.prix = prix;
        this.typeTransport = typeTransport;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getTypeTransport() {
        return typeTransport;
    }

    public void setTypeTransport(String typeTransport) {
        this.typeTransport = typeTransport;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", depart='" + depart + '\'' +
                ", arrive='" + arrive + '\'' +
                ", prix=" + prix +
                ", typeTransport='" + typeTransport + '\'' +
                '}';
    }
}

