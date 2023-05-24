package application;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Label;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HomePage extends Application {
    
	public static int numberMove = 0;
	private static GridPane homepage = new GridPane();
	static SplitPane splitPane = new SplitPane();
    private static Scene scene1 = new Scene(splitPane,700,700);
    
    //private GridPane gridPane = new GridPane();
    
    private static Text Level = new Text(20,100,"#"+2);
    
    private  Image imageLeft = new Image(getClass().getResource("LevelLeft.png").toExternalForm());
    private  ImageView imageViewLeft = new ImageView(imageLeft);
    private static Button buttonLeft = new Button();
    
    private  Image Settings = new Image(getClass().getResource("Settings.png").toExternalForm());
    private  ImageView imageViewSettings= new ImageView(Settings);
    private static Button buttonSettings = new Button();
    
    private  Image Reset = new Image(getClass().getResource("Reset.png").toExternalForm());
    private  ImageView imageViewReset= new ImageView(Reset);
    private static Button buttonReset = new Button();
    
    //private Image play = new Image(getClass().getResource("play.png").toExternalForm());
   // private ImageView imageViewplay= new ImageView(play);
   // private Button buttonplay = new Button();
    
    private static Text nbTurns = new Text(20, 100, "NB TURNS");
    
    private static Text NumberTurns = new Text(20, 100, Integer.toString(numberMove));

    private static Text record = new Text(20, 100, "RECORD");

    private static Text NumberRecord = new Text(20, 100, "0");


    @Override
    public void start(Stage primaryStage) {
        try {


            //Acceuil
            homepage.setPadding(new Insets(10));
            homepage.setHgap(10);
            homepage.setVgap(10);

            //fond en noir
            scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            homepage.getStyleClass().add("my-gridpane");
            homepage.setStyle("-fx-background-color: black;");
            
            
            //Ajout des niveaux
            Level.setFont(new Font(75));
            Level.setFill(Color.WHITE);  
            GridPane.setHalignment(Level, HPos.CENTER);
            GridPane.setMargin(Level, new Insets(0, 10, 0, 0));
            homepage.add(Level, 1, 0);

            //niveau Précédent
            
            buttonLeft.setGraphic(imageViewLeft);
            buttonLeft.setStyle("-fx-background-color: black");
            GridPane.setHalignment(Level, HPos.LEFT);
            GridPane.setMargin(buttonLeft, new Insets(0, 0, 0, 10));
            homepage.add(buttonLeft, 0, 0);


            //niveau Suivant
            Image imageRight = new Image(getClass().getResource("LevelRight.png").toExternalForm());
            ImageView imageViewRight = new ImageView(imageRight);
            Button buttonRight = new Button();
            buttonRight.setGraphic(imageViewRight);
            buttonRight.setStyle("-fx-background-color: black");           
            GridPane.setHalignment(Level, HPos.RIGHT);
            GridPane.setMargin(buttonRight, new Insets(0, 0, 0, 10));
            homepage.add(buttonRight, 2, 0);

            
            //Paramètres
            
            buttonSettings.setGraphic(imageViewSettings);
            buttonSettings.setStyle("-fx-background-color: black");           
            homepage.add(buttonSettings, 0,15);
            
            
            //Remise du niveau à 0
            
            buttonReset.setGraphic(imageViewReset);
            buttonReset.setStyle("-fx-background-color: black");   
            buttonReset.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                	TaquinFX.refreshUI();                }
            });
            homepage.add(buttonReset, 2,15);
            
            
            
            //nombre de coups actuellement joués
            
            nbTurns.setFont(new Font(50));
            nbTurns.setFill(Color.WHITE);
            GridPane.setHalignment(nbTurns, HPos.CENTER);
            GridPane.setMargin(nbTurns, new Insets(0, 10, 0, 0));
            GridPane.setColumnSpan(nbTurns, 3); // Spécifie que nbTurns occupe 3 colonnes
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
            
            NumberRecord.setFont(new Font(75));
            NumberRecord.setFill(Color.WHITE);
            GridPane.setHalignment(NumberRecord, HPos.CENTER);
            GridPane.setMargin(NumberRecord, new Insets(0, 10, 0, 0));
            homepage.add(NumberRecord, 1, 10);
            
                   
            TaquinFX.RUNstart();
            
            homepage.getChildren().add(new Label(""));
            TaquinFX.gridPane.getChildren().add(new Label(""));
            splitPane.getItems().addAll(homepage, TaquinFX.gridPane);
            
            primaryStage.setScene(scene1);
            primaryStage.show();
            primaryStage.setTitle("Jeu du Taquin");
            primaryStage.setResizable(false);



            

        } catch(Exception e) {
            e.printStackTrace();
        }
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
    
    /*public void runApplication() {
        Application.launch(TaquinFX.class);
    }*/
}
