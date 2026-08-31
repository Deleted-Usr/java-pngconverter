package com.willclay.pngconverter.actions.file;

import com.willclay.pngconverter.actions.ConverterAction;

import java.nio.file.Path;

public final class OpenRecentAction extends ConverterAction
{
    private final Path path;
    private final OpenImageAction openImageAction;

    public OpenRecentAction(Path path, OpenImageAction openImageAction)
    {
        super(displayName(path), null, path.toString());
        this.path = path;
        this.openImageAction = openImageAction;
    }

    @Override
    protected void perform()
    {
        openImageAction.open(path);
    }

    private static String displayName(Path path)
    {
        Path fileName = path.getFileName();
        return fileName == null ? path.toString() : fileName.toString();
    }
}
