package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestionNiveaux {
	public static List<Niveau> chargerNiveaux(String cheminFichier) throws IOException {
        BufferedReader lecteur = new BufferedReader(new FileReader(cheminFichier));

        List<Niveau> niveaux = new ArrayList<>();
        Niveau niveauActuel = null;
        int ligneIndex = 0; // Ajout de la variable ligneIndex
        int compteurNiveau = 1; // Ajout du compteur de niveau

        String ligne;
        while ((ligne = lecteur.readLine()) != null) {
            if (ligne.equals(".")) {
                // Ajouter le niveau actuel à la liste des niveaux
                if (niveauActuel != null) {
                    niveaux.add(niveauActuel);
                }
                // Créer un nouveau niveau
                niveauActuel = null;
                ligneIndex = 0; // Réinitialiser ligneIndex
                compteurNiveau++; // Incrémenter le compteur de niveau
            } else if (niveauActuel == null) {
                // Créer un nouveau niveau avec les dimensions spécifiées
                String[] dimensions = ligne.split("x");
                int lignes = Integer.parseInt(dimensions[0]);
                int colonnes = Integer.parseInt(dimensions[1]);
                niveauActuel = new Niveau("Niveau " + compteurNiveau, lignes, colonnes); // Ajouter le nom du niveau
            } else {
                // Remplir la grille du niveau actuel
                String[] tuiles = ligne.split(" ");
                for (int colonneIndex = 0; colonneIndex < tuiles.length; colonneIndex++) {
                    String tuile = tuiles[colonneIndex];
                    if (tuile.equals("-")) {
                        // Marquer une tuile vide (trou)
                        niveauActuel.setTuile(ligneIndex, colonneIndex, -1);
                    } else {
                        // Convertir le numéro ou symbole en tuile
                        int numeroTuile = Integer.parseInt(tuile);
                        niveauActuel.setTuile(ligneIndex, colonneIndex, numeroTuile);
                    }
                }
                ligneIndex++; // Incrémenter ligneIndex après chaque ligne lue
            }
        }

        lecteur.close();
        return niveaux;
    }
}
