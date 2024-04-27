package edu.esprit.GestionTransport.Entity;
public class Transport {
    private int id;
    private String typeTransport;
    private String description;

    public Transport() {
    }

    public Transport(int id, String typeTransport, String description) {
        this.id = id;
        this.typeTransport = typeTransport;
        this.description = description;
    }

    public Transport(String typeTransport, String description) {
        this.typeTransport = typeTransport;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeTransport() {
        return typeTransport;
    }

    public void setTypeTransport(String typeTransport) {
        this.typeTransport = typeTransport;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Transport{" +
                "typeTransport='" + typeTransport + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

