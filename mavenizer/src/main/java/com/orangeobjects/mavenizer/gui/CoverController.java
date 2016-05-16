/*
 *  O R A N G E   O B J E C T S
 *  copyright by Orange Objects
 * 
 *  http://www.OrangeObjects.de
 * 
 *  $Id$
 */
package com.orangeobjects.mavenizer.gui;

import com.orangeobjects.mavenizer.business.Manager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Michael Hofmann <Michael.Hofmann@OrangeObjects.de>
 */
public class CoverController implements Initializable {

    @FXML
    private GridPane panCollectionData;
    @FXML
    private GridPane pagMavenScriptPane;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Parent libraries = FXMLLoader.load(getClass().getResource("/fxml/LibraryStack.fxml"));
            panCollectionData.add(libraries, 0, 0);
            Parent mavenScript = FXMLLoader.load(getClass().getResource("/fxml/MavenScript.fxml"));
            pagMavenScriptPane.add(mavenScript, 0, 0);
            
        } catch (IOException ex) {
            Logger.getLogger(CoverController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    

    @FXML
    private void closeApplication(ActionEvent event) {
        Manager.getInstance().opStopApplication();
    }
}
