package com.willclay.pngconverter.ui.imagepanel;

import com.willclay.pngconverter.actions.view.PreviewBackground;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/// Renders the selected image with zooming and configurable preview backgrounds.
///
/// The image is centered at either an explicit scale or the largest scale that fits
/// the whole image inside the panel.
public class HasImageState extends JPanel
{
    private static final double ZOOM_STEP = 1.25;
    private static final double MIN_SCALE = 0.05;
    private static final double MAX_SCALE = 16.0;
    private static final int CHECKER_SIZE = 12;

    private BufferedImage image;
    private PreviewBackground previewBackground = PreviewBackground.DARK;
    private boolean fitToWindow = true;
    private double scale = 1.0;

    public void setImage(BufferedImage image)
    {
        this.image = image;
        fitToWindow = true;
        repaint();
    }

    public void clearImage()
    {
        image = null;
        fitToWindow = true;
        scale = 1.0;
        repaint();
    }

    public void zoomIn()
    {
        scale = Math.min(currentScale() * ZOOM_STEP, MAX_SCALE);
        fitToWindow = false;
        repaint();
    }

    public void zoomOut()
    {
        scale = Math.max(currentScale() / ZOOM_STEP, MIN_SCALE);
        fitToWindow = false;
        repaint();
    }

    public void showActualSize()
    {
        scale = 1.0;
        fitToWindow = false;
        repaint();
    }

    public void fitToWindow()
    {
        fitToWindow = true;
        repaint();
    }

    public void setPreviewBackground(PreviewBackground previewBackground)
    {
        this.previewBackground = previewBackground;
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
            paintPreviewBackground(g2d);
            g2d.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR
            );

            int availableWidth = getWidth();
            int availableHeight = getHeight();
            double renderedScale = currentScale();

            int imageWidth = Math.max(1, (int) Math.round(image.getWidth() * renderedScale));
            int imageHeight = Math.max(1, (int) Math.round(image.getHeight() * renderedScale));

            int x = (availableWidth - imageWidth) / 2;
            int y = (availableHeight - imageHeight) / 2;

            g2d.drawImage(image, x, y, imageWidth, imageHeight, null);
        }
        finally
        {
            g2d.dispose();
        }
    }

    /// Calculates the effective scale without overwriting the user's explicit zoom level.
    private double currentScale()
    {
        if (!fitToWindow || image == null || getWidth() <= 0 || getHeight() <= 0)
        {
            return scale;
        }

        double widthRatio = (double) getWidth() / image.getWidth();
        double heightRatio = (double) getHeight() / image.getHeight();
        return Math.min(widthRatio, heightRatio);
    }

    private void paintPreviewBackground(Graphics2D graphics)
    {
        switch (previewBackground)
        {
            case DARK -> {
                graphics.setColor(new Color(37, 40, 45));
                graphics.fillRect(0, 0, getWidth(), getHeight());
            }
            case LIGHT -> {
                graphics.setColor(new Color(244, 244, 244));
                graphics.fillRect(0, 0, getWidth(), getHeight());
            }
            case CHECKERBOARD -> paintCheckerboard(graphics);
        }
    }

    /// Paints a theme-aware checkerboard that makes image transparency visible.
    private void paintCheckerboard(Graphics2D graphics)
    {
        boolean darkTheme = UIManager.getBoolean("laf.dark");
        Color first = darkTheme ? new Color(62, 65, 70) : new Color(224, 224, 224);
        Color second = darkTheme ? new Color(48, 51, 56) : new Color(245, 245, 245);

        for (int y = 0; y < getHeight(); y += CHECKER_SIZE)
        {
            for (int x = 0; x < getWidth(); x += CHECKER_SIZE)
            {
                graphics.setColor(((x / CHECKER_SIZE) + (y / CHECKER_SIZE)) % 2 == 0 ? first : second);
                graphics.fillRect(x, y, CHECKER_SIZE, CHECKER_SIZE);
            }
        }
    }
}
