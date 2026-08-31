package com.willclay.pngconverter;

import com.formdev.flatlaf.FlatDarkLaf;
import com.twelvemonkeys.imageio.plugins.webp.WebPImageReaderSpi;
import com.willclay.pngconverter.ui.Window;

import javax.imageio.spi.IIORegistry;
import javax.swing.*;

/// Starts PNG Converter and creates its Swing user interface.
///
/// Image readers and the look and feel are configured before the main [Window]
/// is shown on Swing's event-dispatch thread.
public class Main
{
    /// Launches the desktop application.
    ///
    /// @param args command-line arguments; currently unused
    public static void main(String[] args)
    {
        registerImageReaders();

        SwingUtilities.invokeLater(() ->
        {
            setLookAndFeel();

            Window window = new Window();
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            window.setSize(800, 600);

            window.setLocationRelativeTo(null);
            window.setVisible(true);
        });
    }

    /// Registers the WebP reader when ImageIO has not discovered it automatically.
    private static void registerImageReaders()
    {
        IIORegistry registry = IIORegistry.getDefaultInstance();

        if (registry.getServiceProviderByClass(WebPImageReaderSpi.class) == null)
        {
            registry.registerServiceProvider(new WebPImageReaderSpi());
        }
    }

    /// Applies the default dark theme, leaving Swing's current theme in place on failure.
    private static void setLookAndFeel()
    {
        try
        {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        }
        catch (Exception _)
        {

        }
    }
}
