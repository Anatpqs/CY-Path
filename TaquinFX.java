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

import javafx.scene.control.Label;
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
    public static int coups = HomePage.numberMove;
    public static int score = 0;
    
    private static int[][] grille;
    static GridPane gridPane = new GridPane();

    public static void setGrille(Niveau niveau) {
        grille = niveau.getGrille();
    }
    
    //private static Button button_resolve = new Button("Resolve");
    
    //Définition niveau
    private static int NbrRow;
    private static int NbrCol;
    private static int[][] grid_level;

    
    public static void RUNstart() throws IOException {
    	String cheminFichier = "src/niveau.txt";  //                         /!\ /!\ /!\ A changer en fonction de là où vous placer niveau.txt
   	 	List<Niveau> levels = GestionNiveaux.chargerNiveaux(cheminFichier);
       
        Niveau niveau = levels.get(HomePage.Index_level);
        NbrRow = niveau.getLignes();
        NbrCol = niveau.getColonnes();
        grid_level=copyMatrix(niveau.getGrille());
        score=niveau.getScore();
        
        // Boutton pour résoudre
        //gridPane.add(button_resolve,0,NbrCol+1);
        //button_resolve.setOnAction(e->resolve());
      
        
        setGrille(niveau);
        
	    displayGrid(niveau);
	    
        
	    gridPane.setAlignment(Pos.CENTER);   
       
      

    } 
    
    public static void displayGrid(Niveau niveau) {
    	setGrille(niveau);
        
        int lignes = NbrRow;
        int colonnes = NbrCol;
        
        // Supprime les Ã©lÃ©ments de la grille prÃ©cÃ©dente
        //gridPane.getChildren().clear();
        
        // Adjuste la taille de la fenÃªtre suivant la taille de la grille
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
        //gridPane.add(niveauSuivantButton, 0, NbrRow); // Ajoute le bouton en bas de la grille    
        
    }
    
    public static void displayGrid2(Niveau niveau) {
    	setGrille(niveau);
        
        int lignes = NbrRow;
        int colonnes = NbrCol;
        
        ObservableList<Node> children = gridPane.getChildren();
        List<Node> nodesToRemove = new ArrayList<>();

        // Trouver les tuiles existantes qui doivent Ãªtre supprimÃ©es
        for (Node node : children) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);

            if (rowIndex != null && colIndex != null) {
                if (rowIndex >= lignes || colIndex >= colonnes) {
                    nodesToRemove.add(node);
                }
            }
        }

        // Supprimer les tuiles inutiles
        children.removeAll(nodesToRemove);

        // Mettre Ã  jour la taille du gridPane
        gridPane.setPrefSize(colonnes * TILE_SIZE, lignes * TILE_SIZE);

        // Ajouter les nouvelles tuiles
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

    }
    
    private static void shuffle() {
    	int[][] grilleInit = copyMatrix(grille);
    	boolean shuffleGood = false;
        while (!shuffleGood) {
            shuffleGrille2();
            shuffleGood = true; // Supposons que le mélange est bon par défaut
            for (int row = 0; row < grilleInit.length; row++) {
                for (int col = 0; col < grilleInit[0].length; col++) {
                    if (grilleInit[row][col] == grille[row][col] && grille[row][col] != -1) {
                    	shuffleGood = false;
                    }
                }
            }
        }
    }
    private static void shuffleGrille1(){
        Random rand = new Random();
        int numSwaps = 20; // Nombre d'échanges de nombres
        
        coups = 0; // Réinitialise le compteur de coups à zéro
        HomePage.setCoup(coups);
        
        for (int i = 0; i < numSwaps; i++) {
            int row1, col1, row2, col2;

            // Rechercher une case non vide à échanger
            do {
                row1 = rand.nextInt(NbrRow);
                col1 = rand.nextInt(NbrCol);
            } while (grille[row1][col1] == -1);

            // Rechercher une autre case non vide différente de la position initiale
            do {
                row2 = rand.nextInt(NbrRow);
                col2 = rand.nextInt(NbrCol);
            } while (grille[row2][col2] == -1 || (row2 == row1 && col2 == col1));

            // Échanger les nombres des deux positions
            int temp = grille[row1][col1];
            grille[row1][col1] = grille[row2][col2];
            grille[row2][col2] = temp;
            
            
        }
    }
    
    private static void shuffleGrille2() {
        Random rand = new Random();
        int numMoves = 30; // Nombre de mouvements de mélange
        
        coups = 0; // Réinitialise le compteur de coups à zéro
        HomePage.setCoup(coups);
        
        for (int i = 0; i < numMoves; i++) {
            int emptyRow = -1;
            int emptyCol = -1;

            // Rechercher la case vide
            for (int row = 0; row < NbrRow; row++) {
                for (int col = 0; col < NbrCol; col++) {
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
            if (emptyRow < NbrRow - 1 && grille[emptyRow + 1][emptyCol] != -1) {
                adjacentTiles.add(new Pair<>(emptyRow + 1, emptyCol));
            }
            if (emptyCol > 0 && grille[emptyRow][emptyCol - 1] != -1) {
                adjacentTiles.add(new Pair<>(emptyRow, emptyCol - 1));
            }
            if (emptyCol < NbrCol - 1 && grille[emptyRow][emptyCol + 1] != -1) {
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

    


    private static void moveTile(Button button) {
        int row = GridPane.getRowIndex(button);
        int col = GridPane.getColumnIndex(button);
        

        // Vérifier si la tuile peut être déplacée
        if ((row > 0 && grille[row - 1][col] == 0) ||
                (row < NbrRow - 1 && grille[row + 1][col] == 0) ||
                (col > 0 && grille[row][col - 1] == 0) ||
                (col < NbrCol - 1 && grille[row][col + 1] == 0)) {
            int emptyRow = -1;
            int emptyCol = -1;
            
            coups++; // Incrémente le compteur de coups
            HomePage.setCoup(coups);

            // Rechercher la case vide
            for (int i = 0; i < NbrRow; i++) {
                for (int j = 0; j < NbrCol; j++) {
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
    
    private static void handleKeyPress(KeyCode keyCode) {
        Button button = getSelectedButton();
        if (button != null) {
            int row = GridPane.getRowIndex(button);
            int col = GridPane.getColumnIndex(button);

            if (keyCode == KeyCode.UP && row > 0 && grille[row - 1][col] == 0) {
                moveTile((Button) getNodeByRowColumnIndex(row - 1, col));
            } else if (keyCode == KeyCode.DOWN && row < NbrRow - 1 && grille[row + 1][col] == 0) {
                moveTile((Button) getNodeByRowColumnIndex(row + 1, col));
            } else if (keyCode == KeyCode.LEFT && col > 0 && grille[row][col - 1] == 0) {
                moveTile((Button) getNodeByRowColumnIndex(row, col - 1));
            } else if (keyCode == KeyCode.RIGHT && col < NbrCol - 1 && grille[row][col + 1] == 0) {
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
    	if(Arrays.deepEquals(grille,grid_final))
    	{
    		return true;
    	}
    	return false;
    }
    
    private static int[][] copyMatrix(int[][] grid) {
        int[][] newGrid = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                newGrid[i][j] = grid[i][j];
            }
        }
        return newGrid;
    }
    
    static void refreshUI() {
    	shuffle();
    	gridPane.getChildren().clear(); // Efface tous les nœuds de la grille

        for (int row = 0; row < NbrRow; row++) {
            for (int col = 0; col < NbrCol; col++) {
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
        
        //gridPane.add(button_resolve,0,NbrCol+1);
    }
    
  //Résolution suivant l'algo A*
    static void resolve()
    {
    	
    	int[][] grid_start=copyMatrix(grille);
    	//Grille qu'on veut atteindre
    	int[][] grid_final=copyMatrix(grid_level);
    	
  
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
    		next_state=generateNeighbors(grid_temp, visited);
    		
  
            //Pour chaque mouvement : 
            for(int i=0;i<next_state.size();i++)
            {
    
            	int[][] newGrid=copyMatrix(next_state.get(i));
            	
            	if(visited.contains(newGrid))
            	{
            		continue;
            	}
            	
            	
            	//Calcul du cout avec l'heuristique
            	int newCost=cost.get(grid_temp)+1+heuristic(newGrid);
            	State newState =new State(newGrid,newCost);
            
            	
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
    
    
    public static ArrayList<int[][]> generateNeighbors(int[][] grid_temp,HashSet<int[][]> visited)
    {
    	ArrayList<int[][]> next_state=new ArrayList<>();
		// Rechercher la case vide
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
       
        //Les différents mouvements possibles
        if (emptyRow > 0 && grid_temp[emptyRow - 1][emptyCol] != -1)
        {
        	//copie du tableau
        	int[][] grid_temp2 = copyMatrix(grid_temp);
        	if(!visited.contains(grid_temp2))
        	{
        	
        	// Échanger la tuile avec la case vide
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
        	// Échanger la tuile avec la case vide
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
        	// Échanger la tuile avec la case vide
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
        	// Échanger la tuile avec la case vide
            int temp = grid_temp5[emptyRow][emptyCol + 1];
            grid_temp5[emptyRow][emptyCol + 1] = 0;
            grid_temp5[emptyRow][emptyCol] = temp;
            //Ajout à la liste des possibilité
            next_state.add(grid_temp5);
        	}
        }
        
        return next_state;
    }
    
    
    private static void resolve_print(ArrayList<int[][]> path)
    {
    	 int delay = 600; // Durée de la pause entre chaque déplacement (en millisecondes)
    	 SequentialTransition sequentialTransition = new SequentialTransition();
    
    	 //Boutton pour résolution auto ou étape par étape
    	 Button resolve_auto=new Button("Auto");
    	 gridPane.add(resolve_auto,1,NbrCol+1);

    	 Button resolve_etape=new Button(">");
    	 gridPane.add(resolve_etape,2,NbrCol+1);
    	

        //Affichage de toute la résolution auto
    	resolve_auto.setOnAction(a->{ 
    		//On enlève l'affichage des boutons
    		gridPane.getChildren().remove(resolve_etape);
    		gridPane.getChildren().remove(resolve_auto);
    		
    		//On cherche où la case vide s'est déplacé à l'état i+1
    		int emptyRow2= -1;
        	int emptyCol2 = -1;
        	
    	for(int i=0;i<path.size()-1;i++)
    	{   
    		  //Parcours de la grille à l'état i+1
    		    for (int row2 = 0; row2 < NbrRow; row2++) {
        		    for (int col2 = 0; col2 < NbrCol; col2++) {
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
    	
    	});
    	
    	//Affichage étape par étape
    	 int[] iteration = {0};

    	 resolve_etape.setOnAction(a -> {
    		 gridPane.getChildren().remove(resolve_auto);
    		 
    	     int emptyRow2 = -1;
    	     int emptyCol2 = -1;

    	     if (iteration[0] < path.size() - 1) {
    	         // Parcours de la grille à l'état i+1
    	         for (int row2 = 0; row2 < NbrRow; row2++) {
    	             for (int col2 = 0; col2 < NbrCol; col2++) {
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

    // Calcul du coût d'un état en utilisant la distance de Manhattan
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
                    int distanceAmelioree = distanceManhattan + (2 * linearConflicts);
                    cost += distanceAmelioree;
                }
            }
        }

        return cost;
    }

    private static int countLinearConflicts(int[][] grid, int row, int col, int targetRow, int targetCol) {
        int conflits = 0;

        if (row == targetRow) {
            for (int c = col + 1; c < NbrCol; c++) {
                int value = grid[row][c];
                if (value != 0 && value != -1 && (value - 1) / NbrRow == row && (value - 1) % NbrCol < targetCol) {
                    conflits++;
                }
            }
        }

        if (col == targetCol) {
            for (int r = row + 1; r < NbrRow; r++) {
                int value = grid[r][col];
                if (value != 0 && value != -1 && (value - 1) / NbrRow < targetRow && (value - 1) % NbrCol == col) {
                    conflits++;
                }
            }
        }

        return conflits;
    }
//-------------------------------------------------------------------------------------------------------------------------------------------------


private static Boolean solvability( ) {
	int tile_number = 0;
	int permutation = 0;
	int empty_distance = 0;
	int column_empty_ini = 0;
	int row_empty_ini = 0;
	int column_empty_final = 0;
	int row_empty_final = 0;
	
	for (int row=0;row<NbrRow;row++) {
		for (int column=0;column<NbrCol;column++) {
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
	
	int permutaion_row = 0;
	int permutation_column=0;
	int permutation_column_2=0;
	int permutaion_row_2=0;
	
	while(grille!=grid_level) { //solution is an array of array wich contain the solution of the taquin
		for (int row=0;row<NbrRow;row++) {
			for (int column=0;column<NbrCol;column++) {
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

}
  
  
