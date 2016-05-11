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
import com.orangeobjects.mavenizer.util.ApplicationConfig;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;
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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.ValidatorException;

/**
 * FXML Controller class
 *
 * @author michael
 */
public class LibrariesController implements Initializable {

    static final Logger LOGGER = Logger.getLogger(LibrariesController.class.getName());
    private final static ApplicationConfig config = ApplicationConfig.getInstance();
    
    private Optional<File> optInitialDir = Optional.empty();
    private Optional<File> optLastDir = Optional.empty();
    
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
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LibraryNode.fxml"));
                        final TitledPane root = loader.load();
                        LibraryNodeController rootCtrl = loader.getController();
                        rootCtrl.equip(jarLibrary);
                        Platform.runLater(() -> {
                            accLibList.getPanes().add(root);
                        });
                    } else {
                        // for future releases
                        throw new OperationException("unknown instance of lib");
                    }
                }
            } catch (IOException | OperationException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void addButtonAction(MouseEvent event) {
        selectFiles().forEach(f -> {
            OperationAddLib op = new OperationAddLib(f);
            Manager.getInstance().add(op);
        });
    }

    private List<File> selectFiles() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Java Library");
            fileChooser.setInitialDirectory(getEffectiveDirectory());
            fileChooser.getExtensionFilters().addAll(
                    new ExtensionFilter("Library Files", "*.jar", "*.zip", "*.lib", "*.ear", "*.war"),
                    new ExtensionFilter("All Files", "*.*"));
            List<File> fileList = fileChooser.showOpenMultipleDialog(null);
            if ((fileList == null) || (fileList.isEmpty())) {
                return Collections.EMPTY_LIST;
            } else  {
                File firstFile = fileList.get(0);
                optLastDir = Optional.of(firstFile.getParentFile());
                return fileList;
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "can't read property", ex);
            return Collections.EMPTY_LIST;
        }
    }
    
    private File getEffectiveDirectory() {
        return optLastDir.orElse(optInitialDir.orElseGet(() -> {
            try {
                String dirName = config.getProperty("filechooser.initialDirectory", null);
                if (StringUtils.isNotBlank(dirName)) {
                    File initialDir = new File(dirName);
                    if (initialDir.canRead() && initialDir.isDirectory()) {
                        optInitialDir = Optional.of(initialDir);
                        optLastDir = Optional.of(initialDir);
                        return initialDir;
                    }
                }
            } catch (InterruptedException | TimeoutException | ValidatorException ex) {
                LOGGER.log(Level.SEVERE, "can't read property", ex);
            }
            return null;
        }));
    }
}
