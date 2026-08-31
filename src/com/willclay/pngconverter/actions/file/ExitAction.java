package com.willclay.pngconverter.actions.file;

import com.willclay.pngconverter.actions.ConverterAction;
import com.willclay.pngconverter.actions.Shortcuts;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public final class ExitAction extends ConverterAction
{
    private final Window window;

    public ExitAction(Window window)
    {
        super("Exit", Shortcuts.alt(KeyEvent.VK_F4), "Exit PNG Converter");
        this.window = window;
    }

    @Override
    protected void perform()
    {
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
    }
}
