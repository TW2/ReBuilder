package org.wingate.rebuilder.widget;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class ReFrame extends WidgetAbstract<JFrame> {

    private Dimension previewParentSize;
    private Dimension previewDimension;

    public ReFrame() {
        name = "JFrame";
        widget = new JFrame();
        previewParentSize = null;
        previewDimension = new Dimension(700, 600);
    }

    public Dimension getPreviewParentSize() {
        return previewParentSize;
    }

    public void setPreviewParentSize(Dimension previewParentSize) {
        this.previewParentSize = previewParentSize;
    }

    public Dimension getPreviewDimension() {
        return previewDimension;
    }

    public void setPreviewDimension(Dimension previewDimension) {
        this.previewDimension = previewDimension;
    }

    @Override
    public void draw(Graphics2D g) {
        if(previewParentSize != null){
            g.setColor(new Color(233, 233, 233));
            double x = (previewParentSize.getWidth() - previewDimension.getWidth()) / 2;
            double y = (previewParentSize.getHeight() - previewDimension.getHeight()) / 2;
            g.fill(new Rectangle2D.Double(
                    x, y - 30d,
                    previewDimension.getWidth(), previewDimension.getHeight() + 30d
            ));

            g.setColor(new Color(50, 50, 50));
            g.draw(new Rectangle2D.Double(
                    x, y - 30d,
                    previewDimension.getWidth(), previewDimension.getHeight() + 30d
            ));
            g.draw(new Line2D.Double(x, y, x + previewDimension.getWidth(), y));

            g.setColor(new Color(100, 100, 255));
            g.fill(new Ellipse2D.Double(
                    x + previewDimension.getWidth() - 20, y - 20, 10, 10
            ));
            g.fill(new Ellipse2D.Double(
                    x + previewDimension.getWidth() - 40, y - 20, 10, 10
            ));
            g.fill(new Ellipse2D.Double(
                    x + previewDimension.getWidth() - 60, y - 20, 10, 10
            ));
        }
    }
}
