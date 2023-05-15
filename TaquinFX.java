package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Random;

public class TaquinFX extends Application {
	
	private static final int GRID_SIZE = 3;
    private static final int TILE_SIZE = 100;

    private int[][] grille = new int[GRID_SIZE][GRID_SIZE];
    private GridPane gridPane = new GridPane();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Jeu du Taquin");

        initializeGrille();
        shuffleGrille();

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Button button = new Button(Integer.toString(grille[row][col]));
                button.setPrefSize(TILE_SIZE, TILE_SIZE);
                button.setOnAction(e -> moveTile(button));

                gridPane.add(button, col, row);
            }
        }

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeGrille() {
        int value = 1;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                grille[row][col] = value;
                value++;
            }
        }
        grille[GRID_SIZE - 1][GRID_SIZE - 1] = 0; // Case vide représentée par 0
    }

    private void shuffleGrille() {
        Random rand = new Random();
        int numMoves = 100; // Nombre de mouvements de mélange

        for (int i = 0; i < numMoves; i++) {
            int emptyRow = -1;
            int emptyCol = -1;

            // Rechercher la case vide
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    if (grille[row][col] == 0) {
                        emptyRow = row;
                        emptyCol = col;
                        break;
                    }
                }
            }

            // Choisir aléatoirement une tuile adjacente à la case vide
            ArrayList<Pair<Integer, Integer>> adjacentTiles = new ArrayList<>();
            if (emptyRow > 0) {
                adjacentTiles.add(new Pair<>(emptyRow - 1, emptyCol));
            }
            if (emptyRow < GRID_SIZE - 1) {
                adjacentTiles.add(new Pair<>(emptyRow + 1, emptyCol));
            }
            if (emptyCol > 0) {
                adjacentTiles.add(new Pair<>(emptyRow, emptyCol - 1));
            }
            if (emptyCol < GRID_SIZE - 1) {
                adjacentTiles.add(new Pair<>(emptyRow, emptyCol + 1));
            }

            int randomIndex = rand.nextInt(adjacentTiles.size());
            Pair<Integer, Integer> randomTile = adjacentTiles.get(randomIndex);

            int tileRow = randomTile.getKey();
            int tileCol = randomTile.getValue();

            // Échanger la tuile adjacente avec la case vide
            int temp = grille[tileRow][tileCol];
            grille[tileRow][tileCol] = 0;
            grille[emptyRow][emptyCol] = temp;
        }
    }


    private void moveTile(Button button) {
        int row = GridPane.getRowIndex(button);
        int col = GridPane.getColumnIndex(button);

        // Vérifier si la tuile peut être déplacée
        if ((row > 0 && grille[row - 1][col] == 0) ||
                (row < GRID_SIZE - 1 && grille[row + 1][col] == 0) ||
                (col > 0 && grille[row][col - 1] == 0) ||
                (col < GRID_SIZE - 1 && grille[row][col + 1] == 0)) {
            int emptyRow = -1;
            int emptyCol = -1;

            // Rechercher la case vide
            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
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

            // Vérifier si le puzzle est résolu
            if (estResolu()) {
                System.out.println("Félicitations ! Vous avez résolu le puzzle du taquin !");
                // Ajoutez ici le code pour gérer la résolution du puzzle
            }
        } else {
            System.out.println("Déplacement invalide !");
        }
    }

    private boolean estResolu() {
        int value = 1;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (grille[row][col] != value % (GRID_SIZE * GRID_SIZE)) {
                    return false;
                }
                value++;
            }
        }
        return true;
    }
}
