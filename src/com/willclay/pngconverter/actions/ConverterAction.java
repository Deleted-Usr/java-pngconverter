package com.willclay.pngconverter.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;

public abstract class ConverterAction extends AbstractAction
{
    protected ConverterAction(String name)
    {
        this(name, null, null);
    }

    protected ConverterAction(String name, KeyStroke shortcut)
    {
        this(name, shortcut, null);
    }

    /**
     * @param name     the label shown in menus and on toolbar buttons
     * @param shortcut the accelerator, or null for none — see {@link Shortcuts}
     * @param tooltip  hover text, or null to reuse the name
     */
    protected ConverterAction(String name, KeyStroke shortcut, String tooltip)
    {
        super(name);

        if (shortcut != null) putValue(ACCELERATOR_KEY, shortcut);
        putValue(SHORT_DESCRIPTION, tooltip == null ? name : tooltip);
    }

    /** The work the action actually does. Always called on the Event Dispatch Thread. */
    protected abstract void perform();

    /**
     * Runs this action from code rather than from a click — one action
     * delegating to another, for example Save falling back to Save As.
     * <p>
     * Disabled actions do nothing, which is what a user pressing a greyed-out
     * button would get.
     */
    public final void trigger()
    {
        if (isEnabled()) perform();
    }

    public final void setIcon(Icon icon)
    {
        putValue(SMALL_ICON, icon);
    }

    public final String getName()
    {
        return String.valueOf(getValue(NAME));
    }

    /**
     * Final on purpose. Everything Swing invokes funnels through here, so
     * subclasses cannot accidentally bypass the one entry point.
     */
    @Override
    public final void actionPerformed(ActionEvent event)
    {
        perform();
    }
}
