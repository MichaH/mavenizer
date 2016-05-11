/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orangeobjects.mavenizer.business.operations;

import com.orangeobjects.mavenizer.business.AbstractOperation;
import com.orangeobjects.mavenizer.business.OperationException;
import com.orangeobjects.mavenizer.business.OperationType;
import javafx.application.Platform;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

/**
 *
 * @author michael
 */
public class OperationCopyToClipboard extends AbstractOperation {

    private final TextInputControl textControl;

    public OperationCopyToClipboard(TextInputControl textControl) {
        this.textControl = textControl;
    }
    
    @Override
    public void execute() throws OperationException {
        Platform.runLater(() -> {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(textControl.getText());
            clipboard.setContent(content);
        });        
    }

    @Override
    public String getName() {
        return "Copy text to clipboard";
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.COPY_TO_CLIPBOARD;
    }
}
