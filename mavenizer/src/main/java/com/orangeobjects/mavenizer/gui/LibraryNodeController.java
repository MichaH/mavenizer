/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orangeobjects.mavenizer.gui;

import com.orangeobjects.mavenizer.data.JarLibrary;
import com.orangeobjects.mavenizer.data.Library;
import com.orangeobjects.mavenizer.util.DelayedEventProducer;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

/**
 * FXML Controller class
 *
 * @author michael
 */
public class LibraryNodeController implements Initializable {

    private DelayedEventProducer signalizer;
    
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
    TextField ftxNeWLibName;
    
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
    
    public void equip(JarLibrary lib) {
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
        
        // now, after we filled all widgets, we install the
        // different listeners
        
        
        patJarlibMainPanel.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                /* drag was detected, start a drag-and-drop gesture*/
                /* allow any transfer mode */
                Dragboard db = patJarlibMainPanel.startDragAndDrop(TransferMode.MOVE);

                /* Put a string on a dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(labId.getText());
                db.setContent(content);

                event.consume();
            }
        });
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.signalizer = new DelayedEventProducer(2 * 1000L);
        signalizer.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println("HAAAAAAAAAALLO");
            }
        });
        
        chxScope.setItems(Library.SCOPE_LIST);
        chxScope.setValue(Library.SCOPE_LIST.get(1));
        chxScope.getSelectionModel().selectedItemProperty().addListener(
            event -> {
                update();
            }
        );
        
        chxType.setItems(Library.TYPE_LIST);
        chxType.setValue(Library.TYPE_LIST.get(1));
        chxType.getSelectionModel().selectedItemProperty().addListener(
            event -> {
                update();
            }
        );
        
        cbxPom.setOnAction(actionEventHandler);
        cbxRepository.setOnAction(actionEventHandler);
        cbxGroupIdInherit.setOnAction(actionEventHandler);
        cbxVersionInherit.setOnAction(actionEventHandler);
        
        ftxVersion.textProperty().addListener(event -> {
                update();
            }
        );
    }
    
    private final EventHandler<ActionEvent> actionEventHandler = event -> {
        update();
    };
    
    
    
    final void update() {
        signalizer.notifyObservers();
    }
    
    @FXML
    private void changedGroupId(ActionEvent event) {
    }
    
}
