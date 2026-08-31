package com.willclay.pngconverter.converter;

import com.formdev.flatlaf.util.SystemFileChooser;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class ImageFileFilter
{
    private ImageFileFilter() {}

    public static SystemFileChooser.FileFilter create()
    {
        return new SystemFileChooser.FileNameExtensionFilter(
                "Images (*.jpg, *.jpeg, *.gif, *.bmp, *.webp)",
                "jpg", "jpeg", "gif", "bmp", "webp"
        );
    }
}
