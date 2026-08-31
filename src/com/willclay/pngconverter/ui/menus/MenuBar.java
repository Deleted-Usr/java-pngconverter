package com.willclay.pngconverter.ui.menus;

import com.willclay.pngconverter.actions.ActionManager;

import javax.swing.*;

/// The application menu bar containing the File, View, and Help menus.
public final class MenuBar extends JMenuBar
{
    public MenuBar(ActionManager actionManager)
    {
        add(new FileMenu(actionManager));
        add(new ViewMenu(actionManager));
        add(new HelpMenu(actionManager));
    }
}
