package com.willclay.pngconverter.actions;

import com.willclay.pngconverter.model.ImageSession;

import javax.swing.*;

/// Base action that is enabled only while an image is open.
///
/// The enabled state follows [ImageSession#SELECTED_IMAGE_PROPERTY], keeping menu
/// items and buttons synchronized without each action duplicating listener logic.
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
