package com.orangeobjects.mavenizer;

import com.orangeobjects.mavenizer.business.Manager;
import com.orangeobjects.mavenizer.util.CommandLineArgs;
import com.orangeobjects.mavenizer.util.UserResources;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafx.application.Application.launch;


public class MainApp extends Application {

    private final static Logger LOGGER = Logger.getLogger(MainApp.class.getName());

    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Cover.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/cover.css");
        stage.setTitle("Mavenizer (c) OrangeObjects");
        
        stage.setOnCloseRequest(e -> Manager.getInstance().opStopApplication());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        LOGGER.info("stop was called");
    }

    
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        CommandLineArgs cli = new CommandLineArgs(args) ;
        cli.parse();
        
        
        Manager.getInstance().start();
        launch(args);
    }
}
