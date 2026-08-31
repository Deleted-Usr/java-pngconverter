package com.willclay.pngconverter.actions.file;

import com.willclay.pngconverter.actions.ImageDependentAction;
import com.willclay.pngconverter.actions.Shortcuts;
import com.willclay.pngconverter.model.ImageSession;
import com.willclay.pngconverter.ui.imagepanel.ImagePanel;

import java.awt.event.KeyEvent;

/// Clears the current preview and closes the corresponding image session.
public final class CloseImageAction extends ImageDependentAction
{
    private final ImagePanel imagePanel;
    private final ImageSession imageSession;

    public CloseImageAction(ImagePanel imagePanel, ImageSession imageSession)
    {
        super(
                "Close Image",
                Shortcuts.menu(KeyEvent.VK_W),
                "Close the current image",
                imageSession
        );
        this.imagePanel = imagePanel;
        this.imageSession = imageSession;
    }

    @Override
    protected void perform()
    {
        imagePanel.clearImage();
        imageSession.closeImage();
    }
}
