package edu.esprit.entities;

import java.util.Date;
import java.util.Objects;

public class reservation {

        private int id , nbguest;
        private Date checkin , checkout;
        private String user_id;

        private String heberegement_id;

        public reservation (){

        }

        public reservation(int id, int nbguest, Date checkin, Date checkout) {
                this.id = id;
                this.nbguest = nbguest;
                this.checkin = checkin;
                this.checkout = checkout;
        }

        public reservation(Date checkinDate, Date checkoutDate, int nombrePlaces, String hebergementIdValue, String userIdValue) {
                this.id = id;
                this.nbguest = nombrePlaces;
                this.checkin = checkinDate;
                this.checkout = checkoutDate;
                this.heberegement_id=hebergementIdValue;
                this.user_id=userIdValue;
        }

        public int getId() {
                return id;
        }

        public int getNbguest() {
                return nbguest;
        }

        public java.sql.Date getCheckin() {
                return (java.sql.Date) checkin;
        }

        public java.sql.Date getCheckout() {
                return (java.sql.Date) checkout;
        }


        //setters

        public void setId(int id) {
                this.id = id;
        }

        public void setNbguest(int nbguest) {
                this.nbguest = nbguest;
        }

        public void setCheckin(Date checkin) {
                this.checkin = checkin;
        }

        public void setCheckout(Date checkout) {
                this.checkout = checkout;
        }

        //equals

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                reservation that = (reservation) o;
                return id == that.id && nbguest == that.nbguest && Objects.equals(checkin, that.checkin) && Objects.equals(checkout, that.checkout);
        }

        @Override
        public int hashCode() {
                return Objects.hash(id, nbguest, checkin, checkout);
        }

        @Override
        public String toString() {
                return "reservation{" +
                        "id=" + id +
                        ", nbguest=" + nbguest +
                        ", checkin=" + checkin +
                        ", checkout=" + checkout +
                        '}';
        }

        public String getUser_id() {
                return user_id;
        }

        public String getHebergement_id() {
                return heberegement_id;
        }

        public void setHebergement_id(String heberegementId) {
                this.heberegement_id = heberegementId;
        }


        public void setCheckin(String text) {
        }

        public void setCheckout(String text) {
        }

    public void setUser_id(String userId) {
                this.user_id = user_id;
    }
}
