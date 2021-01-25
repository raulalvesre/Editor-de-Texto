package org.raul.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import static org.raul.gui.GUI.textArea;

public class Saver {

    public static void save(String filePath) {
        File file = new File(filePath.endsWith(".txt") ? filePath : filePath + ".txt");
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8")) {
            writer.write(textArea.getText());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}