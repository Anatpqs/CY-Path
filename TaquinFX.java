/**
 *The game, how to play, rules, shuffle, solvability
 *
 * @author Théo, Julian, Anatole, Andrew, Paul
 * @version 1.0
 */

package application;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.layout.Pane;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;

import javafx.util.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Arrays;
import java.util.PriorityQueue;

public class TaquinFX {
	
    private static final int TILE_SIZE = 100;
    static int nbMove = HomePage.numberMove;
    
    private static int[][] grid;
    static GridPane gridPane = new GridPane();
    public static int IndexMax ;
    public static void setGrid(Level level) {
        grid = level.getGrid();
    }
    public static int score = 0;
    //private static Button button_resolve = new Button("Resolve");
    
    //Définition level
    private static int NbrRow;
    private static int NbrCol;
    private static int[][] grid_level;
    public static boolean test_resolve=false;
    public static String filePath = "/home/cytech/eclipse-workspace/testFX/src/level.txt";  //  /!\ /!\ /!\ A changer en fonction de là où vous placer level.txt
    
    public static void RUNstart() throws IOException {
	List<Level> levels = ManageLevels.loadLevels(filePath);
	IndexMax = levels.size();
        Level level = levels.get(HomePage.Index_level);
        NbrRow = level.getRow();
        NbrCol = level.getColumn();
        grid_level=copyMatrix(level.getGrid());
        score=level.getScore();
        
        // Boutton pour résoudre
        //gridPane.add(button_resolve,0,NbrCol+1);
        //button_resolve.setOnAction(e->resolve());
      
        
        setGrid(level);
        
	    displayGrid(level);
	    
        
	    gridPane.setAlignment(Pos.CENTER);   
       
      

    } 
    
    /**
     * display the grid
 	 *
     * @param level, the level selected from the file
     */
    
    public static void displayGrid(Level level) {
    	setGrid(level);
        
        // Supprime les Ã©lÃ©ments de la grid prÃ©cÃ©dente
        //gridPane.getChildren().clear();
        
        // Adjuste la taille de la fenÃªtre suivant la taille de la grid
        gridPane.setPrefSize(NbrCol * TILE_SIZE, NbrRow * TILE_SIZE);        
        for (int row = 0; row < NbrRow; row++) {
            for (int col = 0; col < NbrCol; col++) {
                if (grid[row][col] != 0 && grid[row][col] != -1) {
                    Button button = new Button(Integer.toString(grid[row][col]));
                    button.setPrefSize(TILE_SIZE, TILE_SIZE);
                    button.setOnAction(e -> moveTile(button));

                    gridPane.add(button, col, row);
                } else if (grid[row][col] == -1) {
                    Pane emptyPane = new Pane();
                    emptyPane.setPrefSize(TILE_SIZE, TILE_SIZE);
                    emptyPane.getStyleClass().add("case-vide");
                    gridPane.add(emptyPane, col, row);
                }
            }
        }
    }
    
    private static void shuffleRandomly() {
    	Random random = new Random();
        int nbAleatoire = random.nextInt(2); // Génère soit 0, soit 1
        if(nbAleatoire == 0) {
        	refreshUI1();
        } else {
        	refreshUI2();
        }
    }
    
    private static void shuffle1() {
    	int[][] gridInit = copyMatrix(grid);
    	boolean shuffleGood = false;
        while (!shuffleGood) {
            shufflegrid1();
            shuffleGood = true; // Supposons que le mélange est bon par défaut
            for (int row = 0; row < gridInit.length; row++) {
                for (int col = 0; col < gridInit[0].length; col++) {
                    if (gridInit[row][col] == grid[row][col] && grid[row][col] != -1) {
                    	shuffleGood = false;
                    }
                }
            }
        }
    }
    
    private static void shuffle2() {
    	int[][] gridInit = copyMatrix(grid);
    	boolean shuffleGood = false;
        while (!shuffleGood) {
            shufflegrid2();
            shuffleGood = true; // Supposons que le mélange est bon par défaut
            for (int row = 0; row < gridInit.length; row++) {
                for (int col = 0; col < gridInit[0].length; col++) {
                    if (gridInit[row][col] == grid[row][col] && grid[row][col] != -1) {
                    	shuffleGood = false;
                    }
                }
            }
        }
    }
    
    private static void shufflegrid1(){
        Random rand = new Random();
        int numSwaps = 20; // Nombre d'échanges de nombres
        
        nbMove = 0; // Réinitialise le compteur de nbMove à zéro
        HomePage.setCoup(nbMove);
        
        for (int i = 0; i < numSwaps; i++) {
            int row1, col1, row2, col2;

            // Rechercher une case non vide à échanger
            do {
                row1 = rand.nextInt(NbrRow);
                col1 = rand.nextInt(NbrCol);
            } while (grid[row1][col1] == -1);

            // Rechercher une autre case non vide différente de la position initiale
            do {
                row2 = rand.nextInt(NbrRow);
                col2 = rand.nextInt(NbrCol);
            } while (grid[row2][col2] == -1 || (row2 == row1 && col2 == col1));

            // Échanger les nombres des deux positions
            int temp = grid[row1][col1];
            grid[row1][col1] = grid[row2][col2];
            grid[row2][col2] = temp;
            
            
        }
    }
    
    private static void shufflegrid2() {
        Random rand = new Random();
        int numMoves = 30; // Nombre de mouvements de mélange
        
        nbMove = 0; // Réinitialise le compteur de nbMove à zéro
        HomePage.setCoup(nbMove);
        
        for (int i = 0; i < numMoves; i++) {
            int emptyRow = -1;
            int emptyCol = -1;

            // Rechercher la case vide
            for (int row = 0; row < NbrRow; row++) {
                for (int col = 0; col < NbrCol; col++) {
                    if (grid[row][col] == 0) {
                        emptyRow = row;
                        emptyCol = col;
                        break;
                    }
                }
            }

            // Choisir aléatoirement une tuile adjacente à la case vide
            ArrayList<Pair<Integer, Integer>> adjacentTiles = new ArrayList<>();
            if (emptyRow > 0 && grid[emptyRow - 1][emptyCol] != -1) {
                adjacentTiles.add(new Pair<>(emptyRow - 1, emptyCol));
            }
            if (emptyRow < NbrRow - 1 && grid[emptyRow + 1][emptyCol] != -1) {
                adjacentTiles.add(new Pair<>(emptyRow + 1, emptyCol));
            }
            if (emptyCol > 0 && grid[emptyRow][emptyCol - 1] != -1) {
                adjacentTiles.add(new Pair<>(emptyRow, emptyCol - 1));
            }
            if (emptyCol < NbrCol - 1 && grid[emptyRow][emptyCol + 1] != -1) {
                adjacentTiles.add(new Pair<>(emptyRow, emptyCol + 1));
            }

            int randomIndex = rand.nextInt(adjacentTiles.size());
            Pair<Integer, Integer> randomTile = adjacentTiles.get(randomIndex);

            int tileRow = randomTile.getKey();
            int tileCol = randomTile.getValue();

            // Échanger la tuile adjacente avec la case vide, en évitant les tuiles avec une valeur de -1
            if (grid[tileRow][tileCol] != -1) {
                int temp = grid[tileRow][tileCol];
                grid[tileRow][tileCol] = 0;
                grid[emptyRow][emptyCol] = temp;
            }
        }
    }

    


    private static void moveTile(Button button) {
	    List<Level> levels = null;
		try {
			levels = ManageLevels.loadLevels(filePath);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        int row = GridPane.getRowIndex(button);
        int col = GridPane.getColumnIndex(button);
        

        // Vérifier si la tuile peut être déplacée
        if ((row > 0 && grid[row - 1][col] == 0) ||
                (row < NbrRow - 1 && grid[row + 1][col] == 0) ||
                (col > 0 && grid[row][col - 1] == 0) ||
                (col < NbrCol - 1 && grid[row][col + 1] == 0)) {
            int emptyRow = -1;
            int emptyCol = -1;
            
            nbMove++; // Incrémente le compteur de nbMove
            HomePage.setCoup(nbMove);

            // Rechercher la case vide
            for (int i = 0; i < NbrRow; i++) {
                for (int j = 0; j < NbrCol; j++) {
                    if (grid[i][j] == 0) {
                        emptyRow = i;
                        emptyCol = j;
                        break;
                    }
                }
            }

            // Échanger la tuile avec la case vide
            int temp = grid[row][col];
            grid[row][col] = 0;
            grid[emptyRow][emptyCol] = temp;

            // Mettre à jour les positions des boutons
            gridPane.getChildren().remove(button);
            gridPane.add(button, emptyCol, emptyRow);

            // Vérifier si le puzzle est résolu
            if (estResolu() && test_resolve==false) {
            	try {
            		if(levels.get(HomePage.Index_level).getScore() > HomePage.getRecord()) {  // Récupération du score, comparaison et actualisation si nouveauScord < ancienScore
            			levels.get(HomePage.Index_level).setScore(HomePage.getRecord());
            			score = nbMove;
    					ManageLevels.saveLevels(levels, filePath);
    					
            		}
            		/*System.out.println("AncienRecord: "+levels.get(HomePage.Index_level).getScore());  // Code qui affiche dans la console pour une meilleur compréhension
            		System.out.println("Score: "+HomePage.getRecord());
					
					System.out.println("Félicitations ! Vous avez résolu le puzzle du taquin !");
					System.out.println("NouveauRecord: " + levels.get(HomePage.Index_level).getScore());*/
					
        			HomePage.setScore();
        			HomePage.victory();
				} catch (IOException e) {
					e.printStackTrace();
				}                
            }
        } else {
            HomePage.displacementfalse();
        }
    }
    
    private static void handleKeyPress(KeyCode keyCode) {
        Button button = getSelectedButton();
        if (button != null) {
            int row = GridPane.getRowIndex(button);
            int col = GridPane.getColumnIndex(button);

            if (keyCode == KeyCode.UP && row > 0 && grid[row - 1][col] == 0) {
                moveTile((Button) getNodeByRowColumnIndex(row - 1, col));
            } else if (keyCode == KeyCode.DOWN && row < NbrRow - 1 && grid[row + 1][col] == 0) {
                moveTile((Button) getNodeByRowColumnIndex(row + 1, col));
            } else if (keyCode == KeyCode.LEFT && col > 0 && grid[row][col - 1] == 0) {
                moveTile((Button) getNodeByRowColumnIndex(row, col - 1));
            } else if (keyCode == KeyCode.RIGHT && col < NbrCol - 1 && grid[row][col + 1] == 0) {
                moveTile((Button) getNodeByRowColumnIndex(row, col + 1));
            }
        }
    }

    private static Node getNodeByRowColumnIndex(final int row, final int column) {
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


    private static Button getSelectedButton() {
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
    

  
    private static boolean estResolu() {
    	int[][] grid_final= grid_level;
    	if(Arrays.deepEquals(grid,grid_final))
    	{
    		return true;
    	}
    	return false;
    }
    //function for copying each element of a matrix
    private static int[][] copyMatrix(int[][] grid) {
        int[][] newGrid = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                newGrid[i][j] = grid[i][j];
            }
        }
        return newGrid;
    }
    
    static void refreshUI1() { 	
    	gridPane.getChildren().clear(); // Efface tous les nœuds de la grid
    	shuffle1();
        for (int row = 0; row < NbrRow; row++) {
            for (int col = 0; col < NbrCol; col++) {
                if (grid[row][col] != 0 && grid[row][col] != -1) {
                    Button button = new Button(Integer.toString(grid[row][col]));
                    button.setPrefSize(TILE_SIZE, TILE_SIZE);
                    button.setOnAction(e -> moveTile(button));

                    gridPane.add(button, col, row);
                } else if (grid[row][col] == -1) {
                    Pane emptyPane = new Pane();
                    emptyPane.setPrefSize(TILE_SIZE, TILE_SIZE);
                    emptyPane.getStyleClass().add("case-vide");
                    gridPane.add(emptyPane, col, row);
                }
            }
        }
    }
    
    static void refreshUI2() { 	
    	gridPane.getChildren().clear(); // Efface tous les nœuds de la grid
    	shuffle2();
        for (int row = 0; row < NbrRow; row++) {
            for (int col = 0; col < NbrCol; col++) {
                if (grid[row][col] != 0 && grid[row][col] != -1) {
                    Button button = new Button(Integer.toString(grid[row][col]));
                    button.setPrefSize(TILE_SIZE, TILE_SIZE);
                    button.setOnAction(e -> moveTile(button));

                    gridPane.add(button, col, row);
                } else if (grid[row][col] == -1) {
                    Pane emptyPane = new Pane();
                    emptyPane.setPrefSize(TILE_SIZE, TILE_SIZE);
                    emptyPane.getStyleClass().add("case-vide");
                    gridPane.add(emptyPane, col, row);
                }
            }
        }
    }
    
    static void refreshUIRandom() { 	
    	gridPane.getChildren().clear(); // Efface tous les nœuds de la grid
    	shuffleRandomly();
    }
    
    
  
    /**
     * Solving using the A* algorithme
     * 
     * <p> the resolve function uses A* algorithm with a priority queue to efficiently explore and find the optimal path from the start state to the goal state while considering the cost and heuristic values.
     * 
     * @return list with the different step to the solution
     */
    static void resolve()
    {
    	
    	int[][] grid_start=copyMatrix(grid);
    	//Grid we want to reach
    	int[][] grid_final=copyMatrix(grid_level);
    	
  
        //Initialization of the various lists/dictionaries required 
        PriorityQueue<State> list_state=new PriorityQueue<>();
	//State = (grid,cost)
        State state1=new State(grid_start,heuristic(grid_start));
    	list_state.add(state1);
    	HashMap<int[][],Integer> cost = new HashMap<>();
    	cost.put(grid_start,heuristic(grid_start));
    	HashMap<int[][],int[][]> parent = new HashMap<>();
    	parent.put(grid_start,null);
    	HashSet<int[][]> visited = new HashSet<>();
    	
    	while(!list_state.isEmpty())
    	{
		//Remove element from queue
    		int[][] grid_temp=list_state.poll().grid;
    		visited.add(grid_temp);
    		
    		if(Arrays.deepEquals(grid_final,grid_temp)) //If we found the solution
    		{
    			System.out.println("RESULTAT TROUVER");
    			//Table of states leading up to the solution
    			ArrayList<int[][]> path = new ArrayList<>();
    	            int[][] current = grid_temp;
    	            while (parent.containsKey(current)) {
    	        
    	                path.add(0, current);
    	                current = parent.get(current);
    	            }
    	           
    	          //Display tiles movement
    	          resolve_print(path);
    	          break;
    		}
    		
    		//List of all possible states starting from the current state
    		ArrayList<int[][]> next_state=new ArrayList<>();
    		next_state=generateNeighbors(grid_temp, visited);
    		
  
            //For each movement : 
            for(int i=0;i<next_state.size();i++)
            {
    
            	int[][] newGrid=copyMatrix(next_state.get(i));
            	
            	if(!visited.contains(newGrid))
            	{
			//Cost calculation with the heuristic
			int newCost=cost.get(grid_temp)+1+heuristic(newGrid);
			State newState =new State(newGrid,newCost);

			 cost.put(newGrid, newCost);
			 parent.put(newGrid, grid_temp);
			 list_state.add(newState);
            	}
            }
    	}
    }
    

    /**
     * generate the different grid possible
     *
     * @param grid_temp, the grid from which we look for the other possible states
     * @param visited, list of all the state already visited
     * @return Une description du résultat retourné par la méthode.
     */
    
    public static ArrayList<int[][]> generateNeighbors(int[][] grid_temp,HashSet<int[][]> visited)
    {
    	ArrayList<int[][]> next_state=new ArrayList<>();
		//Find empty case
		int emptyRow=-1;
		int emptyCol=-1;
        for (int i = 0; i < NbrRow; i++) {
            for (int j = 0; j < NbrCol; j++) {
                if (grid_temp[i][j] == 0) {
                    emptyRow = i;
                    emptyCol = j;
                    break;
                }
            }
        }
       
        //The various possible movements
        if (emptyRow > 0 && grid_temp[emptyRow - 1][emptyCol] != -1)
        {
        	
        	int[][] grid_temp2 = copyMatrix(grid_temp);
        	if(!visited.contains(grid_temp2))
        	{
        	
        	// Exchange the tile with the empty case
            int temp = grid_temp2[emptyRow - 1][emptyCol];
            grid_temp2[emptyRow - 1][emptyCol] = 0;
            grid_temp2[emptyRow][emptyCol] = temp;
            //Ajout à la liste des possibilité
            next_state.add(grid_temp2);
            
        	}

        }
        if (emptyRow < NbrRow - 1 && grid_temp[emptyRow + 1][emptyCol] != -1)
        {
        	int[][] grid_temp3 =copyMatrix(grid_temp);
        	if(!visited.contains(grid_temp3))
        	{
        	// Exchange the tile with the empty case
            int temp = grid_temp3[emptyRow + 1][emptyCol];
            grid_temp3[emptyRow + 1][emptyCol] = 0;
            grid_temp3[emptyRow][emptyCol] = temp;
            //Ajout à la liste des possibilité
            next_state.add(grid_temp3);
        	}
        }
        if (emptyCol > 0 && grid_temp[emptyRow][emptyCol - 1] != -1)
        {
        	int[][] grid_temp4 = copyMatrix(grid_temp);
        	if(!visited.contains(grid_temp4))
        	{
        	// Exchange the tile with the empty case
            int temp = grid_temp4[emptyRow][emptyCol - 1];
            grid_temp4[emptyRow][emptyCol - 1] = 0;
            grid_temp4[emptyRow][emptyCol] = temp;
            //Ajout à la liste des possibilité
            next_state.add(grid_temp4);
        	}
        }
        if (emptyCol < NbrCol - 1 && grid_temp[emptyRow][emptyCol + 1] != -1)
        {
        	int[][] grid_temp5 = copyMatrix(grid_temp);
        	
        	if(!visited.contains(grid_temp5))
        	{
        	// Exchange the tile with the empty case
            int temp = grid_temp5[emptyRow][emptyCol + 1];
            grid_temp5[emptyRow][emptyCol + 1] = 0;
            grid_temp5[emptyRow][emptyCol] = temp;
            //Ajout à la liste des possibilité
            next_state.add(grid_temp5);
        	}
        }
        
        return next_state;
    }
    
    /**
     * Display the solution of the algorithm
     * 
     * @param path, list with the different grid to reach the final grid
     */
    
   private static void resolve_print(ArrayList<int[][]> path)
    {
    	 int delay = 600; // Duration of pause between moves (in milliseconds)
    	 SequentialTransition sequentialTransition = new SequentialTransition();
    
    	 //Button for auto or step-by-step resolution
    	 Button resolve_auto=new Button("Auto");
    	 gridPane.add(resolve_auto,1,NbrCol+1);

    	 Button resolve_step=new Button(">");
    	 gridPane.add(resolve_step,2,NbrCol+1);
    	
    	 ObservableList<Node> children = gridPane.getChildren();
        //Display full auto resolution
    	resolve_auto.setOnAction(a->{ 
    		//Prevent the user from touching the button during resolution
    		gridPane.getChildren().remove(resolve_step);
    		gridPane.getChildren().remove(resolve_auto);

    	        for (Node node : children) {
    	        	node.setDisable(true);
    	        }
    		
    		//Find where the empty cell has moved to state i+1
    		int emptyRow2= -1;
        	int emptyCol2 = -1;
        	
    	for(int i=0;i<path.size()-1;i++)
    	{   
    		
    		  //The grid is traversed to state i+1
    		    for (int row2 = 0; row2 < NbrRow; row2++) {
        		    for (int col2 = 0; col2 < NbrCol; col2++) {
        		    	if (path.get(i+1)[row2][col2] == 0) {
        		            emptyRow2 = row2;
        		            emptyCol2 = col2;
        		            break;
        		        }
        		    }
    		    }
    	        
    	//Move tiles  
    		    final int finalEmptyRow = emptyRow2;
    		    final int finalEmptyCol= emptyCol2;
    		    //Pause between each mouvement
    		    PauseTransition pause = new PauseTransition(Duration.millis(delay));
    		    
    	        pause.setOnFinished(evt -> {
    		    	moveTile((Button) getNodeByRowColumnIndex(finalEmptyRow, finalEmptyCol));
    		    });
    	        sequentialTransition.getChildren().add(pause);
	     }
    	
    	//Disable buttons during resolution
    	sequentialTransition.setOnFinished(event -> {
    	    for (Node node : children) {
    	        node.setDisable(false);
    	    }
    	});
    	
    	sequentialTransition.play();
    	});
    	
    	
    	//Step by step display
    	 int[] iteration = {0};

    	 resolve_step.setOnAction(a -> {
    		 gridPane.getChildren().remove(resolve_auto);
    		 
    		 for (Node node : children) {
 	        	node.setDisable(true);
 	        }
    		 resolve_step.setDisable(false);
    		 
    	     int emptyRow2 = -1;
    	     int emptyCol2 = -1;

    	     if (iteration[0] < path.size() - 1) {
    	    	 
    	         // The grid is traversed to state i+1
    	         for (int row2 = 0; row2 < NbrRow; row2++) {
    	             for (int col2 = 0; col2 < NbrCol; col2++) {
    	                 if (path.get(iteration[0] + 1)[row2][col2] == 0) {
    	                     emptyRow2 = row2;
    	                     emptyCol2 = col2;
    	                     break;
    	                 }
    	             }
    	         }

    	         moveTile((Button) getNodeByRowColumnIndex(emptyRow2, emptyCol2));
    	         
    	         iteration[0]++;
    	     }
    	     else
    	     {
    	    	 for (Node node : children) {
    	 	        	node.setDisable(false);
    	 	        }
    	    	 gridPane.getChildren().remove(resolve_step);
    	     }
    	 });
    
    }

 
   /**
    * Calculating the cost of a state using the Manhattan distance
    *
    * @param grid, matrix of the game
    * @return cost, cost of the state
    */
    private static int heuristic(int[][] grid) {
        int cost = 0;

        for (int row = 0; row < NbrRow; row++) {
            for (int col = 0; col < NbrCol; col++) {
                int value = grid[row][col];

                if (value != 0 && value != -1) {
                    int targetRow = (value - 1) / NbrRow;
                    int targetCol = (value - 1) % NbrCol;

                    int distanceManhattan = Math.abs(row - targetRow) + Math.abs(col - targetCol);
                    int linearConflicts = countLinearConflicts(grid, row, col, targetRow, targetCol);
                    int distanceimproved = distanceManhattan + (2 * linearConflicts);
                    cost += distanceimproved;
                }
            }
        }

        return cost;
    }
	
	//When two tiles that should be in specific rows or columns are placed in reversed positions there is a linear conflict
    private static int countLinearConflicts(int[][] grid, int row, int col, int targetRow, int targetCol) {
        int conflicts = 0;

        if (row == targetRow) {
            for (int c = col + 1; c < NbrCol; c++) {
                int value = grid[row][c];
                if (value != 0 && value != -1 && (value - 1) / NbrRow == row && (value - 1) % NbrCol < targetCol) {
                    conflicts++;
                }
            }
        }

        if (col == targetCol) {
            for (int r = row + 1; r < NbrRow; r++) {
                int value = grid[r][col];
                if (value != 0 && value != -1 && (value - 1) / NbrRow < targetRow && (value - 1) % NbrCol == col) {
                    conflicts++;
                }
            }
        }

        return conflicts;
    }
//-------------------------------------------------------------------------------------------------------------------------------------------------

private static int countPermutations(int[][] array1, int[][] array2) {
        if (array1.length != array2.length) {
            throw new IllegalArgumentException("Arrays must have the same dimensions");
        }

        int[] flattenedArray1 = flattenArray(array1);
        int[] flattenedArray2 = flattenArray(array2);

        if (!Arrays.equals(flattenedArray1, flattenedArray2)) {
            throw new IllegalArgumentException("Arrays must contain the same elements");
        }

        int[] tempArray = Arrays.copyOf(flattenedArray1, flattenedArray1.length);
        int permutationCount = 0;
        int n = flattenedArray1.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < n; j++) {
                if (tempArray[j] < tempArray[minIndex]) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                int temp = tempArray[i];
                tempArray[i] = tempArray[minIndex];
                tempArray[minIndex] = temp;
                permutationCount++;
            }
        }

        return permutationCount;
    }

    private static int[] flattenArray(int[][] array) {
        int rows = array.length;
        int columns = array[0].length;
        int[] flattenedArray = new int[rows * columns];
        int index = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                flattenedArray[index++] = array[i][j];
            }
        }

        return flattenedArray;
    }


private static boolean solvability() {
        int tile_number = 0;
        int permutation = 0;
        int empty_distance = 0;
        int column_empty_ini = 0;
        int row_empty_ini = 0;
        int column_empty_final = 0;
        int row_empty_final = 0;

        // Define and initialize NbrRow, NbrCol, grid, and grid_level variables

        for (int row = 0; row < NbrRow; row++) {
            for (int column = 0; column < NbrCol; column++) {
                // Traverse the array to find the empty tile, the last number tile, and the number of tiles
                if (grid[row][column] == 0) {
                    column_empty_ini = column;
                    row_empty_ini = row;
                }
                if (grid[row][column] != -1) {
                    column_empty_final = column;
                    row_empty_final = row;
                }
                if (grid[row][column] > tile_number) {
                    tile_number = grid[row][column];
                }
            }
        }
        empty_distance = Math.abs(column_empty_ini - column_empty_final) + Math.abs(row_empty_ini - row_empty_final);

        

	if(countPermutation(grid,grid_level)%2==empty_distance%2) {
	//the parity give the solvability
		return true;
	}
	else {
		return false;
	}
}
}
