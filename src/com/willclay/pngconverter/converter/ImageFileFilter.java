package com.willclay.pngconverter.converter;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class ImageFileFilter extends FileFilter
{
    @Override
    public boolean accept(File f)
    {
        if (f.isDirectory())
        {
            return true;
        }

        String name = f.getName().toLowerCase();
        return (name.endsWith(".jpg") ||
                name.endsWith(".jpeg") ||
                name.endsWith(".gif") ||
                name.endsWith(".bmp")) && !name.endsWith(".png");
    }

    @Override
    public String getDescription()
    {
        return "Images (*.jpg, *.jpeg, *.gif, *.bmp)";
    }
}
