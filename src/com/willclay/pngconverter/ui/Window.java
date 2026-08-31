package com.willclay.pngconverter.ui;

import com.willclay.pngconverter.converter.ImageFileFilter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;

public class Window extends JFrame
{
    private Path selectedImagePath;

    private final ImagePanel imagePanel;
    private final JPanel buttonPanel;

    public Window()
    {
        super("Image PNG Converter");
        setLayout(new BorderLayout());

        imagePanel = new ImagePanel(this);
        buttonPanel = new ButtonPanel();

        JButton selectImageButton = new JButton("Select Image to Convert");
        selectImageButton.addActionListener(e -> selectImage());

        JButton convertButton = new JButton("Convert to PNG");
        convertButton.addActionListener(e -> convert());

        add(imagePanel, BorderLayout.NORTH);

        add(selectImageButton, BorderLayout.CENTER);
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

            try
            {
                imagePanel.showImage(selectedImagePath);
            }
            catch (IOException _)
            {

            }
        }
    }

    private void convert()
    {
        System.out.println(selectedImagePath);
    }

    public Path getSelectedImagePath() { return selectedImagePath; }
}
