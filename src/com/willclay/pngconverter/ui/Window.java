package com.willclay.pngconverter.ui;

import com.willclay.pngconverter.converter.ImageFileFilter;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;

public class Window extends JFrame
{
    private Path selectedImagePath;

    public Window()
    {
        super("Image PNG Converter");
        setLayout(new BorderLayout());

        JPanel imagePanel = new ImagePanel();
        JPanel buttonPanel = new ButtonPanel();

        JButton selectImageButton = new JButton("Select Image to Convert");
        selectImageButton.addActionListener(e -> selectImage());

        JButton convertButton = new JButton("Convert to PNG");
        convertButton.addActionListener(e -> convert());

        add(selectImageButton, BorderLayout.NORTH);
        add(convertButton, BorderLayout.SOUTH);
    }

    private void selectImage()
    {
        JFileChooser imageChooser = new JFileChooser();
        imageChooser.setFileFilter(new ImageFileFilter());

        int result = imageChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION)
        {
            selectedImagePath = imageChooser.getSelectedFile().toPath();
        }
    }

    private void convert()
    {
        System.out.println(selectedImagePath);
    }

    public Path getSelectedImagePath() { return selectedImagePath; }
}
