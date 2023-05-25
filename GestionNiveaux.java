package cy_slide_jeudetaquin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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
        int score = 0;

        String ligne;
        while ((ligne = lecteur.readLine()) != null) {
            if (ligne.equals(".")) {
                // Ajouter le niveau actuel à la liste des niveaux
                if (niveauActuel != null) {
                	niveauActuel.setScore(score);
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
                
                // Lire la ligne suivante pour récupérer le score
                ligne = lecteur.readLine();
                score = Integer.parseInt(ligne.trim());
                niveauActuel.setScore(score);
                
            } else if (niveauActuel != null) {
                if (!ligne.startsWith("score:")) {
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
                    ligneIndex++;
                }
            }
        }

        lecteur.close();
        return niveaux;
    }
    
    public static void sauvegarderNiveaux(List<Niveau> niveaux, String cheminFichier) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(cheminFichier));

        for (Niveau niveau : niveaux) {
            writer.write(niveau.getLignes() + "x" + niveau.getColonnes());
            writer.newLine();
            writer.write(Integer.toString(niveau.getScore()));
            writer.newLine();

            for (int i = 0; i < niveau.getLignes(); i++) {
                for (int j = 0; j < niveau.getColonnes(); j++) {
                    int tuile = niveau.getTuile(i, j);
                    if (tuile == -1) {
                        writer.write("-");
                    } else {
                        writer.write(String.valueOf(tuile));
                    }
                    writer.write(" ");
                }
                writer.newLine();
            }

            writer.write(".");
            writer.newLine();
        }

        writer.close();
    }

}
