package com.willclay.pngconverter.actions.help;

import com.willclay.pngconverter.actions.ConverterAction;

import javax.swing.*;
import java.awt.*;

public final class HelpAction extends ConverterAction
{
    private final Component parent;
    private final String message;

    public HelpAction(String name, Component parent, String message)
    {
        super(name);
        this.parent = parent;
        this.message = message;
    }

    @Override
    protected void perform()
    {
        JOptionPane.showMessageDialog(
                parent,
                message,
                getName(),
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
