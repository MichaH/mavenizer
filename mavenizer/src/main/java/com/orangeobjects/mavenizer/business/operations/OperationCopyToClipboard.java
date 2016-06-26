/*
 * O R A N G E   O B J E C T S
 *
 * copyright by Orange Objects
 * http://www.OrangeObjects.de
 *
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
 * @author Michael Hofmann <Michael.Hofmann@OrangeObjects.de>
 * 
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
