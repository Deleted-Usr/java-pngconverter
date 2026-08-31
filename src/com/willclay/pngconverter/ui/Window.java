package com.willclay.pngconverter.ui;

import com.formdev.flatlaf.util.SystemFileChooser;
import com.willclay.pngconverter.converter.ConverterManager;
import com.willclay.pngconverter.converter.ImageFileFilter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;

public class Window extends JFrame
{
    private Path selectedImagePath;

    private final ConverterManager converterManager;

    private final ImagePanel imagePanel;
    private final OptionsPanel optionsPanel;

    public Window()
    {
        super("Image PNG Converter");
        setLayout(new BorderLayout());

        converterManager = new ConverterManager();

        imagePanel = new ImagePanel(this);
        optionsPanel = new OptionsPanel(this::selectImage, converterManager::convert, selectedImagePath);

        JButton selectImageButton = new JButton("Select Image to Convert");
        selectImageButton.addActionListener(e -> selectImage());

        JButton convertButton = new JButton("Convert to PNG");
        convertButton.addActionListener(e -> convert());

        add(imagePanel, BorderLayout.NORTH);
        add(optionsPanel, BorderLayout.SOUTH);

        //add(selectImageButton, BorderLayout.CENTER);
        //add(convertButton, BorderLayout.SOUTH);
    }

    private void selectImage()
    {
        SystemFileChooser fileChooser = new SystemFileChooser();
        fileChooser.addChoosableFileFilter(ImageFileFilter.create());

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION)
        {
            selectedImagePath = fileChooser.getSelectedFile().toPath();

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
