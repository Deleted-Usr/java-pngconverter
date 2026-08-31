package com.willclay.pngconverter.actions;

import com.willclay.pngconverter.model.ImageSession;

import javax.swing.*;

public abstract class ImageDependentAction extends ConverterAction
{
    protected ImageDependentAction(
            String name,
            KeyStroke shortcut,
            String tooltip,
            ImageSession imageSession)
    {
        super(name, shortcut, tooltip);

        setEnabled(imageSession.hasImage());
        imageSession.addPropertyChangeListener(
                ImageSession.SELECTED_IMAGE_PROPERTY,
                event -> setEnabled(event.getNewValue() != null)
        );
    }
}
