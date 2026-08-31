package com.willclay.pngconverter.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ImagePanel extends JPanel
{
    private JLabel label = new JLabel();

    public ImagePanel(Window window)
    {
        super(new BorderLayout());

        add(label, BorderLayout.CENTER);
    }

    public void showImage(Path path) throws IOException
    {
        String pathString = path.toString();

        BufferedImage image = ImageIO.read(new File(pathString));
        label.setIcon(new ImageIcon(image));

        repaint();
    }
}
