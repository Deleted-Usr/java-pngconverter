package com.willclay.pngconverter.ui.menus;

import com.willclay.pngconverter.actions.ActionManager;

import javax.swing.*;
import java.awt.event.KeyEvent;

public final class HelpMenu extends JMenu
{
    public HelpMenu(ActionManager actionManager)
    {
        super("Help");
        setMnemonic(KeyEvent.VK_H);

        add(new JMenuItem(actionManager.getSupportedFormatsAction()));
        addSeparator();
        add(new JMenuItem(actionManager.getAboutAction()));
    }
}
