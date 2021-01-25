package org.raul.utilities;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.raul.gui.GUI.textArea;

public class Loader {

    public static void load(String filePath) {
        File file = new File(filePath.endsWith(".txt") ? filePath : filePath + ".txt");
        try (FileInputStream fis = new FileInputStream(file)) {
            textArea.setText(new String(fis.readAllBytes(), StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
