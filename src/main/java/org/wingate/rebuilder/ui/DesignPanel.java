package org.wingate.rebuilder.ui;

import javax.swing.*;
import java.awt.*;

public class DesignPanel extends JPanel {

    private Color backColor;

    public DesignPanel() {
        setDoubleBuffered(true);
        backColor = new Color(200, 200, 200);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;

        g2d.setColor(backColor);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    public void setBackColor(Color backColor) {
        this.backColor = backColor;
    }
}
