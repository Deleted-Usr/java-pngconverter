package com.willclay.pngconverter.model;

import com.willclay.pngconverter.ui.Window;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.nio.file.Path;

/// Stores the currently open image and its chosen PNG output path.
///
/// Other parts of the application can listen for either property instead of directly
/// coupling themselves to the code that opens, closes, or exports an image.
///
/// For example, [Window] updates its controls whenever the selected image changes:
///
/// ```java
/// imageSession.addPropertyChangeListener(
///         ImageSession.SELECTED_IMAGE_PROPERTY,
///         event -> updateImageState()
/// );
/// ```
public final class ImageSession
{
    /// Property name fired when the selected input-image path changes.
    public static final String SELECTED_IMAGE_PROPERTY = "selectedImage";
    /// Property name fired when the chosen output path changes.
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

    /// Reports whether an input image is currently open.
    public boolean hasImage()
    {
        return selectedImagePath != null;
    }

    /// Opens a normalized absolute path and clears any output chosen for the previous image.
    ///
    /// @param path selected input-image path
    public void openImage(Path path)
    {
        Path normalizedPath = path.toAbsolutePath().normalize();
        Path oldImagePath = selectedImagePath;

        setOutputPath(null);
        selectedImagePath = normalizedPath;
        changes.firePropertyChange(SELECTED_IMAGE_PROPERTY, oldImagePath, selectedImagePath);
    }

    /// Clears both the selected input image and its output path.
    public void closeImage()
    {
        Path oldImagePath = selectedImagePath;

        setOutputPath(null);
        selectedImagePath = null;
        changes.firePropertyChange(SELECTED_IMAGE_PROPERTY, oldImagePath, null);
    }

    /// Stores a normalized absolute output path, or clears it when passed `null`.
    public void setOutputPath(Path path)
    {
        Path oldOutputPath = outputPath;
        outputPath = path == null ? null : path.toAbsolutePath().normalize();
        changes.firePropertyChange(OUTPUT_PATH_PROPERTY, oldOutputPath, outputPath);
    }

    /// Registers a listener for one named session property.
    ///
    /// @param propertyName one of [#SELECTED_IMAGE_PROPERTY] or [#OUTPUT_PATH_PROPERTY]
    /// @param listener callback notified after that property changes
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
    {
        changes.addPropertyChangeListener(propertyName, listener);
    }
}
