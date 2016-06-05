/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orangeobjects.mavenizer.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author michael
 */
public class AboutController implements Initializable {

    @FXML
    private ScrollPane pflContent;
    
    private final Stage stage = new Stage();
    
    // Reference to the Text
    private final Text textRef = new Text() {{
        setLayoutY(100);
        setTextOrigin(VPos.CENTER);
        setTextAlignment(TextAlignment.CENTER);
        setWrappingWidth(400);
        setFill(Color.rgb(137, 137, 137));
        setFont(Font.font("SansSerif", FontWeight.NORMAL, 18));
    }};

    // Provides the animated scrolling behavior for the text
    private final TranslateTransition transTransition;

    public AboutController() {
        textRef.setText(getAboutText());
        transTransition = new TranslateTransition();
        transTransition.setDuration(new Duration(30000));
        transTransition.setNode(textRef);
        transTransition.setToY(-500);
        // setIinterpolator(Interpolator.LINEAR);
        transTransition.setInterpolator(Interpolator.EASE_OUT);
        transTransition.setCycleCount(Timeline.INDEFINITE);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        pflContent.setContent(textRef);
        pflContent.setStyle("-fx-background-color: transparent;");

        // close 'About' with any mouse click
        pflContent.setOnMouseClicked(event -> {
            if (stage != null) {
                stage.close();
            }
        });
        // close 'About' with ESC (escape key)
        pflContent.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                stage.close();
            }
        });
    }
    
    public void start(final Parent root) {
        stage.setTitle("About");
        stage.setHeight(400);
        stage.setWidth(400);
        stage.initModality(Modality.WINDOW_MODAL);
        // stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        transTransition.play();
    }

    private String getAboutText() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("M a v e n i z e r").append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("written by").append("\n");
        sb.append("Michael Hofmann").append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("Orange Objects").append("\n");
        sb.append("(c) 2016").append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("git hosted").append("\n");
        sb.append("https://github.com/MichaH/mavenizer").append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("Orange Objects GmbH & Co. KG").append("\n");
        sb.append("www.OrangeObjects.de").append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("(click here to close)").append("\n");
        return sb.toString();
    }
}
