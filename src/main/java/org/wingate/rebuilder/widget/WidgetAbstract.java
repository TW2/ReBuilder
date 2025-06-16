package org.wingate.rebuilder.widget;

public abstract class WidgetAbstract<T> implements WidgetInterface {

    protected String name;
    protected T widget;

    public WidgetAbstract() {
        name = "Unknown component";
        widget = null;
    }

    @Override
    public String getName() {
        return name;
    }

    public T getWidget() {
        return widget;
    }

    @Override
    public String toString() {
        return getName();
    }
}
