package org.wingate.rebuilder;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.wingate.rebuilder.ui.DesignPanel;
import org.wingate.rebuilder.widget.ReFrame;
import org.wingate.rebuilder.widget.WidgetAbstract;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {

    private final DefaultMutableTreeNode rootNode;
    private final JTree treeList;
    private final RSyntaxTextArea codeArea;
    private final JList<WidgetAbstract<?>> widgetsList;
    private final DefaultListModel<WidgetAbstract<?>> widgetsModel;
    private final DesignPanel designPanel;

    public MainFrame() throws HeadlessException {
        setJMenuBar(createMenuBar());

        JPanel mainPanel = new JPanel(new GridLayout(1,2, 2, 2));
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        // Left: tree list + code
        JPanel codePanel = new JPanel(new BorderLayout());
        rootNode = new DefaultMutableTreeNode("+");
        treeList = new JTree(rootNode);
        JScrollPane scrollPane = new JScrollPane(treeList);
        scrollPane.setPreferredSize(new Dimension(280, scrollPane.getHeight()));
        codePanel.add(scrollPane, BorderLayout.WEST);
        codeArea = new RSyntaxTextArea(20, 60);
        codeArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        codeArea.setCodeFoldingEnabled(true);
        RTextScrollPane sp = new RTextScrollPane(codeArea);
        codePanel.add(sp, BorderLayout.CENTER);
        mainPanel.add(codePanel);

        // Right: ui designer + properties + movable widgets
        JPanel rightPanel = new JPanel(new BorderLayout());
        JPanel embedPanel = new JPanel(new BorderLayout());
        designPanel = new DesignPanel();
        embedPanel.add(designPanel, BorderLayout.CENTER);
        JPanel toolsPanel = new JPanel(new GridLayout(1,2,2,2));
        toolsPanel.setPreferredSize(new Dimension(toolsPanel.getWidth(), 200));
        JTable propsTable = new JTable();
        widgetsList = new JList<>();
        widgetsModel = new DefaultListModel<>();
        widgetsList.setModel(widgetsModel);
        widgetsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
                    addReFrameOneTimeIfSelected();
                    designPanel.repaint();
                }
            }
        });
        populateWidgets();
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

    private void populateWidgets(){
        widgetsModel.addElement(new ReFrame());
    }

    private void addReFrameOneTimeIfSelected(){
        if(widgetsList.getSelectedValue() instanceof ReFrame){
            boolean add = true;
            for(WidgetAbstract<?> w : designPanel.getWidgets()){
                if(w instanceof ReFrame){
                    add = false;
                    break;
                }
            }
            if(add){
                ReFrame x = new ReFrame();
                x.setPreviewParentSize(designPanel.getSize());
                designPanel.getWidgets().add(x);
                addToRoot(x);

                String xml = ReFrame.saveXMLToText(x);
                codeArea.setText(xml);
                codeArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
            }
        }
    }

    private void addToRoot(WidgetAbstract<?> widget){
        rootNode.add(new DefaultMutableTreeNode(widget));

        // Expand all
        for (int i = 0; i < treeList.getRowCount(); i++) {
            treeList.expandRow(i);
        }
    }
}
