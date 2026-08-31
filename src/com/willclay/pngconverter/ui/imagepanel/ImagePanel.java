package com.willclay.pngconverter.ui.imagepanel;

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
    private final HasImageState hasImageState;

    public ImagePanel(Runnable selectImageAction)
    {
        cardLayout = new CardLayout();
        hasImageState = new HasImageState();

        setLayout(cardLayout);
        setPreferredSize(new Dimension(0, 400));

        add(new EmptyPanelState(selectImageAction), EMPTY_STATE);
        add(hasImageState, IMAGE_STATE);

        cardLayout.show(this, EMPTY_STATE);
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
}
