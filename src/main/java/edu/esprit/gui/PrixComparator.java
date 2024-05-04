package edu.esprit.gui;
import edu.esprit.entities.hebergement;
import java.util.Comparator;

public class PrixComparator implements Comparator<hebergement> {

    @Override
    public int compare(hebergement log1, hebergement log2) {
        double prix1 = log1.getPrix();
        double prix2 = log2.getPrix();

        // Comparez les prix et retournez le résultat
        if (prix1 < prix2) {
            return -1;
        } else if (prix1 > prix2) {
            return 1;
        } else {
            return 0;
        }
    }
}