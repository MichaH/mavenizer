package com.orangeobjects.mavenizer;

import com.orangeobjects.mavenizer.business.Manager;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Parent root = FXMLLoader.load(getClass().getResource("/fxml/Cover.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("/fxml/LibraryNode.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Libraries.fxml"));
        
        Scene scene = new Scene(root);
        // scene.getStylesheets().add("/styles/cover.css");
        // scene.getStylesheets().add("/styles/librarynode.css");
        scene.getStylesheets().add("/styles/libraries.css");
        
        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
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
        
        Manager.getInstance().start();
        
        launch(args);
    }

}
