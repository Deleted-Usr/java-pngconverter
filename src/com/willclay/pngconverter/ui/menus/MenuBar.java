package com.willclay.pngconverter.ui.menus;

import com.willclay.pngconverter.actions.ActionManager;

import javax.swing.*;

public final class MenuBar extends JMenuBar
{
    public MenuBar(ActionManager actionManager)
    {
        add(new FileMenu(actionManager));
        add(new ViewMenu(actionManager));
        add(new HelpMenu(actionManager));
    }
}
