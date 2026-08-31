package com.willclay.pngconverter;

import com.formdev.flatlaf.FlatDarkLaf;
import com.twelvemonkeys.imageio.plugins.webp.WebPImageReaderSpi;
import com.willclay.pngconverter.ui.Window;

import javax.imageio.spi.IIORegistry;
import javax.swing.*;

public class Main
{
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

    private static void registerImageReaders()
    {
        IIORegistry registry = IIORegistry.getDefaultInstance();

        if (registry.getServiceProviderByClass(WebPImageReaderSpi.class) == null)
        {
            registry.registerServiceProvider(new WebPImageReaderSpi());
        }
    }

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
