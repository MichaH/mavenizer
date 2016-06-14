/*
 *  O R A N G E   O B J E C T S
 *  copyright by Orange Objects
 * 
 *  http://www.OrangeObjects.de
 * 
 */
package com.orangeobjects.mavenizer.gui;

import com.orangeobjects.mavenizer.business.Manager;
import com.orangeobjects.mavenizer.business.operations.OperationCopyToClipboard;
import com.orangeobjects.mavenizer.data.Library;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.application.Platform;
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
public class MavenPomController implements Initializable, Observer {

    private final static Logger LOGGER = Logger.getLogger(MavenPomController.class.getName());
    private final static String NL = "\n";

    @FXML
    TextArea txaScriptText;
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
        
        // This controller is interested in all changes regarding to the lib collection
        Manager.getInstance().getChangedStructurAgent().addObserver(this);
        // This controller is furthermore interested in all changes regarding to the lib collection
        Manager.getInstance().getChangedContentAgent().addObserver(this);
        
        // Action for button 'refresh'
        butRefresh.setOnAction(event -> updateInternal());
        // Action for button 'comment on error'
        cbxCommentErrors.setOnAction(event -> updateInternal());
        // Action for button 'clipboard'
        butClipboard.setOnAction(event -> {
            Manager.getInstance().add(
                    new OperationCopyToClipboard(txaScriptText)
            );
        });
    }
    
    @Override
    public void update(Observable o, Object arg) {
        updateInternal();
    }

    private void updateInternal() {
        Platform.runLater(() -> {
            txaScriptText.setText(buildScriptLines().toString());
        });
        LOGGER.fine("maven list was re-written");
    }
    
    private boolean isError(Library lib) {
        return cbxCommentErrors.isSelected() && (
            StringUtils.isBlank(lib.getArtifactId()) 
         || StringUtils.isBlank(lib.getGroupId()) 
         || StringUtils.isBlank(lib.getVersion())
        );
    }
    
    private StringBuilder buildScriptLines() {
        StringBuilder sb = new StringBuilder();
        sb.append("    <dependencies>").append(NL);
        Manager.getInstance().getLibrarySet().forEach(lib -> {
            if (lib.isInstall()) {
                if (isError(lib)) {
                    sb.append("        <!-- ").append(NL);
                }
                sb.append("        <dependency>").append(NL);
                sb.append("            <groupId>").append(lib.getGroupId()).append("</groupId>").append(NL);
                sb.append("            <artifactId>").append(lib.getArtifactId()).append("</artifactId>").append(NL);
                sb.append("            <version>").append(lib.getVersion()).append("</version>").append(NL);
                sb.append("            <scope>").append(lib.getScope()).append("</scope>").append(NL);
                sb.append("            <type>").append(lib.getType()).append("</type>").append(NL);
                sb.append("        </dependency>").append(NL);
                if (isError(lib)) {
                    sb.append("        -->").append(NL);
                }
            }
        });
        sb.append("    </dependencies>").append(NL);
        return sb;
    }
}

