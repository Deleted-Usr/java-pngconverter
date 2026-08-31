package com.willclay.pngconverter.ui;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.util.function.BiConsumer;

public class OptionsPanel extends JPanel
{
    private final JButton selectImage;
    private final JButton convertImage;

    public OptionsPanel(Runnable selectAction, Runnable convertAction)
    {
        super();
        setLayout(new FlowLayout(FlowLayout.CENTER, 10 ,0));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        selectImage = new JButton("Select Image");
        convertImage = new JButton("Convert Image");

        selectImage.addActionListener(e -> selectAction.run());
        convertImage.addActionListener(e -> convertAction.run());

        add(selectImage);
        add(convertImage);
    }
}
