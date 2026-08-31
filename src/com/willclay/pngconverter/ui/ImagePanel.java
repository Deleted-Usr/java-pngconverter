package com.willclay.pngconverter.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

// TODO - Use a CardLayout to show the panel even when no image is shown.

public class ImagePanel extends JPanel
{
    private JLabel label = new JLabel("No Image Selected", SwingConstants.CENTER);

    private BufferedImage image;

    public ImagePanel(Window window)
    {
        super(new BorderLayout());
        setMaximumSize(new Dimension());

        add(label, BorderLayout.CENTER);
    }

    public void showImage(Path path) throws IOException
    {
        BufferedImage image = ImageIO.read(path.toFile());

        if (image == null)
        {
            throw new IOException("Unsupported or invalid image" + path);
        }

        label.setIcon(new ImageIcon(image));


        repaint();
    }


}
