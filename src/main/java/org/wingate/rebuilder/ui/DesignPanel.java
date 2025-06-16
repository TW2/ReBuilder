package org.wingate.rebuilder.ui;

import org.wingate.rebuilder.widget.WidgetAbstract;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DesignPanel extends JPanel {

    private final List<WidgetAbstract<?>> widgets;

    private Color backColor;

    public DesignPanel() {
        setDoubleBuffered(true);
        widgets = new ArrayList<>();
        backColor = new Color(200, 200, 200);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;

        g2d.setColor(backColor);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Draw all widgets
        for(WidgetAbstract<?> widget : widgets){
            widget.draw(g2d);
        }
    }

    public List<WidgetAbstract<?>> getWidgets() {
        return widgets;
    }

    public void setBackColor(Color backColor) {
        this.backColor = backColor;
    }
}
