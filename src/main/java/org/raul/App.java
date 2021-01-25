package org.raul;

import org.raul.gui.GUI;

import javax.swing.*;

public class App {

    public static void main( String[] args ) throws Exception {
        Runnable initFrame = () -> new GUI();
        SwingUtilities.invokeAndWait(initFrame);
    }

}
