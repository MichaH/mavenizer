/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orangeobjects.mavenizer.gui;

import com.orangeobjects.mavenizer.MainApp;
import com.orangeobjects.mavenizer.business.Manager;
import com.orangeobjects.mavenizer.business.OperationException;
import com.orangeobjects.mavenizer.business.operations.OperationAddLib;
import com.orangeobjects.mavenizer.data.JarLibrary;
import com.orangeobjects.mavenizer.data.Library;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.SetChangeListener;
import javafx.collections.SetChangeListener.Change;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * FXML Controller class
 *
 * @author michael
 */
public class LibrariesController implements Initializable {

    @FXML
    private Accordion accLibList;
    @FXML
    private HBox hbxPlus;
    @FXML
    private HBox hbxTrashcan;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Manager.getInstance().getLibCollection().addListener(new MyChangeListener());

        // initialize trashcan
        final ImageView imv1 = new ImageView();
        final Image image1 = new Image(MainApp.class.getResourceAsStream("/images/Trash.png"));
        imv1.setImage(image1);
        hbxTrashcan.getChildren().add(imv1);

        // initialize plus
        final ImageView imv2 = new ImageView();
        final Image image2 = new Image(MainApp.class.getResourceAsStream("/images/Plus.png"));
        imv2.setImage(image2);
        hbxPlus.getChildren().add(imv2);

    }

    private class MyChangeListener implements SetChangeListener<Library> {
        @Override
        public void onChanged(Change<? extends Library> change) {
            try {
                if (change.wasAdded()) {
                    Library lib = change.getElementAdded();
                    assert lib != null;
                    if (lib instanceof JarLibrary) {
                        JarLibrary jarLibrary = (JarLibrary) lib;
                        final TitledPane root = FXMLLoader.load(getClass().getResource("/fxml/LibraryNode.fxml"));
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                accLibList.getPanes().add(root);                        
                            }
                        });
                    } else {
                        // for future releases
                        throw new OperationException("unknown instance of lib");
                    }
                }
            } catch (IOException | OperationException ex) {
                Logger.getLogger(LibrariesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void addButtonAction(MouseEvent event) {
        List<File> fileList = selectFiles();
        if ((fileList == null) || (fileList.isEmpty())) {
            return;
        }
        fileList.forEach(f -> {
            OperationAddLib op = new OperationAddLib(f.toPath());
            Manager.getInstance().add(op);
        });
    }

    private List<File> selectFiles() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Java Library");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Library Files", "*.jar", "*.zip", "*.lib"),
                new ExtensionFilter("All Files", "*.*"));
        return fileChooser.showOpenMultipleDialog(null);
    }
}
