/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orangeobjects.mavenizer.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;

/**
 * FXML Controller class
 *
 * @author michael
 */
public class LibrariesController implements Initializable {

    @FXML
    private Button butAdd;
    @FXML
    private Accordion accLibList;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private int row = 0;
    
    @FXML
    private void addButtonAction(ActionEvent event) {
        try {
            System.out.println("Add");
            TitledPane root = FXMLLoader.load(getClass().getResource("/fxml/LibraryNode.fxml"));
            
            accLibList.getPanes().add(root);
            
        } catch (IOException ex) {
            Logger.getLogger(LibrariesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}