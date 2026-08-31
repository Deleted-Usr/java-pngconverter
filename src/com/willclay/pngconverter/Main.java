package com.willclay.pngconverter;

import com.willclay.pngconverter.ui.Window;

import javax.swing.*;

// TODO - Display the chosed image

public class Main
{
    public static void main(String[] args)
    {
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

    private static void setLookAndFeel()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception _)
        {

        }
    }
}