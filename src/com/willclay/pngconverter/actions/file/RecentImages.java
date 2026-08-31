package com.willclay.pngconverter.actions.file;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public final class RecentImages
{
    private static final int MAX_RECENT_IMAGES = 10;
    private static final String RECENT_IMAGE_KEY = "recentImage.";

    private final Preferences preferences = Preferences.userRoot().node("/com/willclay/pngconverter/actions");
    private final List<Path> paths = new ArrayList<>();

    public RecentImages()
    {
        load();
    }

    public List<Path> getPaths()
    {
        return List.copyOf(paths);
    }

    public void add(Path path)
    {
        Path normalizedPath = path.toAbsolutePath().normalize();
        paths.remove(normalizedPath);
        paths.addFirst(normalizedPath);

        if (paths.size() > MAX_RECENT_IMAGES)
        {
            paths.removeLast();
        }

        save();
    }

    private void load()
    {
        for (int index = 0; index < MAX_RECENT_IMAGES; index++)
        {
            String value = preferences.get(RECENT_IMAGE_KEY + index, null);
            if (value == null) break;

            try
            {
                paths.add(Path.of(value));
            }
            catch (RuntimeException ignored)
            {
                // Ignore malformed entries instead of preventing application startup.
            }
        }
    }

    private void save()
    {
        for (int index = 0; index < MAX_RECENT_IMAGES; index++)
        {
            if (index < paths.size())
            {
                preferences.put(RECENT_IMAGE_KEY + index, paths.get(index).toString());
            }
            else
            {
                preferences.remove(RECENT_IMAGE_KEY + index);
            }
        }
    }
}
