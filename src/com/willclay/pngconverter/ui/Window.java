package com.willclay.pngconverter.ui;

import com.formdev.flatlaf.util.SystemFileChooser;
import com.willclay.pngconverter.converter.ConverterManager;
import com.willclay.pngconverter.converter.ImageFileFilter;
import com.willclay.pngconverter.ui.imagepanel.ImagePanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class Window extends JFrame
{
    private Path selectedImagePath;
    private Path outputPath;

    private final ConverterManager converterManager;

    private final ImagePanel imagePanel;
    private final OptionsPanel optionsPanel;

    private final JButton selectImageButton;
    private final JButton convertButton;

    public Window()
    {
        super("Image PNG Converter");
        setLayout(new BorderLayout());

        converterManager = new ConverterManager(this);

        imagePanel = new ImagePanel(this::selectImage);
        optionsPanel = new OptionsPanel(this::selectImage, this::convert);

        selectImageButton = new JButton("Select Image to Convert");
        selectImageButton.addActionListener(e -> selectImage());

        convertButton = new JButton("Convert to PNG");
        convertButton.addActionListener(e -> convert());

        add(imagePanel, BorderLayout.CENTER);
        add(optionsPanel, BorderLayout.SOUTH);

        if (selectedImagePath == null)
        {
            selectImageButton.setVisible(false);
            convertButton.setVisible(false);
        }
    }

    private void selectImage()
    {
        SystemFileChooser fileChooser = new SystemFileChooser();
        fileChooser.addChoosableFileFilter(ImageFileFilter.create());

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION)
        {
            Path imagePath = fileChooser.getSelectedFile().toPath();

            try
            {
                imagePanel.showImage(imagePath);
                selectedImagePath = imagePath;
            }
            catch (IOException e)
            {
                JOptionPane.showMessageDialog(
                        this,
                        "Could not display the selected image:\n" + e.getMessage(),
                        "Unable to Open Image",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void convert()
    {
        if (selectedImagePath == null) return;

        String fileName = JOptionPane.showInputDialog(this, "Give your PNG a name:");
        SystemFileChooser fileChooser = new SystemFileChooser();

        fileChooser.setSelectedFile(new File(fileName + ".png"));

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION)
        {
            outputPath = fileChooser.getSelectedFile().toPath();

            converterManager.setPaths(selectedImagePath, outputPath);
            converterManager.execute();
        }

        System.out.println(selectedImagePath);
    }

    public Path getSelectedImagePath() { return selectedImagePath; }
    public Path getOutputPath() { return outputPath; }
}
