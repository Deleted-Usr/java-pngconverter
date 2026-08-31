package com.willclay.pngconverter.ui;

import javax.swing.*;
import java.awt.*;

/// Displays the actions available after an image has been opened.
///
/// The main [Window] hides this panel while no image is selected.
public class OptionsPanel extends JPanel
{
    private final JButton selectImage;
    private final JButton convertImage;

    public OptionsPanel(Action openImageAction, Action exportPngAction)
    {
        super();
        setLayout(new FlowLayout(FlowLayout.CENTER, 10 ,0));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        selectImage = new JButton(openImageAction);
        selectImage.setText("Choose Another");
        convertImage = new JButton(exportPngAction);
        convertImage.setText("Export as PNG…");

        add(selectImage);
        add(convertImage);
    }
}
