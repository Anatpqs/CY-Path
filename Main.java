package application;

import javafx.application.Application;

/**
 * The main class of the application.
 * 
 * <p>This class contains the entry point of the application, which is the {@link #main(String[])} method.</p>
 */
public class Main {	
    /**
     * The entry point of the application.
     * 
     * <p>This method is called when the application is launched. It launches the JavaFX application by invoking the
     * {@link Application#launch(Class, String[])} method and passing the {@link HomePage} class as the application's
     * entry point.</p>
     * 
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Application.launch(HomePage.class, args);
    }
}
