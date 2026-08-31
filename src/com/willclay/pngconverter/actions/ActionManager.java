package com.willclay.pngconverter.actions;

import com.willclay.pngconverter.actions.file.CloseImageAction;
import com.willclay.pngconverter.actions.file.ExitAction;
import com.willclay.pngconverter.actions.file.ExportPNGAction;
import com.willclay.pngconverter.actions.file.OpenImageAction;
import com.willclay.pngconverter.actions.file.OpenRecentAction;
import com.willclay.pngconverter.actions.file.RecentImages;
import com.willclay.pngconverter.actions.help.HelpAction;
import com.willclay.pngconverter.actions.view.ImageViewAction;
import com.willclay.pngconverter.actions.view.PreviewBackground;
import com.willclay.pngconverter.actions.view.SetPreviewBackgroundAction;
import com.willclay.pngconverter.actions.view.SetThemeAction;
import com.willclay.pngconverter.actions.view.ThemeMode;
import com.willclay.pngconverter.model.ImageSession;
import com.willclay.pngconverter.ui.imagepanel.ImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public final class ActionManager
{
    private final RecentImages recentImages = new RecentImages();

    private final OpenImageAction openImageAction;
    private final ExportPNGAction exportPngAction;
    private final CloseImageAction closeImageAction;
    private final ExitAction exitAction;

    private final ImageViewAction zoomInAction;
    private final ImageViewAction zoomOutAction;
    private final ImageViewAction actualSizeAction;
    private final ImageViewAction fitToWindowAction;
    private final List<SetPreviewBackgroundAction> previewBackgroundActions;
    private final List<SetThemeAction> themeActions;

    private final HelpAction supportedFormatsAction;
    private final HelpAction aboutAction;

    public ActionManager(Window window, ImagePanel imagePanel, ImageSession imageSession)
    {
        openImageAction = new OpenImageAction(window, imagePanel, imageSession, recentImages);
        exportPngAction = new ExportPNGAction(window, imageSession);
        closeImageAction = new CloseImageAction(imagePanel, imageSession);
        exitAction = new ExitAction(window);

        zoomInAction = new ImageViewAction(
                "Zoom In",
                Shortcuts.menu(KeyEvent.VK_PLUS),
                "Increase the preview size",
                imagePanel::zoomIn,
                imageSession
        );
        zoomOutAction = new ImageViewAction(
                "Zoom Out",
                Shortcuts.menu(KeyEvent.VK_MINUS),
                "Decrease the preview size",
                imagePanel::zoomOut,
                imageSession
        );
        actualSizeAction = new ImageViewAction(
                "Actual Size",
                Shortcuts.menu(KeyEvent.VK_0),
                "Show the image at 100%",
                imagePanel::showActualSize,
                imageSession
        );
        fitToWindowAction = new ImageViewAction(
                "Fit to Window",
                Shortcuts.menuShift(KeyEvent.VK_0),
                "Fit the complete image in the preview",
                imagePanel::fitToWindow,
                imageSession
        );

        previewBackgroundActions = List.of(
                new SetPreviewBackgroundAction(
                        "Dark", PreviewBackground.DARK, imagePanel, imageSession, true),
                new SetPreviewBackgroundAction(
                        "Light", PreviewBackground.LIGHT, imagePanel, imageSession, false),
                new SetPreviewBackgroundAction(
                        "Checkerboard", PreviewBackground.CHECKERBOARD, imagePanel, imageSession, false)
        );
        themeActions = List.of(
                new SetThemeAction("System", ThemeMode.SYSTEM, window, false),
                new SetThemeAction("Light", ThemeMode.LIGHT, window, false),
                new SetThemeAction("Dark", ThemeMode.DARK, window, true)
        );

        supportedFormatsAction = new HelpAction(
                "Supported Formats",
                window,
                "PNG Converter can open JPG, JPEG, GIF, BMP, and WebP images.\n"
                        + "All exported images are saved in PNG format."
        );
        aboutAction = new HelpAction(
                "About PNG Converter",
                window,
                "PNG Converter\nA simple desktop utility for converting images to PNG."
        );
    }

    public Action getOpenImageAction() { return openImageAction; }
    public Action getExportPngAction() { return exportPngAction; }
    public Action getCloseImageAction() { return closeImageAction; }
    public Action getExitAction() { return exitAction; }
    public Action getZoomInAction() { return zoomInAction; }
    public Action getZoomOutAction() { return zoomOutAction; }
    public Action getActualSizeAction() { return actualSizeAction; }
    public Action getFitToWindowAction() { return fitToWindowAction; }
    public List<SetPreviewBackgroundAction> getPreviewBackgroundActions() { return previewBackgroundActions; }
    public List<SetThemeAction> getThemeActions() { return themeActions; }
    public Action getSupportedFormatsAction() { return supportedFormatsAction; }
    public Action getAboutAction() { return aboutAction; }

    public List<OpenRecentAction> getOpenRecentActions()
    {
        List<OpenRecentAction> actions = new ArrayList<>();
        for (var path : recentImages.getPaths())
        {
            actions.add(new OpenRecentAction(path, openImageAction));
        }
        return List.copyOf(actions);
    }
}
