package com.willclay.pngconverter.model;

import com.willclay.pngconverter.ui.imagepanel.ImagePanel;
import com.willclay.pngconverter.ui.Window;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.nio.file.Path;

/// The currently open image and how the {@link ImagePanel} handles it.
///
/// This classes uses PropertyChangeListeners across the project that listen
/// for changes on either of the two static String properites of the class.
/// classes can register change listeners that call methods when a `firePropertyChange`
/// is called.
///
/// For example: in {@link Window}, a `PropertyChangeListener` is registered with the
/// [SELECTED_IMAGE_PROPERTY]
///
/// ```java
/// imageSession.addPropertyChangeListener(
///         ImageSession.SELECTED_IMAGE_PROPERTY,
///         event -> updateImageState()
/// );
/// ```
///
/// The method `updateImageState()` will be called when `firePropertyChange` is called in
/// this class.
public final class ImageSession
{
    public static final String SELECTED_IMAGE_PROPERTY = "selectedImage";
    public static final String OUTPUT_PATH_PROPERTY = "outputPath";

    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);

    private Path selectedImagePath;
    private Path outputPath;

    public Path getSelectedImagePath()
    {
        return selectedImagePath;
    }
    public Path getOutputPath()
    {
        return outputPath;
    }

    public boolean hasImage()
    {
        return selectedImagePath != null;
    }

    public void openImage(Path path)
    {
        Path normalizedPath = path.toAbsolutePath().normalize();
        Path oldImagePath = selectedImagePath;

        setOutputPath(null);
        selectedImagePath = normalizedPath;
        changes.firePropertyChange(SELECTED_IMAGE_PROPERTY, oldImagePath, selectedImagePath);
    }

    public void closeImage()
    {
        Path oldImagePath = selectedImagePath;

        setOutputPath(null);
        selectedImagePath = null;
        changes.firePropertyChange(SELECTED_IMAGE_PROPERTY, oldImagePath, null);
    }

    public void setOutputPath(Path path)
    {
        Path oldOutputPath = outputPath;
        outputPath = path == null ? null : path.toAbsolutePath().normalize();
        changes.firePropertyChange(OUTPUT_PATH_PROPERTY, oldOutputPath, outputPath);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
    {
        changes.addPropertyChangeListener(propertyName, listener);
    }
}
