package cy_slide_jeudetaquin;

import javafx.application.Application;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
    	 String cheminFichier = "Niveau.txt";  
    	 List<Niveau> levels = GestionNiveaux.chargerNiveaux(cheminFichier);
         for (Niveau niveau : levels) {
             niveau.afficherNiveau();
             System.out.println("----------"); // Afficher une séparation entre les niveaux                 
         }
         
	      // Modifier le score du niveau 1 (indice 0)
	      Niveau niveau1 = levels.get(0);
	      niveau1.setScore(20);
	      Niveau niveau2 = levels.get(1);
	      niveau2.setScore(5);
	      Niveau niveau3 = levels.get(2);
	      niveau3.setScore(2);
	
	      // Appeler la méthode pour sauvegarder les niveaux avec les modifications
	      GestionNiveaux.sauvegarderNiveaux(levels, cheminFichier);

         Application.launch(TaquinFX.class, args);
     }
    //TODO Faire la sélection de la tuile VIDE en fonction de l'allure de la grille (ex: Une grille qui n'a pas de tuile à la dernière place)
    //TODO Résoudre bouton suivant qui disparait lorsqu'on clear les tuiles
    //TODO changer la fonction shuffle pour que la Tuile 0 soit la tuile vide
    
    //TODO changer homepage.record(niveau.getScore()); dans le fichier de théo
}