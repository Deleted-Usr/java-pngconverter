package com.willclay.pngconverter.ui.imagepanel;

import javax.swing.*;
import java.awt.*;

public class EmptyPanelState extends JPanel
{
    private final JButton selectImage;

    public EmptyPanelState()
    {
        super(new GridBagLayout());

        JPanel overview = new JPanel();
        overview.setLayout(new BoxLayout(overview, BoxLayout.Y_AXIS));
        overview.setOpaque(false);

        JLabel title = new JLabel("PNG Converter");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 26f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel description = new JLabel("Convert your images to PNG in three simple steps");
        description.setForeground(secondaryTextColor());
        description.setAlignmentX(Component.CENTER_ALIGNMENT);

        selectImage = new JButton("Open Image…");
        selectImage.setAlignmentX(Component.CENTER_ALIGNMENT);

        overview.add(title);
        overview.add(Box.createVerticalStrut(8));
        overview.add(description);
        overview.add(Box.createVerticalStrut(24));
        overview.add(createStepsPanel());
        overview.add(Box.createVerticalStrut(24));
        overview.add(selectImage);

        add(overview);
    }

    public void setOpenImageAction(Action openImageAction)
    {
        selectImage.setAction(openImageAction);
    }

    private static JPanel createStepsPanel()
    {
        JPanel steps = new JPanel(new GridLayout(0, 1, 0, 10));
        steps.setOpaque(false);
        steps.setAlignmentX(Component.CENTER_ALIGNMENT);

        steps.add(createStep("1", "Select an image from your computer"));
        steps.add(createStep("2", "Preview the image in this window"));
        steps.add(createStep("3", "Choose Convert Image to create a PNG"));

        return steps;
    }

    private static JLabel createStep(String number, String text)
    {
        JLabel step = new JLabel("<html><b>" + number + "</b>&nbsp;&nbsp;" + text + "</html>");
        step.setForeground(secondaryTextColor());
        step.setHorizontalAlignment(SwingConstants.LEFT);
        return step;
    }

    private static Color secondaryTextColor()
    {
        Color color = UIManager.getColor("Label.disabledForeground");
        return color != null ? color : UIManager.getColor("Label.foreground");
    }
}
