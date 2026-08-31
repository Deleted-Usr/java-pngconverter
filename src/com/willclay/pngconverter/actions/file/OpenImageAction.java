package com.willclay.pngconverter.actions.file;

import com.formdev.flatlaf.util.SystemFileChooser;
import com.willclay.pngconverter.actions.ConverterAction;
import com.willclay.pngconverter.actions.Shortcuts;
import com.willclay.pngconverter.converter.ImageFileFilter;
import com.willclay.pngconverter.model.ImageSession;
import com.willclay.pngconverter.ui.imagepanel.ImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Path;

public final class OpenImageAction extends ConverterAction
{
    private final Component parent;
    private final ImagePanel imagePanel;
    private final ImageSession imageSession;
    private final RecentImages recentImages;

    public OpenImageAction(
            Component parent,
            ImagePanel imagePanel,
            ImageSession imageSession,
            RecentImages recentImages)
    {
        super("Open Image…", Shortcuts.menu(KeyEvent.VK_O), "Choose an image to convert");
        this.parent = parent;
        this.imagePanel = imagePanel;
        this.imageSession = imageSession;
        this.recentImages = recentImages;
    }

    @Override
    protected void perform()
    {
        SystemFileChooser fileChooser = new SystemFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(ImageFileFilter.create());

        if (fileChooser.showOpenDialog(parent) == SystemFileChooser.APPROVE_OPTION)
        {
            open(fileChooser.getSelectedFile().toPath());
        }
    }

    public void open(Path imagePath)
    {
        try
        {
            imagePanel.showImage(imagePath);
            imageSession.openImage(imagePath);
            recentImages.add(imagePath);
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(
                    parent,
                    "Could not display the selected image:\n" + e.getMessage(),
                    "Unable to Open Image",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
