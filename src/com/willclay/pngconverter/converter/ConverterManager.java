package com.willclay.pngconverter.converter;

import com.willclay.pngconverter.ui.Window;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.nio.file.Path;

public class ConverterManager extends SwingWorker<Void, Void>
{
    private Path inputPath;
    private Path outputPath;

    private final Window window;

    public ConverterManager(Window window)
    {
        this.window = window;
    }

    public void setPaths(Path inputPath, Path outputPath)
    {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }

    @Override
    protected Void doInBackground() throws Exception
    {
        BufferedImage image = ImageIO.read(inputPath.toFile());

        if (image == null)
        {
            throw new IllegalArgumentException("Unsupported or invalid image: " + inputPath);
        }

        boolean success = ImageIO.write(image, "png", outputPath.toFile());

        if (success)
        {
            JOptionPane.showMessageDialog(null, "Image converted to PNG successfully.");
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Failed to convert image to PNG.");
        }

        return null;
    }

    @Override
    protected void done()
    {
        try
        {
            get();
            JOptionPane.showMessageDialog(
                    window,
                    "Image converted to PNG successfully.",
                    "Conversion Complete!",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(
                    window,
                    "Failed to convert image to PNG:\n"
                                + e.getCause().getMessage(),
                    "Conversion Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
