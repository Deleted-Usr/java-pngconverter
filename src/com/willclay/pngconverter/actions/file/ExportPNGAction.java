package com.willclay.pngconverter.actions.file;

import com.formdev.flatlaf.util.SystemFileChooser;
import com.willclay.pngconverter.actions.ImageDependentAction;
import com.willclay.pngconverter.actions.Shortcuts;
import com.willclay.pngconverter.converter.ConverterManager;
import com.willclay.pngconverter.model.ImageSession;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Path;
import java.util.Locale;

public final class ExportPNGAction extends ImageDependentAction
{
    private final Component parent;
    private final ImageSession imageSession;

    public ExportPNGAction(Component parent, ImageSession imageSession)
    {
        super(
                "Export as PNG…",
                Shortcuts.menu(KeyEvent.VK_ENTER),
                "Convert and save the current image as a PNG",
                imageSession
        );
        this.parent = parent;
        this.imageSession = imageSession;
    }

    @Override
    protected void perform()
    {
        Path selectedImagePath = imageSession.getSelectedImagePath();
        if (selectedImagePath == null) return;

        SystemFileChooser fileChooser = new SystemFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(
                new SystemFileChooser.FileNameExtensionFilter("PNG image (*.png)", "png")
        );
        fileChooser.setSelectedFile(suggestedOutputFile(selectedImagePath));

        if (fileChooser.showSaveDialog(parent) == SystemFileChooser.APPROVE_OPTION)
        {
            Path outputPath = ensurePngExtension(fileChooser.getSelectedFile().toPath());
            imageSession.setOutputPath(outputPath);

            ConverterManager converterManager = new ConverterManager(parent);
            converterManager.setPaths(selectedImagePath, outputPath);
            converterManager.execute();
        }
    }

    private static File suggestedOutputFile(Path selectedImagePath)
    {
        String inputName = selectedImagePath.getFileName().toString();
        int extensionIndex = inputName.lastIndexOf('.');
        String baseName = extensionIndex > 0 ? inputName.substring(0, extensionIndex) : inputName;

        Path parent = selectedImagePath.getParent();
        Path suggestion = Path.of(baseName + ".png");
        return (parent == null ? suggestion : parent.resolve(suggestion)).toFile();
    }

    private static Path ensurePngExtension(Path path)
    {
        String fileName = path.getFileName().toString();
        if (fileName.toLowerCase(Locale.ROOT).endsWith(".png")) return path;

        return path.resolveSibling(fileName + ".png");
    }
}
