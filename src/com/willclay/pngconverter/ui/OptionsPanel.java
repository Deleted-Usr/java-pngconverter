package com.willclay.pngconverter.ui;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.util.function.Consumer;

public class OptionsPanel extends JPanel
{
    private final JButton selectImage;
    private final JButton convertImage;

    public OptionsPanel(Runnable selectAction, Consumer<Path> convertAction, Path selectedImagePath)
    {
        super();
        setLayout(new FlowLayout(FlowLayout.CENTER, 10 ,0));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        selectImage = new JButton("Select Image");
        convertImage = new JButton("Convert Image");

        selectImage.addActionListener(e -> selectAction.run());
        convertImage.addActionListener(e -> convertAction.accept(selectedImagePath));

        add(selectImage);
        add(convertImage);
    }
}
