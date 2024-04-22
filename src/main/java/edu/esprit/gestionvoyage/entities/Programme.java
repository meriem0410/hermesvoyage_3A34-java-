package edu.esprit.gestionvoyage.entities;

import java.sql.Date;

public class Programme {
    private int id;
    private int voyageId;
    private String activite;
    private Date dateDepart;
    private Date dateRetour;
    private String description;
    public Programme() {
    }
    public Programme(int id, int voyageId, String activite, Date dateDepart, Date dateRetour, String description) {
        this.id = id;
        this.voyageId = voyageId;
        this.activite = activite;
        this.dateDepart = dateDepart;
        this.dateRetour = dateRetour;
        this.description = description;
    }
    public Programme(int voyageId, String activite, Date dateDepart, Date dateRetour, String description) {
        this.voyageId = voyageId;
        this.activite = activite;
        this.dateDepart = dateDepart;
        this.dateRetour = dateRetour;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVoyageId() {
        return voyageId;
    }

    public void setVoyageId(int voyageId) {
        this.voyageId = voyageId;
    }

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Date getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(Date dateRetour) {
        this.dateRetour = dateRetour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Programme{" +
                "id=" + id +
                ", voyageId=" + voyageId +
                ", activite='" + activite + '\'' +
                ", dateDepart=" + dateDepart +
                ", dateRetour=" + dateRetour +
                ", description='" + description + '\'' +
                '}';
    }
}
