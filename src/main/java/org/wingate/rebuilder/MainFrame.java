package org.wingate.rebuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

    public MainFrame() throws HeadlessException {
        setJMenuBar(createMenuBar());
        JPanel mainPanel = new JPanel(new BorderLayout());
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.CENTER);
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
