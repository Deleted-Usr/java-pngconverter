package com.willclay.pngconverter.converter;

import com.formdev.flatlaf.util.SystemFileChooser;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/// Creates the file-chooser filter for image formats that PNG Converter can import.
public class ImageFileFilter
{
    private ImageFileFilter() {}

    /// Creates a fresh filter for JPEG, GIF, BMP, and WebP files.
    ///
    /// @return an image-extension filter suitable for [SystemFileChooser]
    public static SystemFileChooser.FileFilter create()
    {
        return new SystemFileChooser.FileNameExtensionFilter(
                "Images (*.jpg, *.jpeg, *.gif, *.bmp, *.webp)",
                "jpg", "jpeg", "gif", "bmp", "webp"
        );
    }
}
