package edu.esprit.entities;

import java.util.Objects;

public class hebergement {
    private int id , prix , maxguest;
    private String adresse, description , amenities , image;

    //byte[] imageData;


    public hebergement(int prix, int maxguest,  String adresse,  String description, String amenities, String image) {
        this.prix = prix;
        this.maxguest = maxguest;
        this.adresse = adresse;
        this.description = description;
        this.amenities = amenities;
        this.image = image;
    }

    public hebergement () {
        this.id = id;
        this.prix = prix;
        this.maxguest = maxguest;
        this.adresse = adresse;
        this.description = description;
        this.amenities = amenities;
        this.image = image;
    }

    public hebergement(int id, String adresse, String description, Integer prix, Integer maxguest, String amenities, String image) {

    }

    public hebergement(int prix, int maxguest, String adresse, String description, String image) {
        this.id = id;
        this.prix = prix;
        this.maxguest = maxguest;
        this.adresse = adresse;
        this.description = description;
        //this.amenities = amenities;
        this.image = image;
    }

    public hebergement(String adresse, String description, Integer prix, Integer maxguest, String amenities, String image) {
        this.id = id;
        this.prix = prix;
        this.maxguest = maxguest;
        this.adresse = adresse;
        this.description = description;
        this.amenities = amenities;
        this.image = image;
    }


    public  int getId() {
        return id;
    }

    public  int getPrix() {
        return prix;
    }

    public  int getMaxguest() {
        return maxguest;
    }

    public  String getAdresse() {
        return adresse;
    }

    public  String getDescription() {
        return description;
    }

    public  String getAmenities() {
        return amenities;
    }

    public  String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public void setMaxguest(int maxguest) {
        this.maxguest = maxguest;
    }


    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }



    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "hebergement{" +
                "id=" + id +
                ", prix=" + prix +
                ", maxguest=" + maxguest +
                ", adresse='" + adresse + '\'' +
                ", description='" + description + '\'' +
                ", amenities='" + amenities + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        hebergement that = (hebergement) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
