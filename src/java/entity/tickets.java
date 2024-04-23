package java.entity;
import java.util.Date;

public class tickets {
    private String depart;
    private String arrive;
    private float prix;
    private Date date;

    // Constructeur avec tous les attributs
    public tickets(String depart, String arrive, float prix, Date date) {
        this.depart = depart;
        this.arrive = arrive;
        this.prix = prix;
        this.date = date;
    }

    // Getters et Setters pour chaque attribut

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

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // Méthode toString pour afficher les informations du ticket

    @Override
    public String toString() {
        return "Ticket{" +
                "depart='" + depart + '\'' +
                ", arrive='" + arrive + '\'' +
                ", prix=" + prix +
                ", date=" + date +
                '}';
    }
}
