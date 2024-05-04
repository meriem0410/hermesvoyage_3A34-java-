package edu.esprit.services;

import edu.esprit.utiles.DataSource;
import edu.esprit.entities.hebergement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Servicehebergement extends IServices<hebergement> {
   // private Connection con;
   private  Connection con;
    private Statement ste;

    public Servicehebergement() {
        con = DataSource.getInstance().getCon(); // Initialize the connection
    }

    public static void ajouter() {
    }

   /* public int getHebergementById(int id) throws SQLException {
        String query = "SELECT maxguest FROM hebergement WHERE id = ?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return rs.getInt("maxguest");
        }
    }*/



    public void ajouter(hebergement hebergement) throws SQLException {
        con = DataSource.getInstance().getCon(); // Get the connection here
        String req = "insert into hebergement (adresse, description, amenities, image, maxguest, prix) values('" + hebergement.getAdresse() + "','" + hebergement.getDescription() + "','" + hebergement.getAmenities() + "','" + hebergement.getImage() + "','" + hebergement.getMaxguest() + "','" + hebergement.getPrix() + "')";
        ste = con.createStatement();
        ste.executeUpdate(req);

    }



    public  void modifier(hebergement hebergement) throws SQLException {
        //con = DataSource.getInstance().getCon();
        String req = "UPDATE hebergement SET adresse=?, description=?, amenities=?, image=?, maxguest=?, prix=? WHERE id=?";
       try ( PreparedStatement pre = con.prepareStatement(req)){
        pre.setString(1, hebergement.getAdresse());
        pre.setString(2, hebergement.getDescription());
        pre.setString(3, hebergement.getAmenities());
        pre.setString(4, hebergement.getImage());
        pre.setInt(5, hebergement.getMaxguest());
        pre.setFloat(6, hebergement.getPrix());
        pre.setInt(7, hebergement.getId());
        //pre.setInt(8, hebergement.getId_type());
        // pre.setInt(8, hebergement.getId_Type());
        pre.executeUpdate();
    }catch(SQLException e) {
            System.err.println("Error modifying reservation: " + e.getMessage());
        }
    }

    @Override
    public void supprimer(hebergement hebergement) throws SQLException {
        con = DataSource.getInstance().getCon();
        String req = "DELETE FROM hebergement WHERE id=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, hebergement.getId());
        pre.executeUpdate();
        //return false;
    }





    @Override
    public List<hebergement> afficher() throws SQLException {
        List<hebergement> hebergements = new ArrayList<>();
        String req = "SELECT * FROM hebergement";

            PreparedStatement ste = con.prepareStatement(req);
            ResultSet res = ste.executeQuery(req);
            while (res.next()) {
                hebergement l = new hebergement();
                l.setId(res.getInt("id"));
                l.setAdresse(res.getString("adresse"));
                l.setAmenities(res.getString("amenities"));
                l.setDescription(res.getString("description"));
                l.setImage(res.getString("image"));
                l.setMaxguest(res.getInt("maxguest"));
                l.setPrix((int) res.getInt("prix"));
                //logement.setId_type(resultSet.getInt("id_type"));
                hebergements.add(l);


            }

        return hebergements;
    }





     public int getHebergementIdByName(String eventName) throws SQLException {
        con = DataSource.getInstance().getCon(); // Assurez-vous que votre connexion est initialisée
        String query = "SELECT id FROM hebergement";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, eventName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        }
        // Gérer le cas où aucun ID n'est trouvé pour le nom de l'événement
        return -1;
    }


    public List<String> getAllEventNames() {
        String req = "SELECT id FROM hebergement";
        List<String> eventNames = new ArrayList<>();
        try (Statement ste = con.createStatement(); ResultSet res = ste.executeQuery(req)) {
            while (res.next()) {
                String eventName = res.getString("id") ;
                eventNames.add(eventName);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving event names: " + e.getMessage());
        }
        return eventNames;
    }
}