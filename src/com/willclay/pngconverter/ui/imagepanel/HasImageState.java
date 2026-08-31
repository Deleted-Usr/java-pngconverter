package com.willclay.pngconverter.ui.imagepanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HasImageState extends JPanel
{
    private BufferedImage image;

    public void setImage(BufferedImage image)
    {
        this.image = image;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (image == null || getWidth() <= 0 || getHeight() <= 0)
        {
            return;
        }

        Graphics2D g2d = (Graphics2D) g.create();
        try
        {
            g2d.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR
            );

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
        }
        finally
        {
            g2d.dispose();
        }
    }
}
