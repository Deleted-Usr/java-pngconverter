package com.willclay.pngconverter.actions.view;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.willclay.pngconverter.actions.ConverterAction;

import javax.swing.*;
import java.awt.*;

public final class SetThemeAction extends ConverterAction
{
    private final ThemeMode theme;
    private final Component parent;

    public SetThemeAction(String name, ThemeMode theme, Component parent, boolean selected)
    {
        super(name);
        this.theme = theme;
        this.parent = parent;
        putValue(SELECTED_KEY, selected);
    }

    @Override
    protected void perform()
    {
        try
        {
            switch (theme)
            {
                case SYSTEM -> UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                case LIGHT -> UIManager.setLookAndFeel(new FlatLightLaf());
                case DARK -> UIManager.setLookAndFeel(new FlatDarkLaf());
            }
            SwingUtilities.updateComponentTreeUI(parent);
            putValue(SELECTED_KEY, true);
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(
                    parent,
                    "Could not apply the selected theme:\n" + e.getMessage(),
                    "Unable to Change Theme",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
