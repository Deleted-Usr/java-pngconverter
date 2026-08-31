package com.willclay.pngconverter.converter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Path;

/// Converts one image to PNG away from Swing's event-dispatch thread.
///
/// Configure the paths before starting the worker:
///
/// ```java
/// ConverterManager converter = new ConverterManager(parentComponent);
/// converter.setPaths(inputPath, outputPath);
/// converter.execute();
/// ```
///
/// Completion and failure messages are displayed on the event-dispatch thread by [#done()].
public class ConverterManager extends SwingWorker<Void, Void>
{
    private Path inputPath;
    private Path outputPath;

    private final Component dialogParent;

    public ConverterManager(Component dialogParent)
    {
        this.dialogParent = dialogParent;
    }

    /// Sets the source image and destination PNG used by the next execution.
    ///
    /// @param inputPath image to decode
    /// @param outputPath PNG file to write
    public void setPaths(Path inputPath, Path outputPath)
    {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }

    /// Reads and encodes the image on this worker's background thread.
    @Override
    protected Void doInBackground() throws Exception
    {
        BufferedImage image = ImageIO.read(inputPath.toFile());

        if (image == null)
        {
            throw new IllegalArgumentException("Unsupported or invalid image: " + inputPath);
        }

        if (!ImageIO.write(image, "png", outputPath.toFile()))
        {
            throw new IllegalStateException("No PNG writer is available.");
        }

        return null;
    }

    /// Reports the worker's result after Swing returns execution to the event-dispatch thread.
    @Override
    protected void done()
    {
        try
        {
            get();
            JOptionPane.showMessageDialog(
                    dialogParent,
                    "Image converted to PNG successfully.",
                    "Conversion Complete!",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
        catch (Exception e)
        {
            Throwable cause = e.getCause() == null ? e : e.getCause();
            JOptionPane.showMessageDialog(
                    dialogParent,
                    "Failed to convert image to PNG:\n"
                                + cause.getMessage(),
                    "Conversion Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
