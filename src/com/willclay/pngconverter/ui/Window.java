package com.willclay.pngconverter.ui;

import com.willclay.pngconverter.actions.ActionManager;
import com.willclay.pngconverter.model.ImageSession;
import com.willclay.pngconverter.ui.imagepanel.ImagePanel;
import com.willclay.pngconverter.ui.menus.MenuBar;

import javax.swing.*;
import java.awt.*;

/// The application's main window and top-level UI coordinator.
///
/// It owns the shared [ImageSession], actions, preview, and conversion controls,
/// and updates its title and controls whenever the selected image changes.
public class Window extends JFrame
{
    private static final String APPLICATION_TITLE = "PNG Converter";

    private final ActionManager actionManager;
    private final ImageSession imageSession;
    private final ImagePanel imagePanel;
    private final OptionsPanel optionsPanel;

    public Window()
    {
        super(APPLICATION_TITLE);
        setLayout(new BorderLayout());

        imageSession = new ImageSession();
        imagePanel = new ImagePanel();
        actionManager = new ActionManager(this, imagePanel, imageSession);
        imagePanel.setOpenImageAction(actionManager.getOpenImageAction());

        optionsPanel = new OptionsPanel(
                actionManager.getOpenImageAction(),
                actionManager.getExportPngAction()
        );
        optionsPanel.setVisible(false);

        imageSession.addPropertyChangeListener(
                ImageSession.SELECTED_IMAGE_PROPERTY,
                event -> updateImageState()
        );

        setJMenuBar(new MenuBar(actionManager));
        add(imagePanel, BorderLayout.CENTER);
        add(optionsPanel, BorderLayout.SOUTH);
    }

    private void updateImageState()
    {
        optionsPanel.setVisible(imageSession.hasImage());
        setTitle(imageSession.hasImage()
                ? imageSession.getSelectedImagePath().getFileName() + " — " + APPLICATION_TITLE
                : APPLICATION_TITLE);
    }
}
