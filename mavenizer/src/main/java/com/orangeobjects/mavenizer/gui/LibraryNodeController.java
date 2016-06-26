/*
 * O R A N G E   O B J E C T S
 *
 * copyright by Orange Objects
 * http://www.OrangeObjects.de
 *
 */
package com.orangeobjects.mavenizer.gui;

import com.orangeobjects.mavenizer.business.Manager;
import com.orangeobjects.mavenizer.business.operations.OperationUpdateLibrary;
import com.orangeobjects.mavenizer.data.JarLibrary;
import com.orangeobjects.mavenizer.data.Library;
import com.orangeobjects.mavenizer.data.LibraryBean;
import com.orangeobjects.mavenizer.util.DelayedObserverable;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author Michael.Hofmann@OrangeObjects.de
 */
public class LibraryNodeController implements Initializable {

    private DelayedObserverable changedPropSignal;
    
    @FXML
    TitledPane patJarlibMainPanel;

    @FXML
    Label labId;
    @FXML
    Label labOriginalFile;
    @FXML
    TextField ftxOriginalArtefactId;
    @FXML
    TextField ftxOriginalVersion;

    @FXML
    TextField ftxGroupId;
    @FXML
    TextField ftxArtefactId;
    @FXML
    TextField ftxVersion;
    @FXML
    TextField ftxNewLibName;
    
    @FXML
    CheckBox cbxGroupIdInherit;
    @FXML
    CheckBox cbxVersionInherit;
    
    @FXML
    CheckBox cbxPom;
    @FXML
    CheckBox cbxRepository;
    
    @FXML
    ChoiceBox<String> chxScope;
    @FXML
    ChoiceBox<String> chxType;
    
    private final String creator;
    private JarLibrary lib;

    
    
    public LibraryNodeController() {
        // that's me, my controller id
        creator = "LibraryNodeController-" + RandomStringUtils.randomAlphanumeric(10);
    }
    
    /** 
     * Copies all variable values from gui to bean (value object).
     */
    private LibraryBean panel2bean() {
        return LibraryBean.build(lib.getOriginalFile())
                .setGroupId(ftxGroupId.getText())
                .setInheritedGroupId(cbxGroupIdInherit.isSelected())
                .setArtifactId(ftxArtefactId.getText())
                .setVersion(ftxVersion.getText())
                .setInheritedVersion(cbxVersionInherit.isSelected())
                .setPomDependency(cbxPom.isSelected())
                .setInstall(cbxRepository.isSelected())
                .setScope(chxScope.getValue())
                .setType(chxType.getValue())
                .setLastCreator(creator)
                .setLastDataVersionNo(lib.getLastDataVersionNo());
    }
    
    public void equip(JarLibrary lib) {
        assert lib != null;
        this.lib = lib;
        Platform.runLater(() -> {
            // set title
            patJarlibMainPanel.setText(lib.getOriginalFile().getName());
            // set all fields
            labId.setText(String.valueOf(lib.getId()));
            labOriginalFile.setText(lib.getOriginalFile().getAbsolutePath());
            ftxOriginalArtefactId.setText(lib.getOriginalArtefactname());
            ftxOriginalVersion.setText(lib.getOptOriginalVersion().orElse(null));
            // also the editable fields
            ftxGroupId.setText(lib.getGroupId());
            ftxArtefactId.setText(lib.getArtifactId());
            ftxVersion.setText(lib.getVersion());

            cbxVersionInherit.setSelected(lib.isInheritedVersion());
            cbxGroupIdInherit.setSelected(lib.isInheritedGroupId());
        });
        
        // now, after we filled all widgets, we install the
        // different listeners
        changedPropSignal.addObserver((Observable o, Object arg) -> {
            Manager.getInstance().add(new OperationUpdateLibrary(panel2bean()));
        });
        
        Manager.getInstance().getChangedContentAgent().addObserver((Observable o, Object arg) -> {
            Library newLib = (Library)arg;
            Platform.runLater(() -> {
                if (newLib.getId() == this.lib.getId()) {
                    ftxNewLibName.setText(newLib.getNewLibraryName());
                }
            });
        });
        
        patJarlibMainPanel.setOnDragDetected((MouseEvent event) -> {
            /* drag was detected, start a drag-and-drop gesture*/
            /* allow any transfer mode */
            Dragboard db = patJarlibMainPanel.startDragAndDrop(TransferMode.MOVE);
            
            /* Put a string on a dragboard */
            ClipboardContent content = new ClipboardContent();
            content.putString(labId.getText());
            db.setContent(content);
            
            event.consume();
        });
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Two seconds is a good choice to propagate property changes
        this.changedPropSignal = new DelayedObserverable(1300L);
        
        chxScope.setItems(Library.SCOPE_LIST);
        chxScope.setValue(Library.SCOPE_LIST.get(1));
        chxScope.getSelectionModel().selectedItemProperty()
                .addListener(event -> update());
        
        chxType.setItems(Library.TYPE_LIST);
        chxType.setValue(Library.TYPE_LIST.get(1));
        chxType.getSelectionModel().selectedItemProperty()
                .addListener(event -> update());
        
        cbxPom.setOnAction(event -> update());
        cbxRepository.setOnAction(event -> update());
        cbxGroupIdInherit.setOnAction(event -> update());
        cbxVersionInherit.setOnAction(event -> update());
        ftxGroupId.textProperty().addListener(event -> update());
        ftxVersion.textProperty().addListener(event -> update());
    }
    
    final void update() {
        changedPropSignal.notifyObservers();
    }
    
    @FXML
    private void changedGroupId(ActionEvent event) {
    }
}
