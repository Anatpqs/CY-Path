package application;
import javafx.application.Application;
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

public class JeuTaquin extends Application {
    
    private static int SIZE = 3;
    private int[][] boardGame = new int[SIZE][SIZE];

    GridPane homepage = new GridPane();
    Scene scene1 = new Scene(homepage,400,600);

    Stage PlayingStage = new Stage();
    GridPane BoardGame = new GridPane();
    Scene scene2 = new Scene(BoardGame,100*SIZE,100*SIZE);

    private double initialX, initialY;
    private double previousTranslateX, previousTranslateY;

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

            //Ajout des niveaux
            Text Level = new Text(20,100,"#"+2);
            Level.setFont(new Font(75));
            Level.setFill(Color.WHITE);  
            GridPane.setHalignment(Level, HPos.CENTER);
            homepage.add(Level, 1, 0);

            //niveau Précédent
            Image imageLeft = new Image(getClass().getResource("LevelLeft.png").toExternalForm());
            ImageView imageViewLeft = new ImageView(imageLeft);
            Button buttonLeft = new Button();
            buttonLeft.setGraphic(imageViewLeft);
            buttonLeft.setStyle("-fx-background-color: black");
            GridPane.setHalignment(Level, HPos.LEFT);
            homepage.add(buttonLeft, 0, 0);


            //niveau Suivant
            Image imageRight = new Image(getClass().getResource("play.png").toExternalForm());
            ImageView imageViewRight = new ImageView(imageRight);
            Button buttonRight = new Button();
            buttonRight.setGraphic(imageViewRight);
            buttonRight.setStyle("-fx-background-color: black");           
            GridPane.setHalignment(Level, HPos.RIGHT);
            homepage.add(buttonRight, 2, 0);

            Image Settings = new Image(getClass().getResource("Settings.png").toExternalForm());
            ImageView imageViewSettings= new ImageView(Settings);
            Button buttonSettings = new Button();
            buttonSettings.setGraphic(imageViewSettings);
            buttonSettings.setStyle("-fx-background-color: black");           
            homepage.add(buttonSettings, 0,30);
            
            
            primaryStage.setScene(scene1);
            primaryStage.show();
            primaryStage.setTitle("Jeu du Taquin");
            primaryStage.setResizable(true);



            //Terrain de jeu

            int nbr=0;
            for(int i=0; i<SIZE; i++) {
                for(int j=0;j<SIZE;j++) {
                    boardGame[j][i]=nbr;
                    nbr++;
                    if(boardGame[j][i] != 0) {
                        Rectangle square = new Rectangle(100, 100, Color.GRAY);
                        Text number = new Text(String.valueOf(boardGame[j][i]));
                        number.setFont(Font.font(24));
                        number.setFill(Color.WHITE);

                        StackPane stackPane = new StackPane();
                        stackPane.getChildren().addAll(square, number);

                        stackPane.setOnMousePressed(event -> {
                            if (event.getButton() == MouseButton.PRIMARY) {
                                initialX = event.getSceneX();
                                initialY = event.getSceneY();
                                previousTranslateX = stackPane.getTranslateX();
                                previousTranslateY = stackPane.getTranslateY();
                            }
                        });

                        stackPane.setOnMouseDragged(event -> {
                            if (event.getButton() == MouseButton.PRIMARY) {
                                double offsetX = event.getSceneX() - initialX;
                                double offsetY = event.getSceneY() - initialY;

                                double newTranslateX = previousTranslateX + offsetX;
                                double newTranslateY = previousTranslateY + offsetY;

                                stackPane.setTranslateX(newTranslateX);
                                stackPane.setTranslateY(newTranslateY);
                            }
                        });

                        stackPane.setOnMouseReleased(event -> {
                            if (event.getButton() == MouseButton.PRIMARY) {
                                double finalTranslateX = stackPane.getTranslateX();
                                double finalTranslateY = stackPane.getTranslateY();

                                double gridSizeX = BoardGame.getWidth() / SIZE;
                                double gridSizeY = BoardGame.getHeight() / SIZE;

                                int newColumn = (int) Math.round(finalTranslateX / gridSizeX);
                                int newRow = (int) Math.round(finalTranslateY / gridSizeY);

                                stackPane.setTranslateX(newColumn * gridSizeX);
                                stackPane.setTranslateY(newRow * gridSizeY);
                            }
                        });

                        BoardGame.add(stackPane, j, i);
                    }           
                }

            }


            BoardGame.setStyle("-fx-background-color: #333333;");
            PlayingStage.setScene(scene2);
            PlayingStage.show();
            PlayingStage.setTitle("Plateau de Jeu");
            PlayingStage.setResizable(false);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
