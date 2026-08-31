package com.willclay.pngconverter.ui.menus;

import com.willclay.pngconverter.actions.ActionManager;

import javax.swing.*;
import java.awt.event.KeyEvent;

public final class ViewMenu extends JMenu
{
    public ViewMenu(ActionManager actionManager)
    {
        super("View");
        setMnemonic(KeyEvent.VK_V);

        add(new JMenuItem(actionManager.getZoomInAction()));
        add(new JMenuItem(actionManager.getZoomOutAction()));
        add(new JMenuItem(actionManager.getActualSizeAction()));
        add(new JMenuItem(actionManager.getFitToWindowAction()));
        addSeparator();

        add(createRadioMenu("Preview Background", actionManager.getPreviewBackgroundActions()));
        add(createRadioMenu("Theme", actionManager.getThemeActions()));
    }

    private static JMenu createRadioMenu(String name, Iterable<? extends Action> actions)
    {
        JMenu menu = new JMenu(name);
        ButtonGroup group = new ButtonGroup();

        for (Action action : actions)
        {
            JRadioButtonMenuItem item = new JRadioButtonMenuItem(action);
            group.add(item);
            menu.add(item);
        }
        return menu;
    }
}
