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


import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Arrays;
import java.util.PriorityQueue;

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
        shuffleGrille2();
     
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
        
	   // Boutton pour résoudre
           Button button_resolve = new Button("Resolve");
           gridPane.add(button_resolve,0,GRID_SIZE+1);
           button_resolve.setOnAction(e->resolve());
           //Fin bouton résoudre

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
          private Boolean solvability( ) {
    	int tile_number,permutation,empty_distance,column_empty_ini,row_empty_ini,column_empty_final,row_empty_final=0;
    	
    	for (int row=0;row<GRID_SIZE;row++) {
    		for (int column=0;column<GRID_SIZE;column++) {
			//browse the array to find the empty tile the last number tile and the number of tile 
    			if (grille[row][column]==0) {
    				column_empty_ini=column;
    				row_empty_ini=row;
    			}
    			if (grille[row][column]!=-1) {
    				column_empty_final=column;
    				row_empty_final=row;
    			}
    			if (grille[row][column]>=tile_number) {
    				tile_number = grille[row][column];
    			}
    		}
    	}
    	empty_distance=Math.abs(column_empty_ini-column_empty_final)+Math.abs(row_empty_ini-row_empty_final);
    	int permutaion_row,permutation_column,permutation_column_2,permutaion_row_2;
    	while(grille!=soultion) { //solution is an array of array wich contain the solution of the taquin
    		for (int row=0;row<GRID_SIZE;row++) {
    			for (int column=0;column<GRID_SIZE;column++) {
				//count the number of permutation between solution and the initial state
    				if (grille[row][column]==tile_number) {
        				permutaion_row=row;
        				permutation_column=column;
        			}
    				if (grille[row][column]!=-1) {
    					permutaion_row_2=row;
        				permutation_column_2=column;
    				}
    			}
    		}
		//count the distance between initial state and solution 
    		grille[permutaion_row][permutation_column]=grille[permutaion_row_2][permutation_column_2];
    		grille[permutaion_row_2][permutation_column_2]=tile_number;
    		tile_number-=1;
    		permutation+=1;
    	}
    	if(permutation%2==empty_distance%2) {
		//the parity give the solvability
    		return true;
    	}
    	else return false;
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
	
	 //------------------------------------------------------------------------------------------------------------------------------------------
    
    //Résolution suivant l'algo A*
    private void resolve()
    {
    	
    	int[][] grid_start=copyMatrix(grille);
    	//Grille qu'on veut atteindre
    	int[][] grid_final=new int[GRID_SIZE][GRID_SIZE];
    	
    	 int value = 1;
         for (int row = 0; row < GRID_SIZE; row++) {
             for (int col = 0; col < GRID_SIZE; col++) {
                 grid_final[row][col] = value;
                 value++;
             }
         }
         grid_final[GRID_SIZE - 1][GRID_SIZE - 1] = 0; // Case vide représentée par 0
         //grid_final[0][0] = -1; // Case inexistante par -1
    
        //Initialisation des différentes listes/dico necessaire 
        PriorityQueue<State> list_state=new PriorityQueue<>();
        State state1=new State(grid_start,heuristic(grid_start));
    	list_state.add(state1);
    	HashMap<int[][],Integer> cost = new HashMap<>();
    	cost.put(grid_start,heuristic(grid_start));
    	HashMap<int[][],int[][]> parent = new HashMap<>();
    	parent.put(grid_start,null);
    	HashSet<int[][]> visited = new HashSet<>();
    	
    	while(!list_state.isEmpty())
    	
    	{
    		int[][] grid_temp=list_state.poll().grid;
    		visited.add(grid_temp);
    		
    		if(Arrays.deepEquals(grid_final,grid_temp)) //Si on a trouvé la solution
    		{
    			System.out.println("RESULTAT TROUVER");
    			//Tableau des différents états pour résoudre
    			ArrayList<int[][]> path = new ArrayList<>();
    	            int[][] current = grid_temp;
    	            while (parent.containsKey(current)) {
    	                path.add(0, current);
    	                current = parent.get(current);
    	            }
    	          //Afficher le déplacelement des cases
    	          resolve_print(path);
    	          break;
    		}
    		
    		//Liste de tous les états possible à partir de l'état où on est
    		ArrayList<int[][]> next_state=new ArrayList<>();
    		next_state=generateNeighbors(grid_temp);
    		
  
            //Pour chaque mouvement : 
            for(int i=0;i<next_state.size();i++)
            {
    
            	int[][] newGrid=copyMatrix(next_state.get(i));
            	//Calcul du cout avec l'heuristique
            	int newCost=cost.get(grid_temp)+1+heuristic(newGrid);
            	State newState =new State(newGrid,newCost);
            
            	if(visited.contains(newGrid))
            	{
            		continue;
            	}
            	
            	if (cost.containsKey(newGrid)) {
            	    int existingCost = cost.get(newGrid);
            	    if (newCost < existingCost) {
            	        cost.put(newGrid, newCost);
            	        parent.put(newGrid, grid_temp);
            	        System.out.println("test");
            	    }
            	} else {
            	    cost.put(newGrid, newCost);
            	    parent.put(newGrid, grid_temp);
            	    list_state.add(newState);
            	}
            }
    	}
    }
    
    
    public ArrayList<int[][]> generateNeighbors(int[][] grid_temp)
    {
    	ArrayList<int[][]> next_state=new ArrayList<>();
		// Rechercher la case vide
		int emptyRow=-1;
		int emptyCol=-1;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid_temp[i][j] == 0) {
                    emptyRow = i;
                    emptyCol = j;
                    break;
                }
            }
        }
       
        //Les différents mouvements possibles
        if (emptyRow > 0 && grid_temp[emptyRow - 1][emptyCol] != -1)
        {
        	//copie du tableau
        	int[][] grid_temp2 = copyMatrix(grid_temp);
        	
        	// Échanger la tuile avec la case vide
            int temp = grid_temp2[emptyRow - 1][emptyCol];
            grid_temp2[emptyRow - 1][emptyCol] = 0;
            grid_temp2[emptyRow][emptyCol] = temp;
            //Ajout à la liste des possibilité
            next_state.add(grid_temp2);

        }
        if (emptyRow < GRID_SIZE - 1 && grid_temp[emptyRow + 1][emptyCol] != -1)
        {
        	int[][] grid_temp3 =copyMatrix(grid_temp);
        	
        	// Échanger la tuile avec la case vide
            int temp = grid_temp3[emptyRow + 1][emptyCol];
            grid_temp3[emptyRow + 1][emptyCol] = 0;
            grid_temp3[emptyRow][emptyCol] = temp;
            //Ajout à la liste des possibilité
            next_state.add(grid_temp3);
        }
        if (emptyCol > 0 && grid_temp[emptyRow][emptyCol - 1] != -1)
        {
        	int[][] grid_temp4 = copyMatrix(grid_temp);
        	// Échanger la tuile avec la case vide
            int temp = grid_temp4[emptyRow][emptyCol - 1];
            grid_temp4[emptyRow][emptyCol - 1] = 0;
            grid_temp4[emptyRow][emptyCol] = temp;
            //Ajout à la liste des possibilité
            next_state.add(grid_temp4);
        }
        if (emptyCol < GRID_SIZE - 1 && grid_temp[emptyRow][emptyCol + 1] != -1)
        {
        	int[][] grid_temp5 = copyMatrix(grid_temp);
        	// Échanger la tuile avec la case vide
            int temp = grid_temp5[emptyRow][emptyCol + 1];
            grid_temp5[emptyRow][emptyCol + 1] = 0;
            grid_temp5[emptyRow][emptyCol] = temp;
            //Ajout à la liste des possibilité
            next_state.add(grid_temp5);
        }
        
        return next_state;
    }
    
    
    private void resolve_print(ArrayList<int[][]> path)
    {
    	 int delay = 600; // Durée de la pause entre chaque déplacement (en millisecondes)
    	 SequentialTransition sequentialTransition = new SequentialTransition();
    
    	 //Boutton pour résolution auto ou étape par étape
    	 Button resolve_auto=new Button("Auto");
    	 gridPane.add(resolve_auto,1,GRID_SIZE+1);

    	 Button resolve_etape=new Button(">");
    	 gridPane.add(resolve_etape,2,GRID_SIZE+1);
    	

        //Affichage de toute la résolution auto
    	resolve_auto.setOnAction(a->{ 
    		
    		//On cherche où la case vide s'est déplacé à l'état i+1
    		int emptyRow2= -1;
        	int emptyCol2 = -1;
        	
    	for(int i=0;i<path.size()-1;i++)
    	{   
    		  //Parcours de la grille à l'état i+1
    		    for (int row2 = 0; row2 < path.get(i+1).length; row2++) {
        		    for (int col2 = 0; col2 < path.get(i+1).length; col2++) {
        		    	if (path.get(i+1)[row2][col2] == 0) {
        		            emptyRow2 = row2;
        		            emptyCol2 = col2;
        		            break;
        		        }
        		    }
    		    }
    	//Déplacer	  
    		    final int finalEmptyRow = emptyRow2;
    		    final int finalEmptyCol= emptyCol2;
    		    PauseTransition pause = new PauseTransition(Duration.millis(delay));
    		    
    	        pause.setOnFinished(evt -> {
    		    	moveTile((Button) getNodeByRowColumnIndex(finalEmptyRow, finalEmptyCol));
    		    });
    	        sequentialTransition.getChildren().add(pause);
	     }
    	sequentialTransition.play();
    	
    	resolve_auto.setDisable(true);
    	resolve_etape.setDisable(true);
    	});
    	
    	//Affichage étape par étape
    	 int[] iteration = {0};

    	 resolve_etape.setOnAction(a -> {
    	     int emptyRow2 = -1;
    	     int emptyCol2 = -1;

    	     if (iteration[0] < path.size() - 1) {
    	         // Parcours de la grille à l'état i+1
    	         for (int row2 = 0; row2 < path.get(iteration[0] + 1).length; row2++) {
    	             for (int col2 = 0; col2 < path.get(iteration[0] + 1).length; col2++) {
    	                 if (path.get(iteration[0] + 1)[row2][col2] == 0) {
    	                     emptyRow2 = row2;
    	                     emptyCol2 = col2;
    	                     break;
    	                 }
    	             }
    	         }

    	         // Déplacer
    	         moveTile((Button) getNodeByRowColumnIndex(emptyRow2, emptyCol2));

    	         iteration[0]++;
    	     }
    	     else
    	     {
    	    	 resolve_etape.setDisable(true);
    	    	 resolve_auto.setDisable(true);
    	     }
    	 });
    
    }
    //Fin résolution auto
    
    private int[][] copyMatrix(int[][] grid) {
        int[][] newGrid = new int[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                newGrid[i][j] = grid[i][j];
            }
        }
        return newGrid;
    }

    // Calcul du coût d'un état en utilisant la distance de Manhattan
    public static int heuristic(int[][] grid) {
        int cost = 0;

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                int value = grid[row][col];

                if (value != 0) {
                    int targetRow = (value - 1) / GRID_SIZE;
                    int targetCol = (value - 1) % GRID_SIZE;

                    int distance = Math.abs(row - targetRow) + Math.abs(col - targetCol);
                    cost += distance;
                }
            }
        }

        return cost;
    }
  //Fin résolution auto
  
  //---------------------------------------------------------------------------------------------------------------------------------------------
	
}

                   
