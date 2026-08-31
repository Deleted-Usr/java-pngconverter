package com.willclay.pngconverter.ui.menus;

import com.willclay.pngconverter.actions.ActionManager;
import com.willclay.pngconverter.actions.file.OpenRecentAction;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.KeyEvent;
import java.util.List;

/// Provides image-opening, exporting, closing, and application-exit actions.
///
/// The recent-images submenu is rebuilt each time it opens so it reflects the
/// latest persisted history.
public final class FileMenu extends JMenu
{
    private final ActionManager actionManager;
    private final JMenu openRecentMenu;

    public FileMenu(ActionManager actionManager)
    {
        super("File");
        this.actionManager = actionManager;
        setMnemonic(KeyEvent.VK_F);

        add(new JMenuItem(actionManager.getOpenImageAction()));

        openRecentMenu = new JMenu("Open Recent");
        openRecentMenu.setMnemonic(KeyEvent.VK_R);
        openRecentMenu.addMenuListener(new MenuListener()
        {
            @Override
            public void menuSelected(MenuEvent event)
            {
                rebuildRecentImages();
            }

            @Override public void menuDeselected(MenuEvent event) { }
            @Override public void menuCanceled(MenuEvent event) { }
        });
        add(openRecentMenu);

        addSeparator();
        add(new JMenuItem(actionManager.getExportPngAction()));
        add(new JMenuItem(actionManager.getCloseImageAction()));
        addSeparator();
        add(new JMenuItem(actionManager.getExitAction()));

        rebuildRecentImages();
    }

    /// Replaces the recent-images submenu with actions for the current history.
    private void rebuildRecentImages()
    {
        openRecentMenu.removeAll();
        List<OpenRecentAction> recentActions = actionManager.getOpenRecentActions();
        if (recentActions.isEmpty())
        {
            JMenuItem emptyItem = new JMenuItem("No Recent Images");
            emptyItem.setEnabled(false);
            openRecentMenu.add(emptyItem);
            return;
        }

        recentActions.forEach(action -> openRecentMenu.add(new JMenuItem(action)));
    }
}
