package org.raul;

import org.raul.ui.UI;

import javax.swing.*;

public class App {

    public static void main( String[] args ) throws Exception {
        Runnable initFrame = () -> new UI();
        SwingUtilities.invokeAndWait(initFrame);
    }

}
