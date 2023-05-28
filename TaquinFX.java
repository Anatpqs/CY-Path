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
    private static Button button_resolve = new Button("Resolve");
    
    //Define level
    private static int NbrRow;
    private static int NbrCol;
    private static int[][] grid_level;
    public static boolean test_resolve=false;
    public static String filePath = "/home/cytech/eclipse-workspace/testFX/src/level.txt";  //  /!\ /!\ /!\ A changer en fonction de là où vous placer level.txt
    
    public static void RUNstart() throws IOException {
    	List<Level> levels = ManageLevels.loadLevels(filePath);
		IndexMax = levels.size();
		test_resolve=false;
        Level level = levels.get(HomePage.Index_level);
        NbrRow = level.getRow();
        NbrCol = level.getColumn();
        grid_level=copyMatrix(level.getGrid());
        score=level.getScore();
        
        // Boutton pour résoudre
        //gridPane.add(button_resolve,0,NbrCol+1);
      
        
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
    
    /**
     * This method shuffle randomly the grid
     * <p> We use shuffle 1 or 2, depending on the generated number
     */
    private static void shuffleRandomly() {
    	Random random = new Random();
        int nbRandom = random.nextInt(2);
        
        if(nbRandom == 0) {		// We use shuffle 1 or 2, depending on the generated number							
        	refreshUI1();
        } else {
        	refreshUI2();
        }
    }
    
    /**
     * This method shuffle so that no tile is on the same starting square with shuffleGrid1
     * <p> We create a copy of the original grid, we run a while to find if any tile in the shuffled grid is in the same position as the initial grid
     */
    private static void shuffle1() {
    	int[][] gridInit = copyMatrix(grid);	// Create a copy of the original grid
    	boolean shuffleGood = false;
        while (!shuffleGood) {
            shuffleGrid1();
            shuffleGood = true;		// Assume that the mix is good by default
            
            // Check if any tile in the shuffled grid is in the same position as the initial grid
            for (int row = 0; row < gridInit.length; row++) {
                for (int col = 0; col < gridInit[0].length; col++) {
                    if (gridInit[row][col] == grid[row][col] && grid[row][col] != -1) {	// If a tile is in the same position, start again
                    	shuffleGood = false;
                    }
                }
            }
        }
    }
    
    /**
     * This method shuffle so that no tile is on the same starting square with shuffleGrid1
     * <p> Same for shuffle1
     */
    private static void shuffle2() {
    	int[][] gridInit = copyMatrix(grid);	
    	boolean shuffleGood = false;
        while (!shuffleGood) {
            shuffleGrid2();
            shuffleGood = true; 
            for (int row = 0; row < gridInit.length; row++) {
                for (int col = 0; col < gridInit[0].length; col++) {
                    if (gridInit[row][col] == grid[row][col] && grid[row][col] != -1) {
                    	shuffleGood = false;
                    }
                }
            }
        }
    }
    
    /**
     * This method shuffles the grid numbers randomly
     */
    private static void shuffleGrid1(){
        Random random = new Random();
        int nbSwap = 10;	// Number of number exchanges
        
        nbMove = 0;		// Resets nbMove counter to zero
        HomePage.setNumberMove(nbMove);
        
        for (int i = 0; i < nbSwap; i++) {
            int row1, col1, row2, col2;

            // Search for a non-empty box to swap
            do {
                row1 = random.nextInt(NbrRow);
                col1 = random.nextInt(NbrCol);
            } while (grid[row1][col1] == -1);

            // Search for another non-empty square different from the initial position
            do {
                row2 = random.nextInt(NbrRow);
                col2 = random.nextInt(NbrCol);
            } while (grid[row2][col2] == -1 || (row2 == row1 && col2 == col1));

            // Exchange the numbers of the two positions
            int temp = grid[row1][col1];
            grid[row1][col1] = grid[row2][col2];
            grid[row2][col2] = temp;
        }
    }
    
    /**
     * This method shuffles in such a way as to move the tiles a certain number of times
     */
    private static void shuffleGrid2() {
        Random random = new Random();
        int nbMoves = 10; 	// Number of mixing movements
        
        nbMove = 0; 	// Resets nbMove counter to zero
        HomePage.setNumberMove(nbMove);
        
        for (int i = 0; i < nbMoves; i++) {
            int emptyRow = -1;
            int emptyCol = -1;

            // Find the empty box
            for (int row = 0; row < NbrRow; row++) {
                for (int col = 0; col < NbrCol; col++) {
                    if (grid[row][col] == 0) {
                        emptyRow = row;
                        emptyCol = col;
                        break;
                    }
                }
            }

            // Randomly select a tile adjacent to the empty square
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

            int randomIndex = random.nextInt(adjacentTiles.size());
            Pair<Integer, Integer> randomTile = adjacentTiles.get(randomIndex);

            int tileRow = randomTile.getKey();
            int tileCol = randomTile.getValue();

            // Swap the adjacent tile with the empty square, avoiding tiles with a value of -1.
            if (grid[tileRow][tileCol] != -1) {
                int temp = grid[tileRow][tileCol];
                grid[tileRow][tileCol] = 0;
                grid[emptyRow][emptyCol] = temp;
            }
        }
    }

    

    /**
     * This method moves a tile
     * @param button Tile to be moved
     */
    private static void moveTile(Button button) {
        List<Level> levels = null;
        try {
            levels = ManageLevels.loadLevels(filePath); // Load the levels from a file
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        int row = GridPane.getRowIndex(button); // Get the row index of the button in the gridPane
        int col = GridPane.getColumnIndex(button); // Get the column index of the button in the gridPane

        // Check if the tile can be moved
        if ((row > 0 && grid[row - 1][col] == 0) ||
                (row < NbrRow - 1 && grid[row + 1][col] == 0) ||
                (col > 0 && grid[row][col - 1] == 0) ||
                (col < NbrCol - 1 && grid[row][col + 1] == 0)) {
            int emptyRow = -1;
            int emptyCol = -1;

            nbMove++; // Increment the move counter
            HomePage.setNumberMove(nbMove); // Update the UI with the new move count

            // Search for the empty tile
            for (int i = 0; i < NbrRow; i++) {
                for (int j = 0; j < NbrCol; j++) {
                    if (grid[i][j] == 0) {
                        emptyRow = i;
                        emptyCol = j;
                        break;
                    }
                }
            }

            // Swap the tile with the empty tile
            int temp = grid[row][col];
            grid[row][col] = 0;
            grid[emptyRow][emptyCol] = temp;

            // Update the positions of the buttons in the gridPane
            gridPane.getChildren().remove(button);
            gridPane.add(button, emptyCol, emptyRow);

            // Check if the puzzle is solved
            if (isResolved() && test_resolve == false) {
                try {
                    if (levels.get(HomePage.Index_level).getScore() > HomePage.getRecord() || levels.get(HomePage.Index_level).getScore() == 0) {
                        // Update the score if it is higher than the previous record or if no record exists
                        levels.get(HomePage.Index_level).setScore(HomePage.getRecord());
                        score = nbMove;
                        ManageLevels.saveLevels(levels, filePath); // Save the updated levels to the file

                    }    				
                    HomePage.setScore(); // Update the UI with the new score
                    HomePage.victory(); // Trigger the victory state in the UI
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            HomePage.displacementfalse(); // Notify the UI that the tile cannot be moved
        }
    }
    
    /**
     * This method manages the keyboard 
     * @param keycode press button
     */
    private static void handleKeyPress(KeyCode keyCode) {
        Button button = getSelectedButton(); // Get the currently selected button
        if (button != null) { // If a Button is selected
            int row = GridPane.getRowIndex(button);
            int col = GridPane.getColumnIndex(button);

            // If the UP key is pressed, the row is greater than 0, and the target grid cell is empty
            if (keyCode == KeyCode.UP && row > 0 && grid[row - 1][col] == 0) {
                moveTile((Button) getNodeByRowColumnIndex(row - 1, col)); // Move the tile to the target position
                
            // If the DOWN key is pressed  
            } else if (keyCode == KeyCode.DOWN && row < NbrRow - 1 && grid[row + 1][col] == 0) {                
                moveTile((Button) getNodeByRowColumnIndex(row + 1, col)); 
                
            // If the LEFT key is pressed
            } else if (keyCode == KeyCode.LEFT && col > 0 && grid[row][col - 1] == 0) {                // If the LEFT key is pressed
                moveTile((Button) getNodeByRowColumnIndex(row, col - 1));
                
            // If the RIGHT key is pressed
            } else if (keyCode == KeyCode.RIGHT && col < NbrCol - 1 && grid[row][col + 1] == 0) {                
                moveTile((Button) getNodeByRowColumnIndex(row, col + 1));
            }
        }
    }
    
    /**
     * This method is used for the handleKeyPress method, which returns the node where the tile is to be moved
     * @param row Retrieves the line of the node to be moved
     * @param column Retrieves the column of the node to be moved
     * @return Return the node found at the specified row and column indices, or null if not found
     */
    private static Node getNodeByRowColumnIndex(final int row, final int column) {
        Node result = null; // Initialize the result as null
        ObservableList<Node> children = gridPane.getChildren(); // Get the list of children nodes in the GridPane

        for (Node node : children) { // Iterate over each node in the children list
        	
        	// If the row and column indices of the current node match the given row and column indices
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {                
                result = node; // Assign the current node to the result variable
                break; // Exit the loop since the desired node has been found
            }
        }

        return result; // Return the node found at the specified row and column indices, or null if not found
    }

    /**
     * This method brings up the currently selected button
     * @return Return the selected button or null
     */
    private static Button getSelectedButton() {
        for (Node node : gridPane.getChildren()) { // Iterate over each node in the GridPane's children
            if (node instanceof Button) { // Check if the node is an instance of Button
                Button button = (Button) node; // Perform a type conversion to get a Button object
                if (button.isFocused()) { // Check if the button is focused (selected)
                    return button; // Return the selected button
                }
            }
        }
        return null; // If no button is selected, return null
    }
    

    /**
     * This method lets you know if the game has been solved.
     * @return True if resolved, false otherwise
     */
    private static boolean isResolved() {
        int[][] grid_final = grid_level; // Assign the final grid state to a new variable grid_final

        // Check if the current grid is equal to the final grid using the deepEquals method of the Arrays class
        if (Arrays.deepEquals(grid, grid_final)) {
            return true; // Return true if the grids are equal
        }
        return false; // Return false if the grids are not equal
    }
    
    /**
     * Method for copying each element of a matrix
     */
    private static int[][] copyMatrix(int[][] grid) {
        int[][] newGrid = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                newGrid[i][j] = grid[i][j];
            }
        }
        return newGrid;
    }
    
    /**
     * This method makes the shuffle1
     */
    static void refreshUI1() {
        gridPane.getChildren().clear(); // Clear all nodes from the current grid
        do {
        shuffle1(); // Shuffle the tiles in the grid with shuffle 1
        } while (isSolvable(grid) == false); //This loop produces a solvent shuffle
        // Iterate over each row and column in the grid
        for (int row = 0; row < NbrRow; row++) {
            for (int col = 0; col < NbrCol; col++) {
            	
            	// If the value in the grid is not 0 and -1 (non-empty)
                if (grid[row][col] != 0 && grid[row][col] != -1) {               
                    Button button = new Button(Integer.toString(grid[row][col]));
                    button.setPrefSize(TILE_SIZE, TILE_SIZE);
                    button.setOnAction(e -> moveTile(button));
                    gridPane.add(button, col, row); // Add the button at the corresponding position in the gridPane
                    
                // If the value in the grid is -1 (empty tile)
                } else if (grid[row][col] == -1) {                    
                    Pane emptyPane = new Pane();
                    emptyPane.setPrefSize(TILE_SIZE, TILE_SIZE);
                    emptyPane.getStyleClass().add("case-vide");
                    gridPane.add(emptyPane, col, row); // Add the empty tile at the corresponding position in the gridPane
                }
            }
        }
    }

    
    /**
     * This method makes the shuffle2
     */
    static void refreshUI2() { 	
    	gridPane.getChildren().clear(); // 
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
    
   /**
    *  This method makes the shuffle randomly
    */
    static void refreshUIRandom() {
        gridPane.getChildren().clear(); 
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

/**
 * Determines if a given grid is solvable.
 *
 * @param grid The 2D array representing the grid.
 * @return {@code true} if the grid is solvable, {@code false} otherwise.
 */
public static boolean isSolvable(int[][] grid) {
    int gridSize = NbrRow * NbrCol;
    int[] flattenedGrid = flattenGrid(grid);
    int inversions = countInversions(flattenedGrid);
    int blankRow = findBlankRow(grid);

    if (gridSize % 2 == 1) {
        // For an odd-sized grid, the game is solvable if the number of inversions is even.
        return inversions % 2 == 0;
    } else {
        // For an even-sized grid, the game is solvable if the following conditions are met:
        // - the number of inversions is even
        // - the row of the blank tile (counted from the bottom) is odd
        return (inversions % 2 == 0) == (blankRow % 2 == 1);
    }
}

/**
 * Flattens the 2D grid into a 1D array.
 *
 * @param grid The 2D array representing the grid.
 * @return The flattened grid as a 1D array.
 */
private static int[] flattenGrid(int[][] grid) {
    int gridSize = NbrRow * NbrCol;
    int[] flattenedGrid = new int[gridSize];
    int index = 0;
    for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[0].length; j++) {
            flattenedGrid[index] = grid[i][j];
            index++;
        }
    }
    return flattenedGrid;
}

/**
 * Counts the number of inversions in the flattened grid.
 *
 * @param flattenedGrid The flattened grid as a 1D array.
 * @return The number of inversions in the grid.
 */
private static int countInversions(int[] flattenedGrid) {
    int inversions = 0;
    for (int i = 0; i < flattenedGrid.length - 1; i++) {
        for (int j = i + 1; j < flattenedGrid.length; j++) {
            if (flattenedGrid[i] > flattenedGrid[j] && flattenedGrid[i] != 0 && flattenedGrid[j] != 0) {
                inversions++;
            }
        }
    }
    return inversions;
}

/**
 * Finds the row of the blank tile in the grid.
 *
 * @param grid The 2D array representing the grid.
 * @return The row of the blank tile (counted from the bottom). Returns -1 if the blank tile is not found.
 */
private static int findBlankRow(int[][] grid) {
    for (int i = grid.length - 1; i >= 0; i--) {
        for (int j = 0; j < grid[0].length; j++) {
            if (grid[i][j] == 0) {
                return grid.length - i;
            }
        }
    }
    return -1; // Returns -1 if the blank tile is not found (this case should not occur)
}


} 
