package org.raul.utilities;

import javax.swing.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.raul.ui.UI.textArea;
import static org.raul.ui.UI.useRegexCheckBox;

public class Searcher {

    private static ArrayList<Map.Entry<Integer, Integer>> matches = new ArrayList<>();
    public static int currentEntryIndex;

    public static void startSearch(String query) {
        Runnable search = () -> {
            boolean isRegex = useRegexCheckBox.isSelected();
            String texto = textArea.getText();
            String toBeCompiled = isRegex ? query : Pattern.quote(query);
            Matcher matcher = Pattern.compile(toBeCompiled).matcher(texto);

            while (matcher.find()) {
                matches.add(new AbstractMap.SimpleEntry<>(matcher.group().length(), matcher.start()));
            }

            currentEntryIndex = 0;
            highlightText();
        };

        try {
            SwingUtilities.invokeLater(search);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void highlightText() {
        try {
            textArea.setCaretPosition(matches.get(currentEntryIndex).getValue() + matches.get(currentEntryIndex).getKey());
            textArea.select(matches.get(currentEntryIndex).getValue(), matches.get(currentEntryIndex).getValue() + matches.get(currentEntryIndex).getKey());
            textArea.grabFocus();
        } catch (Exception ignored) {

        }
    }

    public static void previousMatch() {
        if (currentEntryIndex > 0) {
            currentEntryIndex--;
        } else {
            currentEntryIndex = matches.size() - 1;
        }

        highlightText();
    }

    public static void nextMatch() {
        if (currentEntryIndex < matches.size() - 1) {
            currentEntryIndex++;
        } else {
            currentEntryIndex = 0;
        }

        highlightText();
    }

    public static void reset() {
        matches.clear();
        currentEntryIndex = 0;
    }
}
