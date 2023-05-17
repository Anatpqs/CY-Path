package application;

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
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.layout.Pane;

public class TaquinFX extends Application {

	private static final int GRID_SIZE = 4;
    private static final int TILE_SIZE = 100;

    private int[][] grille = new int[GRID_SIZE][GRID_SIZE];
    private GridPane gridPane = new GridPane();
    private Scene scene;


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Jeu du Taquin");
        
        initializeGrille();
        shuffleGrille1();
     
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
            	if(grille[row][col] != 0 && grille[row][col] != -1){
	            	Button button = new Button(Integer.toString(grille[row][col]));
	                button.setPrefSize(TILE_SIZE, TILE_SIZE);
	                //button.getStyleClass().add("case");
	                button.setOnAction(e -> moveTile(button));
	
	                gridPane.add(button, col, row);
            	} else if(grille[row][col] == -1){
                    Pane emptyPane = new Pane();
                    emptyPane.setPrefSize(TILE_SIZE, TILE_SIZE);
                    emptyPane.getStyleClass().add("case-vide");
                    gridPane.add(emptyPane, col, row);
                }
            	
            }
        }
        

        gridPane.setAlignment(Pos.CENTER);   

        scene = new Scene(gridPane);
        scene.setOnKeyPressed(event -> handleKeyPress(event.getCode()));
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        
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
        grille[0][0] = -1; // Case inexistante par -1
    }

    
    private void shuffleGrille1() {
        Random rand = new Random();
        int numSwaps = 100; // Nombre d'échanges de nombres

        for (int i = 0; i < numSwaps; i++) {
            int row1, col1, row2, col2;

            do {
                row1 = rand.nextInt(GRID_SIZE);
                col1 = rand.nextInt(GRID_SIZE);
            } while (grille[row1][col1] == -1);

            do {
                row2 = rand.nextInt(GRID_SIZE);
                col2 = rand.nextInt(GRID_SIZE);
            } while (grille[row2][col2] == -1);

            // Échanger les nombres des deux positions
            int temp = grille[row1][col1];
            grille[row1][col1] = grille[row2][col2];
            grille[row2][col2] = temp;
            }
    }
    
    private void shuffleGrille2() {
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
            if (emptyRow > 0 && grille[emptyRow - 1][emptyCol] != -1) {
                adjacentTiles.add(new Pair<>(emptyRow - 1, emptyCol));
            }
            if (emptyRow < GRID_SIZE - 1 && grille[emptyRow + 1][emptyCol] != -1) {
                adjacentTiles.add(new Pair<>(emptyRow + 1, emptyCol));
            }
            if (emptyCol > 0 && grille[emptyRow][emptyCol - 1] != -1) {
                adjacentTiles.add(new Pair<>(emptyRow, emptyCol - 1));
            }
            if (emptyCol < GRID_SIZE - 1 && grille[emptyRow][emptyCol + 1] != -1) {
                adjacentTiles.add(new Pair<>(emptyRow, emptyCol + 1));
            }

            int randomIndex = rand.nextInt(adjacentTiles.size());
            Pair<Integer, Integer> randomTile = adjacentTiles.get(randomIndex);

            int tileRow = randomTile.getKey();
            int tileCol = randomTile.getValue();

            // Échanger la tuile adjacente avec la case vide, en évitant les tuiles avec une valeur de -1
            if (grille[tileRow][tileCol] != -1) {
                int temp = grille[tileRow][tileCol];
                grille[tileRow][tileCol] = 0;
                grille[emptyRow][emptyCol] = temp;
            }
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
    
    private void handleKeyPress(KeyCode keyCode) {
        Button button = getSelectedButton();
        if (button != null) {
            int row = GridPane.getRowIndex(button);
            int col = GridPane.getColumnIndex(button);

            if (keyCode == KeyCode.UP && row > 0 && grille[row - 1][col] == 0) {
                moveTile((Button) getNodeByRowColumnIndex(row - 1, col));
            } else if (keyCode == KeyCode.DOWN && row < GRID_SIZE - 1 && grille[row + 1][col] == 0) {
                moveTile((Button) getNodeByRowColumnIndex(row + 1, col));
            } else if (keyCode == KeyCode.LEFT && col > 0 && grille[row][col - 1] == 0) {
                moveTile((Button) getNodeByRowColumnIndex(row, col - 1));
            } else if (keyCode == KeyCode.RIGHT && col < GRID_SIZE - 1 && grille[row][col + 1] == 0) {
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

                   
