package com.willclay.pngconverter.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

// TODO - Use a CardLayout to show the panel even when no image is shown.

public class ImagePanel extends JPanel
{
    private BufferedImage image;

    public ImagePanel(Window window)
    {
        setPreferredSize(new Dimension(0, 400));
        //setBackground(Color.white);
    }

    public void showImage(Path path) throws IOException
    {
        BufferedImage image = ImageIO.read(path.toFile());

        if (image == null)
        {
            throw new IOException("Unsupported or invalid image" + path);
        }

        this.image = image;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR
        );

        if (image == null)
        {
            paintPlaceHolder(g2d);
            return;
        }

        int availableWidth = getWidth();
        int availableHeight = getHeight();

        double widthRatio = (double) availableWidth / image.getWidth();
        double heightRatio = (double) availableHeight / image.getHeight();

        double scale = Math.min(widthRatio, heightRatio);

        int imageWidth = (int) (image.getWidth() * scale);
        int imageHeight = (int) (image.getHeight() * scale);

        int x = (availableWidth - imageWidth) / 2;
        int y = (availableHeight - imageHeight) / 2;

        g2d.drawImage(image, x, y, imageWidth, imageHeight, null);
        g2d.dispose();
    }

    private void paintPlaceHolder(Graphics2D g2d)
    {
        String text = "No Image Selected";
        FontMetrics fm = g2d.getFontMetrics();

        int x = (getWidth() - fm.stringWidth(text)) / 2;
        int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

        g2d.setColor(UIManager.getColor("Label.foreground"));
        g2d.drawString(text, x, y);
    }
}
