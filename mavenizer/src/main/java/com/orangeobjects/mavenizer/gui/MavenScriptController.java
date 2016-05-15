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
import com.orangeobjects.mavenizer.business.operations.OperationCopyToClipboard;
import com.orangeobjects.mavenizer.data.Library;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import org.apache.commons.lang3.StringUtils;

/**
 * FXML Controller class
 *
 * @author Michael Hofmann <Michael.Hofmann@OrangeObjects.de>
 */
public class MavenScriptController implements Initializable, Observer {

    private final static String NL = "\n";

    @FXML
    TextArea txaScriptText;
    @FXML
    CheckBox cbxWrapText;
    @FXML
    CheckBox cbxCommentErrors;
    @FXML
    Button butClipboard;
    @FXML
    Button butRefresh;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Manager.getInstance().getSignalizer().addObserver(this);
        butRefresh.setOnAction(handleRefresh);
        butClipboard.setOnAction(handleClipboard);
        cbxCommentErrors.setOnAction(handleCommentErrors);
    }

    private final EventHandler<ActionEvent> handleClipboard = event -> {
        Manager.getInstance().add(
                new OperationCopyToClipboard(txaScriptText)
        );
    };

    private final EventHandler<ActionEvent> handleRefresh = event -> {
        updateInternal();
    };

    private final EventHandler<ActionEvent> handleCommentErrors = event -> {
        updateInternal();
    };
    
    @FXML
    public void cbxWrapTextHandler(ActionEvent event) {
        Platform.runLater(() -> {
            txaScriptText.setWrapText(cbxWrapText.isSelected());
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        updateInternal();
    }

    private void updateInternal() {
        StringBuilder sb = new StringBuilder();
        Manager.getInstance().getLibrarySet().forEach(lib -> {
            if (lib.isInstall()) {
                if (isError(lib)) {
                    sb.append("# ");
                }
                sb.append("mvn install:install-file")
                        .append(" -D").append("file=").append(lib.getOriginalFile().getAbsolutePath())
                        .append(" -D").append("groupId=").append(lib.getArtifactId())
                        .append(" -D").append("artifactId=").append(lib.getGroupId())
                        .append(" -D").append("version=").append(lib.getVersion())
                        .append(" -D").append("packaging=jar").append(NL);
            }
        });
        Platform.runLater(() -> {
            txaScriptText.setText(sb.toString());
        });
    }
    
    private boolean isError(Library lib) {
        return cbxCommentErrors.isSelected() && (
            StringUtils.isBlank(lib.getArtifactId()) 
         || StringUtils.isBlank(lib.getGroupId()) 
         || StringUtils.isBlank(lib.getVersion())
        );
    }
}

