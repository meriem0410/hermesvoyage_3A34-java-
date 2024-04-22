package edu.esprit.gestionvoyage.entities;

public class Reservation {
    private int id;
    private int paysId;
    private boolean confirmed;
    private String mail;
    private int number;
    private String origine;
    private int age;
    private int userId;

    public Reservation() {
    }

    public Reservation(int id, int paysId, boolean confirmed, String mail, int number, String origine, int age, int userId) {
        this.id = id;
        this.paysId = paysId;
        this.confirmed = confirmed;
        this.mail = mail;
        this.number = number;
        this.origine = origine;
        this.age = age;
        this.userId = userId;
    }

    public Reservation(int paysId, boolean confirmed, String mail, int number, String origine, int age, int userId) {
        this.paysId = paysId;
        this.confirmed = confirmed;
        this.mail = mail;
        this.number = number;
        this.origine = origine;
        this.age = age;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPaysId() {
        return paysId;
    }

    public void setPaysId(int paysId) {
        this.paysId = paysId;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", paysId=" + paysId +
                ", confirmed=" + confirmed +
                ", mail='" + mail + '\'' +
                ", number=" + number +
                ", origine='" + origine + '\'' +
                ", age=" + age +
                ", userId=" + userId +
                '}';
    }
}
