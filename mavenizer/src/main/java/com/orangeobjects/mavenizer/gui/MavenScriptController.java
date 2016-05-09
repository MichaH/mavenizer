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
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Michael Hofmann <Michael.Hofmann@OrangeObjects.de>
 */
public class MavenScriptController implements Initializable, Observer {

    private final static String NL = "\n";
    
    @FXML
    TextArea txaScriptText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Manager.getInstance().getSignalizer().addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        StringBuilder sb = new StringBuilder();
        Manager.getInstance().getLibrarySet().forEach(lib -> {
            sb.append("mvn install:install-file")
              .append(" -D").append("file=").append(lib.getOriginalFile().getAbsolutePath())
              .append(" -D").append("groupId=").append(lib.getArtifactId())
              .append(" -D").append("artifactId=").append(lib.getGroupId())
              .append(" -D").append("version=").append(lib.getVersion())
              .append(" -D").append("packaging=jar").append(NL);
        });
        Platform.runLater(() -> {
            txaScriptText.setText(sb.toString());
        });
    }
}
