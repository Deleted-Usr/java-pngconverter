package com.willclay.pngconverter.actions.file;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

/// Maintains a most-recently-used list of image paths across application launches.
///
/// Paths are normalized, deduplicated, and capped at ten entries before being saved
/// with [Preferences]. Malformed stored paths are ignored during startup.
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

    /// Returns a read-only snapshot ordered from newest to oldest.
    public List<Path> getPaths()
    {
        return List.copyOf(paths);
    }

    /// Moves a path to the front of the history and persists the updated list.
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

    /// Restores consecutive preference entries until the first unused slot.
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

    /// Writes current paths and removes stale entries left by a previously longer list.
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
