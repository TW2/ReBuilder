package org.wingate.rebuilder;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.wingate.rebuilder.ui.DesignPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

    public MainFrame() throws HeadlessException {
        setJMenuBar(createMenuBar());

        JPanel mainPanel = new JPanel(new GridLayout(1,2, 2, 2));
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        // Left: tree list + code
        JPanel codePanel = new JPanel(new BorderLayout());
        JTree treeList = new JTree();
        JScrollPane scrollPane = new JScrollPane(treeList);
        scrollPane.setPreferredSize(new Dimension(280, scrollPane.getHeight()));
        codePanel.add(scrollPane, BorderLayout.WEST);
        RSyntaxTextArea codeArea = new RSyntaxTextArea(20, 60);
        codeArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        codeArea.setCodeFoldingEnabled(true);
        RTextScrollPane sp = new RTextScrollPane(codeArea);
        codePanel.add(sp, BorderLayout.CENTER);
        mainPanel.add(codePanel);

        // Right: ui designer + properties + movable widgets
        JPanel rightPanel = new JPanel(new BorderLayout());
        JPanel embedPanel = new JPanel(new BorderLayout());
        DesignPanel designPanel = new DesignPanel();
        embedPanel.add(designPanel, BorderLayout.CENTER);
        JPanel toolsPanel = new JPanel(new GridLayout(1,2,2,2));
        toolsPanel.setPreferredSize(new Dimension(toolsPanel.getWidth(), 200));
        JTable propsTable = new JTable();
        JList<Object> widgetsList = new JList<>();
        toolsPanel.add(propsTable);
        toolsPanel.add(widgetsList);
        rightPanel.add(embedPanel, BorderLayout.CENTER);
        rightPanel.add(toolsPanel, BorderLayout.SOUTH);
        mainPanel.add(rightPanel);
    }

    private JMenuBar createMenuBar(){
        JMenuBar mnu = new JMenuBar();

        JMenu mFile = new JMenu("File");
        JMenuItem mFileNP = new JMenuItem("New project...");
        JMenuItem mFileOP = new JMenuItem("Open project...");
        JMenuItem mFileSP = new JMenuItem("Save project as...");
        JMenuItem mFileSR = new JMenuItem("Save project");
        JMenuItem mFileCP = new JMenuItem("Close project");
        JSeparator mFileSeparator1 = new JSeparator(JSeparator.HORIZONTAL);
        JMenuItem mFileQuit = new JMenuItem("Quit without saving");

        mFile.add(mFileNP);
        mFile.add(mFileOP);
        mFile.add(mFileSP);
        mFile.add(mFileSR);
        mFile.add(mFileCP);
        mFile.add(mFileSeparator1);
        mFile.add(mFileQuit);
        mnu.add(mFile);

        mFileNP.addActionListener(this::newProjectActionListener);
        mFileOP.addActionListener(this::openProjectActionListener);
        mFileSP.addActionListener(this::saveAsProjectActionListener);
        mFileSR.addActionListener(this::saveProjectActionListener);
        mFileCP.addActionListener(this::closeProjectActionListener);
        mFileQuit.addActionListener(this::quitActionListener);

        return mnu;
    }

    private void quitActionListener(ActionEvent e){
        System.exit(0);
    }

    private void newProjectActionListener(ActionEvent e){

    }

    private void openProjectActionListener(ActionEvent e){

    }

    private void saveAsProjectActionListener(ActionEvent e){

    }

    private void saveProjectActionListener(ActionEvent e){

    }

    private void closeProjectActionListener(ActionEvent e){

    }
}
