package com.willclay.pngconverter.actions.view;

import com.willclay.pngconverter.actions.ImageDependentAction;
import com.willclay.pngconverter.model.ImageSession;
import com.willclay.pngconverter.ui.imagepanel.ImagePanel;

/// Changes the background behind the image preview.
///
/// The action is disabled when no image is open and exposes Swing's selected state
/// so it can be used directly by a radio-button menu item.
public final class SetPreviewBackgroundAction extends ImageDependentAction
{
    private final PreviewBackground background;
    private final ImagePanel imagePanel;

    public SetPreviewBackgroundAction(
            String name,
            PreviewBackground background,
            ImagePanel imagePanel,
            ImageSession imageSession,
            boolean selected)
    {
        super(name, null, name + " preview background", imageSession);
        this.background = background;
        this.imagePanel = imagePanel;
        putValue(SELECTED_KEY, selected);
    }

    @Override
    protected void perform()
    {
        imagePanel.setPreviewBackground(background);
        putValue(SELECTED_KEY, true);
    }
}
