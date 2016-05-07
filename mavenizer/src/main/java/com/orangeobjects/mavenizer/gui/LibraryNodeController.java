/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orangeobjects.mavenizer.gui;

import com.orangeobjects.mavenizer.data.JarLibrary;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

/**
 * FXML Controller class
 *
 * @author michael
 */
public class LibraryNodeController implements Initializable {

    @FXML
    Label labId;
    @FXML
    Label labOriginalFile;
    @FXML
    TextField ftxOriginalArtefactId;
    @FXML
    TextField ftxOriginalVersion;
            
    @FXML
    TextField fieGroupId;
    @FXML
    ChoiceBox<String> cbxType;
    @FXML
    TitledPane patJarlibMainPanel;
    
    public void equip(JarLibrary lib) {
        patJarlibMainPanel.setText(lib.getOriginalFile().getName());
        labId.setText(String.valueOf(lib.getId()));
        labOriginalFile.setText(lib.getOriginalFile().getAbsolutePath());
        ftxOriginalArtefactId.setText(lib.getOriginalArtefactname());
        ftxOriginalVersion.setText(lib.getOptOriginalVersion().orElse(null));
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void changedGroupId(ActionEvent event) {
    }
}
