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

    public String getDescriptionTypeById(int id_type) throws SQLException {
        String query = "SELECT description FROM typelog WHERE id = ?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, id_type);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return rs.getString("description");
        } else {
            return null; // Gérer le cas où aucun type avec cet ID de hebergement n'est trouvé
        }
    }


    public void ajouter(hebergement hebergement) throws SQLException {
        con = DataSource.getInstance().getCon(); // Get the connection here
        String req = "insert into hebergement (adresse, description, amenities, image, maxguest, prix) values('" + hebergement.getAdresse() + "','" + hebergement.getDescription() + "','" + hebergement.getAmenities() + "','" + hebergement.getImage() + "','" + hebergement.getMaxguest() + "','" + hebergement.getPrix() + "')";
        ste = con.createStatement();
        ste.executeUpdate(req);

    }
    /*
  @Override
  public void ajouter(hebergement hebergement) throws SQLException {
      String query = "INSERT INTO hebergement (adresse, description, amenities, image, maxguest, prix, id_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement pst = con.prepareStatement(query);
      pst.setString(5, hebergement.getAdresse());
      pst.setString(1, hebergement.getDescription());
      pst.setString(6, hebergement.getamenities());
      pst.setString(, hebergement.getImage());
      pst.setInt(5, hebergement.getmaxguest());
      pst.setInt(6, hebergement.getprix());
      pst.setInt(7, hebergement.getId_Type());
      pst.executeUpdate();
  }*/


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
        try (PreparedStatement ste = con.prepareStatement(req);
             ResultSet res = ste.executeQuery(req)) {
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





 /* @Override
  public List<hebergement> afficher() throws SQLException {
      List<hebergement> hebergements = new ArrayList<>();
      String req = "SELECT * FROM hebergement";
      try (Statement ste = con.createStatement(); ResultSet res = ste.executeQuery(req)) {
          while (res.next()) {
              int id_log = res.getInt("id_log");
              String adresse = res.getString("adresse");
              String description = res.getString("description");
              String amenities = res.getString("amenities");
              String image = res.getString("image");
              int maxguest = res.getInt("maxguest");
              int prix = res.getInt("prix");
              int id_type = res.getInt("id_type");

              // Récupérer la description du type de hebergement en utilisant son ID
              String descriptionType = getDescriptionTypeById(id_type);

              // Créer l'objet hebergement avec la description du type de hebergement
              hebergement l = new hebergement(id_log, adresse, description, amenities, image, maxguest, prix);
             // l.setDescriptionType(descriptionType);

              // Ajouter l'objet hebergement à la liste
              hebergements.add(l);
          }
      }
      return hebergements;
  }*/


