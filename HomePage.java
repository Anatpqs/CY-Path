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

public class HomePage extends Application {
    
	//menu initialization
	private static GridPane homepage = new GridPane();
	static SplitPane splitPane = new SplitPane();
    private static Scene scene1 = new Scene(splitPane,900,700);

    public static int Index_level = 0;
    private static Text Level = new Text(20,100,"#"+(Index_level+1));
    
    private  Image imageLeft = new Image(getClass().getResource("LevelLeft.png").toExternalForm());
    private  ImageView imageViewLeft = new ImageView(imageLeft);
    private static Button buttonLeft = new Button();
    
    private  Image Rules = new Image(getClass().getResource("Rules.png").toExternalForm());
    private  ImageView imageViewRules= new ImageView(Rules);
    private static Button buttonRules = new Button();
    
    private  Image Reset = new Image(getClass().getResource("Reset.png").toExternalForm());
    private  ImageView imageViewReset= new ImageView(Reset);
    private static Button buttonReset = new Button();
    
    private  Image Resolve = new Image(getClass().getResource("resolve.png").toExternalForm());
    private  ImageView imageViewResolve= new ImageView(Resolve);
    private static Button buttonResolve = new Button();
  
    private static Text nbTurns = new Text(20, 100, "NB TURNS");
    public static int numberMove = 0;
    private static Text NumberTurns = new Text(20, 100, Integer.toString(numberMove));

    
    private static Text record = new Text(20, 100, "RECORD");
    private static Text NumberRecord ;


    @Override
    public void start(Stage primaryStage) {
        try {
        	//boardgame initialization
        	TaquinFX.RUNstart();

            //homepage
            homepage.setPadding(new Insets(10));
            homepage.setHgap(10);
            homepage.setVgap(10);

            
            //back in black
            scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            homepage.getStyleClass().add("my-gridpane");
            homepage.setStyle("-fx-background-color: black;");
            
            
            //level's number
            Level.setFont(new Font(75));
            Level.setFill(Color.WHITE);  
            GridPane.setHalignment(Level, HPos.CENTER);
            GridPane.setMargin(Level, new Insets(0, 10, 0, 0));
            homepage.add(Level, 1, 0);

            
            //previous level
            buttonLeft.setGraphic(imageViewLeft);
            buttonLeft.setStyle("-fx-background-color: black");
            GridPane.setHalignment(Level, HPos.LEFT);
            GridPane.setMargin(buttonLeft, new Insets(0, 0, 0, 10));
            buttonLeft.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                	if(Index_level > 0) {
                		Index_level--;
                		setLevel();
                		setScore();
                		setBoardGame();
                	}
                	          }
            });
            homepage.add(buttonLeft, 0, 0);


            //next level
            Image imageRight = new Image(getClass().getResource("LevelRight.png").toExternalForm());
            ImageView imageViewRight = new ImageView(imageRight);
            Button buttonRight = new Button();
            buttonRight.setGraphic(imageViewRight);
            buttonRight.setStyle("-fx-background-color: black");           
            GridPane.setHalignment(Level, HPos.RIGHT);
            GridPane.setMargin(buttonRight, new Insets(0, 0, 0, 10));
            buttonRight.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                	if(TaquinFX.score != 0) {
                		if(Index_level < TaquinFX.IndexMax-1) {
                    		Index_level++;
                        	setLevel();
                        	setScore();
                        	setBoardGame();
                    	}
                	}
                	}
            });
            homepage.add(buttonRight, 2, 0);

            
            //settings
            buttonRules.setGraphic(imageViewRules);
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
            buttonReset.setGraphic(imageViewReset);
            buttonReset.setStyle("-fx-background-color: black");   
            buttonReset.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                	Button buttonrefreshUI1 = new Button("Melange 1");
                	buttonrefreshUI1.setOnAction(new EventHandler<ActionEvent>() {
                		@Override
                		public void handle(ActionEvent event) {
                			TaquinFX.refreshUI1();
                		}
                	});
                	
                	Button buttonrefreshUI2 = new Button("Melange 2");
                	buttonrefreshUI2.setOnAction(new EventHandler<ActionEvent>() {
                		@Override
                		public void handle(ActionEvent event) {
                			TaquinFX.refreshUI2();
                		}
                	});
                	
                	Button buttonrandom = new Button("Random");
                	buttonrandom.setOnAction(new EventHandler<ActionEvent>() {
                		@Override
                		public void handle(ActionEvent event) {
                			TaquinFX.refreshUIRandom();
                		}
                	});
                	
                	TaquinFX.gridPane.add(buttonrefreshUI1, 0, 10);
                	TaquinFX.gridPane.add(buttonrefreshUI2, 1, 10);
                	TaquinFX.gridPane.add(buttonrandom, 2, 10);                	
                	}
            });
            homepage.add(buttonReset, 2,15);
            
            buttonResolve.setGraphic(imageViewResolve);
            buttonResolve.setStyle("-fx-background-color: black");   
            buttonResolve.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                	TaquinFX.test_resolve=true;
                	TaquinFX.resolve();                }
            });
            homepage.add(buttonResolve, 1,15);
            
            //nombre de coups actuellement joués
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
            GridPane.setColumnSpan(record, 3); // Spécifie que nbTurns occupe 3 colonnes
            homepage.add(record, 0, 9);
            
            NumberRecord = new Text(20, 100, Integer.toString(TaquinFX.score));
            NumberRecord.setFont(new Font(75));
            NumberRecord.setFill(Color.WHITE);
            GridPane.setHalignment(NumberRecord, HPos.CENTER);
            GridPane.setMargin(NumberRecord, new Insets(0, 10, 0, 0));
            homepage.add(NumberRecord, 1, 10);
            
                   
            
            
            /*homepage.getChildren().add(new Label(""));
            TaquinFX.gridPane.getChildren().add(new Label(""));*/
            splitPane.getItems().addAll(homepage, TaquinFX.gridPane);
            //splitPane.setDividerPositions(0.5);
          
            primaryStage.setScene(scene1);
            primaryStage.show();
            primaryStage.setTitle("Jeu du Taquin");
            primaryStage.setResizable(false);



            

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void setBoardGame() {
    	splitPane.getItems().remove(TaquinFX.gridPane);
    	TaquinFX.coups = 0;
    	setCoup(TaquinFX.coups);
    	TaquinFX.gridPane.getChildren().clear();
    	try {
			TaquinFX.RUNstart();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	//TaquinFX.gridPane.getChildren().add(new Label(""));
    	splitPane.getItems().add(TaquinFX.gridPane);
    	
    }
    public static void setCoup(int coup) {
    	homepage.getChildren().remove(NumberTurns);
        NumberTurns.setText(Integer.toString(coup));	
    	NumberTurns.setFont(new Font(75));
        NumberTurns.setFill(Color.WHITE);
        GridPane.setHalignment(NumberTurns, HPos.CENTER);
        GridPane.setMargin(NumberTurns, new Insets(0, 10, 0, 0));
        homepage.add(NumberTurns, 1, 6);
    }
    
    public static void setLevel() {
    	homepage.getChildren().remove(Level);
        Level.setText("#"+ (Index_level+1));	
        Level.setFont(new Font(75));
        Level.setFill(Color.WHITE);  
        GridPane.setHalignment(Level, HPos.CENTER);
        GridPane.setMargin(Level, new Insets(0, 10, 0, 0));
        homepage.add(Level, 1, 0);
    }
    
    public static void setScore() {
    	homepage.getChildren().remove(NumberRecord);
    	try {
			TaquinFX.RUNstart();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	NumberRecord.setText(Integer.toString(TaquinFX.score));	
    	NumberRecord.setFont(new Font(75));
        NumberRecord.setFill(Color.WHITE);
        GridPane.setHalignment(NumberRecord, HPos.CENTER);
        GridPane.setMargin(NumberRecord, new Insets(0, 10, 0, 0));
        homepage.add(NumberRecord, 1, 10);
    }
	public static int getRecord() {
		return Integer.parseInt(NumberTurns.getText());
	}
	
	public static void victory() {
    	TaquinFX.gridPane.getChildren().clear();
    	try {
			TaquinFX.RUNstart();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 for (int i = 0; i < TaquinFX.gridPane.getChildren().size(); i++) {
			 if (TaquinFX.gridPane.getChildren().get(i) instanceof Button) {
		            Button bouton = (Button) TaquinFX.gridPane.getChildren().get(i);
		            bouton.setStyle("-fx-background-color: chartreuse; -fx-border-color: black;");
		        }
	}

	}
}
