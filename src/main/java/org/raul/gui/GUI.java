package org.raul.gui;

import org.raul.utilities.Loader;
import org.raul.utilities.Saver;
import org.raul.utilities.Searcher;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

public class GUI extends JFrame {

    public static JTextArea textArea;
    public static JCheckBox useRegexCheckBox;
    private JTextField searchQuery;
    private JFileChooser fileChooser;

    public GUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Editor de Texto");
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setSize(600,410);
        setLayout(new BorderLayout());
        setFileChooser();
        setMenu();
        setNorthPanel();
        setCenterPanel();
    }

    private void setFileChooser() {
        fileChooser = new JFileChooser();
        fileChooser.setName("FileChooser");
        fileChooser.setDialogTitle("Select file text");
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter fileChooserFilter = new FileNameExtensionFilter(".txt files", "txt");
        fileChooser.addChoosableFileFilter(fileChooserFilter);
        this.add(fileChooser);
    }

    //MENU
    private void setMenu() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createSearchMenu());
        setJMenuBar(menuBar);
    }

    private JMenu createFileMenu() {
        JMenu fileMenu =  new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem exit = new JMenuItem("Exit");

        fileMenu.setName("MenuFile");
        open.setName("MenuOpen");
        save.setName("MenuSave");
        exit.setName("MenuExit");

        open.addActionListener(actionEvent -> {
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                Loader.load(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        save.addActionListener(actionEvent -> {
            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                Saver.save(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        exit.addActionListener(actionEvent -> dispose());

        fileMenu.add(save);
        fileMenu.add(open);
        fileMenu.addSeparator();
        fileMenu.add(exit);

        return fileMenu;
    }

    private JMenu createSearchMenu() {
        JMenu searchMenu = new JMenu("Search");
        searchMenu.setName("MenuSearch");
        searchMenu.add(createStartSearchMenuItem());
        searchMenu.add(createPreviousMatchMenuItem());
        searchMenu.add(createNextMatchMenuItem());
        searchMenu.add(createUseRegexMenuItem());

        return searchMenu;
    }

    private JMenuItem createStartSearchMenuItem() {
        JMenuItem startSearch = new JMenuItem("Start search");
        startSearch.setName("MenuStartSearch");

        startSearch.addActionListener(a -> Searcher.startSearch(searchQuery.getText()));

        return startSearch;
    }

    private JMenuItem createPreviousMatchMenuItem() {
        JMenuItem previousMatch = new JMenuItem("Previous search");
        previousMatch.setName("MenuPreviousMatch");

        previousMatch.addActionListener(a -> Searcher.previousMatch());

        return previousMatch;
    }

    private JMenuItem createNextMatchMenuItem() {
        JMenuItem nextMatch = new JMenuItem("Next match");
        nextMatch.setName("MenuNextMatch");

        nextMatch.addActionListener(a -> Searcher.nextMatch());

        return nextMatch;
    }

    private JMenuItem createUseRegexMenuItem() {
        JMenuItem useRegularExpressionsMenuItem = new JMenuItem("Use regular expressions");
        useRegularExpressionsMenuItem.setName("MenuUseRegExp");

        useRegularExpressionsMenuItem.addActionListener(a -> useRegexCheckBox.doClick());

        return useRegularExpressionsMenuItem;
    }

    //NORTH PANEL
    private void setNorthPanel() {
        JPanel northPanel = new JPanel(new FlowLayout());
        northPanel.setSize(300,50);
        northPanel.setBorder(new EmptyBorder(10,0,0,0));
        setNorthPanelComponents(northPanel);
        add(northPanel, BorderLayout.NORTH);
    }

    private void setNorthPanelComponents(JPanel northPanel) {
        searchQuery = new JTextField();
        searchQuery.setName("SearchField");
        searchQuery.setPreferredSize(new Dimension(220,35));

        searchQuery.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                Searcher.reset();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                Searcher.reset();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                Searcher.reset();
            }
        });

        northPanel.add(createOpenButton());
        northPanel.add(createSaveButton());
        northPanel.add(searchQuery);
        northPanel.add(createSearchButton());
        northPanel.add(createPreviousMatchButton());
        northPanel.add(createNextMatchButton());
        northPanel.add(createUseRegexCheckBox());
    }

    private JButton createOpenButton() {
        ImageIcon openIcon = new ImageIcon(getClass().getResource("/open.png"));

        JButton openButton = new JButton(openIcon);
        openButton.setName("OpenButton");
        openButton.setPreferredSize(new Dimension(35,35));

        openButton.addActionListener(a -> {
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                Loader.load(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        return openButton;
    }

    private JButton createSaveButton() {
        ImageIcon saveIcon = new ImageIcon(getClass().getResource("/save.png"));

        JButton saveButton = new JButton(saveIcon);
        saveButton.setName("SaveButton");
        saveButton.setPreferredSize(new Dimension(35,35));
        saveButton.setVerticalAlignment(SwingConstants.CENTER);

        saveButton.addActionListener(a -> {
            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                Saver.save(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        return saveButton;
    }

    private JButton createSearchButton() {
        ImageIcon searchIcon = new ImageIcon(getClass().getResource("/search.png"));

        JButton searchButton = new JButton(searchIcon);
        searchButton.setName("StartSearchButton");
        searchButton.setPreferredSize(new Dimension(35,35));

        searchButton.addActionListener(a -> Searcher.startSearch(searchQuery.getText()));

        return searchButton;
    }

    private JButton createPreviousMatchButton() {
        ImageIcon previousMatchIcon = new ImageIcon(getClass().getResource("/leftArrow.png"));

        JButton previousMatchButton = new JButton(previousMatchIcon);
        previousMatchButton.setName("PreviousMatchButton");
        previousMatchButton.setPreferredSize(new Dimension(35,35));

        previousMatchButton.addActionListener(a -> Searcher.previousMatch());

        return previousMatchButton;
    }

    private JButton createNextMatchButton() {
        ImageIcon nextMatchIcon = new ImageIcon(getClass().getResource("/rightArrow.png"));

        JButton nextMatchButton = new JButton(nextMatchIcon);
        nextMatchButton.setName("NextMatchButton");
        nextMatchButton.setPreferredSize(new Dimension(35,35));

        nextMatchButton.addActionListener(a -> Searcher.nextMatch());

        return nextMatchButton;
    }

    private JCheckBox createUseRegexCheckBox() {
        useRegexCheckBox = new JCheckBox("Use regex");
        useRegexCheckBox.setName("UseRegExCheckbox");

        useRegexCheckBox.addChangeListener(change -> Searcher.reset());

        return useRegexCheckBox;
    }

    //CENTER PANEL
    private void setCenterPanel() {
        JPanel centerPanel = new JPanel(null);
        centerPanel.setVisible(true);
        setCenterPanelComponents(centerPanel);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void setCenterPanelComponents(JPanel centerPanel) {
        textArea = new JTextArea();
        textArea.setName("TextArea");
        textArea.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setName("ScrollPane");
        scrollPane.setBounds(35,5,530,275);
        centerPanel.add(scrollPane);
    }
}
