/*
 *  O R A N G E   O B J E C T S
 *  copyright by Orange Objects
 * 
 *  http://www.OrangeObjects.de
 * 
 *  $Id$
 */
package com.orangeobjects.mavenizer.gui;

import com.orangeobjects.mavenizer.business.AbstractOperation;
import com.orangeobjects.mavenizer.business.Manager;
import com.orangeobjects.mavenizer.business.OperationException;
import com.orangeobjects.mavenizer.business.operations.OperationAddLib;
import com.orangeobjects.mavenizer.business.operations.OperationRemoveLib;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.SetChangeListener;
import javafx.collections.SetChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.ValidatorException;

/**
 * FXML LibraryStackController class
 *
 * @author Michael Hofmann <Michael.Hofmann@OrangeObjects.de>
 */
public class LibraryStackController implements Initializable {

    static final Logger LOGGER = Logger.getLogger(LibraryStackController.class.getName());
    private final static ApplicationConfig CONFIG = ApplicationConfig.getInstance();
    
    public final static String FILECHOOSER_INITIAL_DIR = "mavenizer.filechooser.initialDirectory";
    
    private final static DropShadow BORDER_GLOW = getBorderGlowEffect();
    private Optional<File> optInitialDir = Optional.empty();
    private Optional<File> optLastDir = Optional.empty();
    
    @FXML
    private Accordion accLibList;
    @FXML
    private HBox hbxTrashcan;
    @FXML
    private Button butAdd;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Manager.getInstance().getLibCollection().addListener(new MyChangeListener());

        // initialize trashcan
//        final Image image1 = new Image(MainApp.class.getResourceAsStream("/images/AddToList.png"));
//        butAdd.setGraphic(new ImageView(image1));

        // initialize plus
//        final Image image2 = new Image(MainApp.class.getResourceAsStream("/images/RemoveFromList.png"));
//        hbxTrashcan.getChildren().add(new ImageView(image2));
        
        butAdd.setOnAction(handleAdd);
        
        
        hbxTrashcan.setOnDragOver((DragEvent event) -> {
            /* data is dragged over the target */
            /* accept it only if it is not dragged from the same node
            * and if it has a string data */
            if (event.getGestureSource() != hbxTrashcan
                    && event.getDragboard().hasString()) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.MOVE);
                
            }
            event.consume();
        });
        
        hbxTrashcan.setOnDragEntered((DragEvent event) -> {
            /* the drag-and-drop gesture entered the target */
            /* show to the user that it is an actual gesture target */
            if (event.getGestureSource() != hbxTrashcan
                    && event.getDragboard().hasString()) {
                Platform.runLater(() -> {
                    hbxTrashcan.setEffect(BORDER_GLOW);
                });
            }
            event.consume();
        });
        
        hbxTrashcan.setOnDragDropped((DragEvent event) -> {
            /* data dropped */
            /* if there is a string data on dragboard, read it and use it */
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                int id = Integer.parseInt(db.getString());
                AbstractOperation op = new OperationRemoveLib(id);
                Manager.getInstance().add(op);
                success = true;
            }
            /* let the source know whether the string was successfully
            * transferred and used */
            event.setDropCompleted(success);
            event.consume();
            Platform.runLater(() -> {
                hbxTrashcan.setEffect(null);
            });
        });       
    }

    private static DropShadow getBorderGlowEffect() {
        int depth = 70;
        DropShadow borderGlow = new DropShadow();
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);
        borderGlow.setColor(Color.RED);
        borderGlow.setWidth(depth);
        borderGlow.setHeight(depth);
        return borderGlow;
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
                        root.setUserData(lib);
                        LibraryNodeController rootCtrl = loader.getController();
                        rootCtrl.equip(jarLibrary);
                        Platform.runLater(() -> {
                            accLibList.getPanes().add(root);
                        });
                    } else {
                        // for future releases
                        throw new OperationException("unknown instance of lib");
                    }
                } else if (change.wasRemoved()) {
                    removeTitledPane(change.getElementRemoved());
                }
            } catch (IOException | OperationException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /** Remove TitledPane identified by given Library from accordion
     * 
     * @param lib 
     */
    private void removeTitledPane(Library lib) {
        assert lib != null;
        searchTitledPane(lib)
            .ifPresent(tp -> {
                Platform.runLater(() -> {
                    accLibList.getPanes().remove(tp);
                });
            });
    }
    
    /** Find the TitledPane to the given Library using the Library-Id.
     * 
     * @param searchLib
     * @return 
     */
    private Optional<TitledPane> searchTitledPane(Library searchLib) {
        return accLibList.getPanes()
                .stream()
                .filter(tp -> getLibrary(tp).getId() == searchLib.getId())
                .findFirst();
    }
    
    /** Give us the internal UserData of the given TitledPane - should be a Library.
     * 
     * @param tp
     * @return 
     */
    private Library getLibrary(TitledPane tp) {
        assert tp.getUserData() != null;
        assert tp.getUserData() instanceof Library;
        return (Library)tp.getUserData();
    }
    
    

    private final EventHandler<ActionEvent> handleAdd = event -> {
        selectFiles().forEach(file -> {
            OperationAddLib op = new OperationAddLib(file);
            Manager.getInstance().add(op);
        });
    };    

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
                // we save the parent dir for further selects
                File firstFile = fileList.get(0);
                optLastDir = Optional.of(firstFile.getParentFile());
                Manager.getInstance().getUserResources()
                        .setProperty(FILECHOOSER_INITIAL_DIR, firstFile.getParent());
                
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
                String dirName = CONFIG.getProperty(FILECHOOSER_INITIAL_DIR, null);
                LOGGER.log(Level.FINE, "initial dir (props) is {0}",dirName);
                // if the application configuration gives no hint, we have a look to
                // the user resource file.
                if (StringUtils.isBlank(dirName)) {
                    dirName = Manager.getInstance().getUserResources()
                            .getProperty(FILECHOOSER_INITIAL_DIR, null);
                }
                LOGGER.log(Level.FINE, "initial dir is {0}", dirName);
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
