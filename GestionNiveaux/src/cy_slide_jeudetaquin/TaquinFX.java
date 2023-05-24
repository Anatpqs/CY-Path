package cy_slide_jeudetaquin;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.layout.Pane;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Arrays;
import java.util.PriorityQueue;

public class TaquinFX extends Application {
	
	private Button niveauSuivantButton;
	int indiceNiveau = 0; // Indice du niveau actuellement affiché
    private static final int TILE_SIZE = 100;

    private int[][] grille;
    private GridPane gridPane = new GridPane();
    private Scene scene;

    public void setGrille(Niveau niveau) {
        this.grille = niveau.getGrille();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
    	String cheminFichier = "Niveau.txt";  
   	 	List<Niveau> levels = GestionNiveaux.chargerNiveaux(cheminFichier);
        primaryStage.setTitle("Jeu du Taquin");    
        
        Niveau niveau = levels.get(0);
        setGrille(niveau);
        shuffleGrille1();
	    displayGrid(niveau);
        
	    gridPane.setAlignment(Pos.CENTER);   
        scene = new Scene(gridPane);
        scene.setOnKeyPressed(event -> handleKeyPress(event.getCode()));
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        
        niveauSuivantButton = new Button("Niveau suivant");
        gridPane.add(niveauSuivantButton, 0, grille.length); // Ajoute le bouton en bas de la grille    
        
        primaryStage.setScene(scene);
        
        niveauSuivantButton.setOnAction(event -> {
        	System.out.println("Bouton appuyer");
            indiceNiveau++; // Incrémente l'indice du niveau
            if (indiceNiveau >= levels.size()) {
                indiceNiveau = 0; // Reviens au premier niveau si on atteint la fin de la liste
            }              
            Niveau niveauSuivant = levels.get(indiceNiveau);
            setGrille(niveauSuivant); 
            shuffleGrille1();
            displayGrid(niveauSuivant);
            primaryStage.sizeToScene();
        });
        primaryStage.show();

    } 
    public void displayGrid(Niveau niveau) {
    	setGrille(niveau);
        
        int lignes = grille.length;
        int colonnes = grille[0].length;
        
        // Supprime les éléments de la grille précédente
        gridPane.getChildren().clear();
        
        // Adjuste la taille de la fenêtre suivant la taille de la grille
        gridPane.setPrefSize(colonnes * TILE_SIZE, lignes * TILE_SIZE);        
        
        for (int row = 0; row < lignes; row++) {
            for (int col = 0; col < colonnes; col++) {
                if (grille[row][col] != 0 && grille[row][col] != -1) {
                    Button button = new Button(Integer.toString(grille[row][col]));
                    button.setPrefSize(TILE_SIZE, TILE_SIZE);
                    button.setOnAction(e -> moveTile(button));

                    gridPane.add(button, col, row);
                } else if (grille[row][col] == -1) {
                    Pane emptyPane = new Pane();
                    emptyPane.setPrefSize(TILE_SIZE, TILE_SIZE);
                    emptyPane.getStyleClass().add("case-vide");
                    gridPane.add(emptyPane, col, row);
                }
            }
        }
        //gridPane.add(niveauSuivantButton, 0, grille.length); // Ajoute le bouton en bas de la grille    
        
    }
    
    private void shuffleGrille1() { // Permet d'initialiser la case vide à la dernière tuile valide
        int emptyRow = grille.length - 1;
        int emptyCol = grille[0].length - 1;
        Random rand = new Random();
        int numSwaps = 100; // Nombre d'échanges de nombres

        for (int i = 0; i < numSwaps; i++) {
            int row1, col1, row2, col2;

            do {
                row1 = rand.nextInt(grille.length);
                col1 = rand.nextInt(grille[0].length);
            } while (grille[row1][col1] == -1);

            do {
                row2 = rand.nextInt(grille.length);
                col2 = rand.nextInt(grille[0].length);
            } while (grille[row2][col2] == -1);

            // Vérifier si l'une des positions est la case vide
            if (row1 == emptyRow && col1 == emptyCol) {
                emptyRow = row2;
                emptyCol = col2;
            } else if (row2 == emptyRow && col2 == emptyCol) {
                emptyRow = row1;
                emptyCol = col1;
            }

            // Échanger les nombres des deux positions
            int temp = grille[row1][col1];
            grille[row1][col1] = grille[row2][col2];
            grille[row2][col2] = temp;
        }

        grille[emptyRow][emptyCol] = 0; // Mettre à jour la position de la case vide
    }

    // ------------------------------------------------------------------------------------------------//
    private void moveTile(Button button) {
        int row = GridPane.getRowIndex(button);
        int col = GridPane.getColumnIndex(button);

        // Vérifier si la tuile peut être déplacée
        if ((row > 0 && grille[row - 1][col] == 0) ||
                (row < grille.length - 1 && grille[row + 1][col] == 0) ||
                (col > 0 && grille[row][col - 1] == 0) ||
                (col < grille[0].length - 1 && grille[row][col + 1] == 0)) {
            int emptyRow = -1;
            int emptyCol = -1;

            // Rechercher la case vide
            for (int i = 0; i < grille.length; i++) {
                for (int j = 0; j < grille[0].length; j++) {
                    if (grille[i][j] == 0) {
                        emptyRow = i;
                        emptyCol = j;
                        break;
                    }
                }
            }

            // Échanger la tuile avec la case vide
            int temp = grille[row][col];
            grille[row][col] = 0;
            grille[emptyRow][emptyCol] = temp;

            // Mettre à jour les positions des boutons
            gridPane.getChildren().remove(button);
            gridPane.add(button, emptyCol, emptyRow);

           
        } else {
            System.out.println("Déplacement invalide !");
        }
    }
    private void handleKeyPress(KeyCode keyCode) {
        Button button = getSelectedButton();
        if (button != null) {
            int row = GridPane.getRowIndex(button);
            int col = GridPane.getColumnIndex(button);

            if (keyCode == KeyCode.UP && row > 0 && grille[row - 1][col] == 0) {
                moveTile((Button) getNodeByRowColumnIndex(row - 1, col));
            } else if (keyCode == KeyCode.DOWN && row < grille.length - 1 && grille[row + 1][col] == 0) {
                moveTile((Button) getNodeByRowColumnIndex(row + 1, col));
            } else if (keyCode == KeyCode.LEFT && col > 0 && grille[row][col - 1] == 0) {
                moveTile((Button) getNodeByRowColumnIndex(row, col - 1));
            } else if (keyCode == KeyCode.RIGHT && col < grille[0].length - 1 && grille[row][col + 1] == 0) {
                moveTile((Button) getNodeByRowColumnIndex(row, col + 1));
            }
        }
    }
    private Node getNodeByRowColumnIndex(final int row, final int column) {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }


    private Button getSelectedButton() {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (button.isFocused()) {
                    return button;
                }
            }
        }
        return null;
    }
 }                               