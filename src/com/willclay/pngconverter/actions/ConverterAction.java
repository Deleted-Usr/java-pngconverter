package com.willclay.pngconverter.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;

/// Common base for user commands exposed through Swing buttons and menu items.
///
/// It centralizes action names, accelerators, tooltips, and programmatic triggering;
/// subclasses only implement [#perform()].
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

    /// Creates an action with display metadata shared by every Swing presentation.
    ///
    /// @param name label shown in menus and on buttons
    /// @param shortcut accelerator, or `null` for none; see [Shortcuts]
    /// @param tooltip hover text, or `null` to reuse `name`
    protected ConverterAction(String name, KeyStroke shortcut, String tooltip)
    {
        super(name);

        if (shortcut != null) putValue(ACCELERATOR_KEY, shortcut);
        putValue(SHORT_DESCRIPTION, tooltip == null ? name : tooltip);
    }

    /// Performs the action's work on the Swing event-dispatch thread.
    protected abstract void perform();

    /// Runs this action from code rather than from a click.
    ///
    /// This is useful when one action delegates to another. Disabled actions do
    /// nothing, matching the behavior of a disabled Swing button or menu item.
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

    /// Funnels every Swing invocation through the single [#perform()] entry point.
    ///
    /// This method is final so subclasses cannot accidentally bypass that contract.
    @Override
    public final void actionPerformed(ActionEvent event)
    {
        perform();
    }
}
