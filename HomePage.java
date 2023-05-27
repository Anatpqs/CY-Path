package application;
import java.io.IOException;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.SplitPane;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.animation.PauseTransition;
import javafx.util.Duration;


/**
 * class that will generate the game menu and the game board on the same window
 * initialization of each image, and each button with methods that will change certain fields over time
 * 
 * 
 * @author Théo
 * @version 1.0
 */

public class HomePage extends Application {
    
	
	
	/**
	 * menu initialization
	 */
	private static GridPane homepage = new GridPane();
	static SplitPane splitPane = new SplitPane();
    private static Scene scene1 = new Scene(splitPane,900,700);

    /**
     * Index of the level 
     */
    public static int Index_level = 0;
    private static Text Level = new Text(20,100,"#"+(Index_level+1));
    
	/**
	 * Images and buttons for navigation
	 */
    private  Image Leftpicutre = new Image(getClass().getResource("LevelLeft.png").toExternalForm());
    private  ImageView ViewPictureLeft = new ImageView(Leftpicutre);
    private static Button buttonLeft = new Button();
    
    
    private Image PictureRight = new Image(getClass().getResource("LevelRight.png").toExternalForm());
    private ImageView ViewPictureRight = new ImageView(PictureRight);
    private static Button buttonRight = new Button();
    
    private  Image Rules = new Image(getClass().getResource("Rules.png").toExternalForm());
    private  ImageView ViewPictureRules= new ImageView(Rules);
    private static Button buttonRules = new Button();
    
    private  Image Reset = new Image(getClass().getResource("Reset.png").toExternalForm());
    private  ImageView ViewPictureReset= new ImageView(Reset);
    private static Button buttonReset = new Button();
    
    private  Image Resolve = new Image(getClass().getResource("resolve.png").toExternalForm());
    private  ImageView ViewPictureResolve= new ImageView(Resolve);
    private static Button buttonResolve = new Button();
  
    /**
     * Title for the number of move
     */
    private static Text nbTurns = new Text(20, 100, "NB TURNS");
    public static int numberMove = 0;
    /**
     * Number of move
     */
    private static Text NumberTurns = new Text(20, 100, Integer.toString(numberMove));

    /**
     * Title for the record
     */
    private static Text record = new Text(20, 100, "RECORD");
    /**
     * Record
     */
    private static Text NumberRecord ;

    /**
     * start function which starts the project and creates the main window
     * 
     * @param primaryStage main window
     * @throws exceptions by printing the stack trace
     */
    @Override
    public void start(Stage primaryStage) {
        try {
			// Board game initialization
        	TaquinFX.RUNstart();

            // Homepage
            homepage.setPadding(new Insets(10));
            homepage.setHgap(10);
            homepage.setVgap(10);

            
			// Styling
            scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            homepage.getStyleClass().add("my-gridpane");
            homepage.setStyle("-fx-background-color: black;");
            
            
            // Level's number
            Level.setFont(new Font(75));
            Level.setFill(Color.WHITE);  
            GridPane.setHalignment(Level, HPos.CENTER);
            GridPane.setMargin(Level, new Insets(0, 10, 0, 0));
            homepage.add(Level, 1, 0);

            
            // Previous level button
            buttonLeft.setGraphic(ViewPictureLeft);
            buttonLeft.setStyle("-fx-background-color: black");
            GridPane.setHalignment(Level, HPos.LEFT);
            GridPane.setMargin(buttonLeft, new Insets(0, 0, 0, 10));
            /**
             * Sets an event handler for the "Left" button.
             *
             * <p>This event handler is triggered when the "Left" button is clicked. It decrements the level index (until it reaches 0),
             * refreshes the page by updating the level, score, and game board.</p>
             *
             * <p>Note: The "Left" button is assumed to be associated with the variable "buttonLeft".</p>
             *
             * @param event the event representing the button click
             */
            buttonLeft.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                	// Decrement of the level (until 0) and refresh of the page
                	if(Index_level > 0) {
                		Index_level--; 
                		setLevel();
                		setScore();
                		setBoardGame();
                	}
                	          }
            });
            homepage.add(buttonLeft, 0, 0);


            // Next level button
            buttonRight.setGraphic(ViewPictureRight);
            buttonRight.setStyle("-fx-background-color: black");           
            GridPane.setHalignment(Level, HPos.RIGHT);
            GridPane.setMargin(buttonRight, new Insets(0, 0, 0, 10));
            
            /**
             * Sets an event handler for the "Right" button.
             *
             * <p>This event handler is triggered when the "Right" button is clicked. It increments the level index (up to the maximum level
             * minus one) and refreshes the page by updating the level, score, and game board.</p>
             *
             * <p>Note: The "Right" button is assumed to be associated with the variable "buttonRight".</p>
             *
             * @param event the event representing the button click
             */
            buttonRight.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // Level increment (up to level max-1) then page refresh
                    if (TaquinFX.score != 0) {
                        if (Index_level < TaquinFX.IndexMax - 1) {
                            Index_level++;
                            setLevel();
                            setScore();
                            setBoardGame();
                        }
                    }
                }
            });

            homepage.add(buttonRight, 2, 0);

            
            // Rules
            buttonRules.setGraphic(ViewPictureRules);
            buttonRules.setStyle("-fx-background-color: black"); 
            buttonRules.setOnAction(event -> {
                Label label = new Label("Le taquin\n"
                        + "Le taquin est un jeu qui a été inventé vers 1870 aux États-Unis.\n"
                        + "Règles :\n"
                        + "		- Il y a un emplacement vide, ce qui permet de déplacer un bloc en le faisant \n		glisser.\n"
                        + "		- Il vous suffit de cliquer sur un bloc pour qu'il prenne la place de \n		l'emplacement vide.\n"
                        + "		- Les blocs sont tout d'abord mélangés, et la partie est gagnée quand \n		la disposition initiale est atteinte.\n"
                        + "		- Il y a plusieurs niveaux, pour y accéder, vous devez réussir le niveau actuel!\n"
                        + "		- Au début, le niveau est dans sa position finale, pour le commencer, \n		cliquez sur le bouton en bas à droite et sélectionnez votre type de mélange.\n"
                        + "		- Puis résolvez l'énigme!\n"
                        + "		- Si vous êtes bloqué, vous pouvez toujours cliquer sur l'ampoule \n		qui résoudra étape par étape le jeu du taquin. Il y a la résolution automatique,\n		 ou case par case.\n"
                        + "		- Attention !!! L'ampoule ne vous validera pas le niveau.\n"
                        + "		- Si le niveau est fini, le plateau de jeu deviendra vert. \n		Pour recommencer le niveau, vous devrez réappuyer sur le bouton mélanger.\n"
                        + "		- Les cases noires sont des cases interdites, vous ne pourrez pas aller dessus.\n"
                        + "		- Vous pouvez jouer avec votre souris ou avec le clavier, \n		il suffit simplement de sélectionner la case avec les flèches directionnelles\n		 puis appuyer sur espace ou entrée.\n\n\n"
                        + "Il ne reste plus qu'à vous amuser !!! Bon jeu.\n\n\n\n"
                        + "GRANDJEAN THEO ; BOCQ ANDREW ; GOSSELIN JULIAN ; PAQUES ANATOLE ; JUMEL PAUL");
                label.setTextFill(Color.WHITE);
                VBox root = new VBox(label);
                root.setSpacing(10);
                Stage rule = new Stage();
                Scene rulescene = new Scene(root, 600, 500);
                root.setStyle("-fx-background-color: black;");
                rule.setScene(rulescene);
                rule.show();
                rule.setTitle("Rule");
                rule.setResizable(false);
            });

            homepage.add(buttonRules, 0,15);
            
            
            //Random's type
            buttonReset.setGraphic(ViewPictureReset);
            buttonReset.setStyle("-fx-background-color: black");  
            /**
             * Sets an event handler for the "Reset" button.
             *
             * <p>This event handler is triggered when the "Reset" button is clicked. It creates and configures three additional buttons:
             * "Melange 1", "Melange 2", and "Random". Each button has its own event handler that performs a specific action when clicked.</p>
             *
             * <p>The "Melange 1" button triggers the {@link TaquinFX#refreshUI1()} method, which refreshes the user interface by performing
             * a specific shuffling operation.</p>
             *
             * <p>The "Melange 2" button triggers the {@link TaquinFX#refreshUI2()} method, which refreshes the user interface by performing
             * another shuffling operation.</p>
             *
             * <p>The "Random" button triggers the {@link TaquinFX#refreshUIRandom()} method, which refreshes the user interface by performing
             * a random shuffling operation.</p>
             *
             *
             * @param event the event representing the button click
             */
            buttonReset.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                	Button buttonrefreshUI1 = new Button("Melange 1");
                	buttonrefreshUI1.setOnAction(new EventHandler<ActionEvent>() {
                		@Override
                		public void handle(ActionEvent event) {
        					TaquinFX.test_resolve=false;
                			TaquinFX.refreshUI1();
                		}
                	});
                	
                	Button buttonrefreshUI2 = new Button("Melange 2");
                	buttonrefreshUI2.setOnAction(new EventHandler<ActionEvent>() {
                		@Override
                		public void handle(ActionEvent event) {
        					TaquinFX.test_resolve=false;
                			TaquinFX.refreshUI2();
                		}
                	});
                	
                	Button buttonrandom = new Button("Random");
                	buttonrandom.setOnAction(new EventHandler<ActionEvent>() {
                		@Override
                		public void handle(ActionEvent event) {
        					TaquinFX.test_resolve=false;
                			TaquinFX.refreshUIRandom();
                		}
                	});
                	
                	TaquinFX.gridPane.add(buttonrefreshUI1, 0, 10);
                	TaquinFX.gridPane.add(buttonrefreshUI2, 1, 10);
                	TaquinFX.gridPane.add(buttonrandom, 2, 10);                	
                	}
            });
            homepage.add(buttonReset, 2,15);
            
            // Resolve button
            buttonResolve.setGraphic(ViewPictureResolve);
            buttonResolve.setStyle("-fx-background-color: black"); 
            /**
             * Sets an event handler for the "Resolve" button.
             *
             * <p>This event handler is triggered when the "Resolve" button is clicked. It performs the following actions:</p>
             *
             * <ol>
             *   <li>Sets the boolean flag {@link TaquinFX#test_resolve} to <code>true</code>.</li>
             *   <li>Invokes the {@link TaquinFX#resolve()} method, which executes the logic to resolve the puzzle.</li>
             * </ol>
             *
             * <p>Note: The "Resolve" button is assumed to be associated with the variable "buttonResolve".</p>
             *
             * @param event the event representing the button click
             */
            buttonResolve.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                	TaquinFX.test_resolve=true;
                	TaquinFX.resolve();                }
            });
            homepage.add(buttonResolve, 1,15);
            

            // Number of currently played moves
            nbTurns.setFont(new Font(50));
            nbTurns.setFill(Color.WHITE);
            GridPane.setHalignment(nbTurns, HPos.CENTER);
            GridPane.setMargin(nbTurns, new Insets(0, 10, 0, 0));
            GridPane.setColumnSpan(nbTurns, 3); // Specifies that nbTurns occupies 3 columns
            homepage.add(nbTurns, 0, 5);
            
        	NumberTurns.setFont(new Font(75));
            NumberTurns.setFill(Color.WHITE);
            GridPane.setHalignment(NumberTurns, HPos.CENTER);
            GridPane.setMargin(NumberTurns, new Insets(0, 10, 0, 0));
            homepage.add(NumberTurns, 1, 6);
            
            
            //Record
            record.setFont(new Font(50));
            record.setFill(Color.WHITE);
            GridPane.setHalignment(record, HPos.CENTER);
            GridPane.setMargin(record, new Insets(0, 10, 0, 0));
            GridPane.setColumnSpan(record, 3); // Specifies that nbTurns occupies 3 columns
            homepage.add(record, 0, 9);
            
            NumberRecord = new Text(20, 100, Integer.toString(TaquinFX.score));
            NumberRecord.setFont(new Font(75));
            NumberRecord.setFill(Color.WHITE);
            GridPane.setHalignment(NumberRecord, HPos.CENTER);
            GridPane.setMargin(NumberRecord, new Insets(0, 10, 0, 0));
            homepage.add(NumberRecord, 1, 10);
            
                 
            // Added menu and game board to have both on the same window
            splitPane.getItems().addAll(homepage, TaquinFX.gridPane);
          
            // Window loading
            primaryStage.setScene(scene1);
            primaryStage.show();
            primaryStage.setTitle("Jeu du Taquin");
            primaryStage.setResizable(false);



            

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Changes the game board when the level changes.
     * Removes the previous game board and creates a new one.
     */
    public static void setBoardGame() {
        splitPane.getItems().remove(TaquinFX.gridPane); // remove previous game board
        TaquinFX.coups = 0;
        setCoup(TaquinFX.coups); // set played moves to 0
        TaquinFX.gridPane.getChildren().clear();
        try {
            TaquinFX.RUNstart(); // create the new game board
        } catch (IOException e) {
            e.printStackTrace();
        }
        splitPane.getItems().add(TaquinFX.gridPane); // add the new game board
    }

    /**
     * Updates the display of the number of moves whenever a box is moved.
     * Removes the previous number and sets the new text number.
     */
    public static void setCoup(int coup) {
        homepage.getChildren().remove(NumberTurns); // remove previous number
        NumberTurns.setText(Integer.toString(coup)); // create the new text number
        NumberTurns.setFont(new Font(75));
        NumberTurns.setFill(Color.WHITE);
        GridPane.setHalignment(NumberTurns, HPos.CENTER);
        GridPane.setMargin(NumberTurns, new Insets(0, 10, 0, 0));
        homepage.add(NumberTurns, 1, 6); // add the new text number
    }

    /**
     * Updates the display of the level index.
     * Removes the previous number and sets the new text number.
     */
    public static void setLevel() {
        homepage.getChildren().remove(Level); // remove previous number
        Level.setText("#" + (Index_level + 1)); // create the new text number
        Level.setFont(new Font(75));
        Level.setFill(Color.WHITE);
        GridPane.setHalignment(Level, HPos.CENTER);
        GridPane.setMargin(Level, new Insets(0, 10, 0, 0));
        homepage.add(Level, 1, 0); // add the new text number
    }

    /**
     * Updates the display of the score whenever the level changes.
     * Removes the previous number and sets the new text number.
     * 
     * @throws IOException if an error occurs while reading the file
     */
    public static void setScore() {
        homepage.getChildren().remove(NumberRecord); // remove previous number
        try {
            TaquinFX.RUNstart(); // the record is loaded when creating the game board
        } catch (IOException e) {
            e.printStackTrace();
        }
        NumberRecord.setText(Integer.toString(TaquinFX.score)); // create the new text number
        NumberRecord.setFont(new Font(75));
        NumberRecord.setFill(Color.WHITE);
        GridPane.setHalignment(NumberRecord, HPos.CENTER);
        GridPane.setMargin(NumberRecord, new Insets(0, 10, 0, 0));
        homepage.add(NumberRecord, 1, 10); // add the new text number
    }

    /**
     * Returns the number of moves currently played to become the new record.
     * @return The number of moves played.
     */
    public static int getRecord() {
        return Integer.parseInt(NumberTurns.getText());
    }

    /**
     * Changes the appearance of the game board when the player wins the level.
     * Clears the game board and creates a new one with buttons colored green.
     * 
     * @throws IOException if an error occurs while reading the file
     */
    public static void victory() {
        TaquinFX.gridPane.getChildren().clear(); // remove previous number
        try {
            TaquinFX.RUNstart(); // create new game board
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < TaquinFX.gridPane.getChildren().size(); i++) {
            if (TaquinFX.gridPane.getChildren().get(i) instanceof Button) {
                Button button = (Button) TaquinFX.gridPane.getChildren().get(i);
                button.setStyle("-fx-background-color: chartreuse; -fx-border-color: black;"); // give each button the color green
            }
        }
    }

	
	/**
	 * Displays a message in red when the player makes an invalid move.
	 * The message is displayed for 1 second before being deleted.
	 */
	public static void displacementfalse() {
		Label label = new Label("Deplacement Invalide !"); //creation message and set color red
		label.setTextFill(Color.RED);
		label.setFont(new Font(20));
		TaquinFX.gridPane.add(label, 0, 15); // add the message
		TaquinFX.gridPane.setColumnSpan(label, 3); // Specifies that nbTurns occupies 3 columns

        PauseTransition pause = new PauseTransition(Duration.seconds(1)); //
        pause.setOnFinished(event -> TaquinFX.gridPane.getChildren().remove(label)); // after 1 seconde delete the message
        pause.play();
    }
	
}
