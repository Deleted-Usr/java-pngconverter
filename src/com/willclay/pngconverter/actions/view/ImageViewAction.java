package com.willclay.pngconverter.actions.view;

import com.willclay.pngconverter.actions.ImageDependentAction;
import com.willclay.pngconverter.model.ImageSession;

import javax.swing.*;

/// Adapts a preview operation, such as zooming, into an image-dependent Swing action.
public final class ImageViewAction extends ImageDependentAction
{
    private final Runnable operation;

    public ImageViewAction(
            String name,
            KeyStroke shortcut,
            String tooltip,
            Runnable operation,
            ImageSession imageSession)
    {
        super(name, shortcut, tooltip, imageSession);
        this.operation = operation;
    }

    @Override
    protected void perform()
    {
        operation.run();
    }
}
