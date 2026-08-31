package com.willclay.pngconverter.ui.imagepanel;

import com.willclay.pngconverter.actions.view.PreviewBackground;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

public class ImagePanel extends JPanel
{
    private static final String EMPTY_STATE = "empty";
    private static final String IMAGE_STATE = "image";

    private final CardLayout cardLayout;
    private final EmptyPanelState emptyPanelState;
    private final HasImageState hasImageState;

    public ImagePanel()
    {
        cardLayout = new CardLayout();
        emptyPanelState = new EmptyPanelState();
        hasImageState = new HasImageState();

        setLayout(cardLayout);
        setPreferredSize(new Dimension(0, 400));

        add(emptyPanelState, EMPTY_STATE);
        add(hasImageState, IMAGE_STATE);

        cardLayout.show(this, EMPTY_STATE);
    }

    public void setOpenImageAction(Action openImageAction)
    {
        emptyPanelState.setOpenImageAction(openImageAction);
    }

    public void showImage(Path path) throws IOException
    {
        BufferedImage image = ImageIO.read(path.toFile());

        if (image == null)
        {
            throw new IOException("Unsupported or invalid image: " + path);
        }

        hasImageState.setImage(image);
        cardLayout.show(this, IMAGE_STATE);
    }

    public void clearImage()
    {
        hasImageState.clearImage();
        cardLayout.show(this, EMPTY_STATE);
    }

    public void zoomIn() { hasImageState.zoomIn(); }
    public void zoomOut() { hasImageState.zoomOut(); }
    public void showActualSize() { hasImageState.showActualSize(); }
    public void fitToWindow() { hasImageState.fitToWindow(); }

    public void setPreviewBackground(PreviewBackground background)
    {
        hasImageState.setPreviewBackground(background);
    }
}
