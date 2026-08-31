package com.willclay.pngconverter.actions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;

/// Creates Swing keyboard shortcuts with platform-appropriate modifier keys.
///
/// Use these helpers instead of hard-coding Control so shortcuts use Command on macOS:
///
/// ```java
/// KeyStroke openShortcut = Shortcuts.menu(KeyEvent.VK_O);
/// KeyStroke fitShortcut = Shortcuts.menuShift(KeyEvent.VK_0);
/// ```
public class Shortcuts
{
    /// Control on Windows and Linux, Command on macOS.
    private static final int MENU_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();

    private Shortcuts() { }

    /// Creates a shortcut using the platform's menu modifier, such as Control+S.
    public static KeyStroke menu(int keyCode)
    {
        return KeyStroke.getKeyStroke(keyCode, MENU_MASK);
    }

    /// Creates a menu-modifier shortcut that also requires Shift.
    public static KeyStroke menuShift(int keyCode)
    {
        return KeyStroke.getKeyStroke(keyCode, MENU_MASK | InputEvent.SHIFT_DOWN_MASK);
    }

    /// Creates a menu-modifier shortcut that also requires Alt.
    public static KeyStroke menuAlt(int keyCode)
    {
        return KeyStroke.getKeyStroke(keyCode, MENU_MASK | InputEvent.ALT_DOWN_MASK);
    }

    /// Creates an unmodified shortcut, such as F5.
    public static KeyStroke plain(int keyCode)
    {
        return KeyStroke.getKeyStroke(keyCode, 0);
    }

    /// Creates an Alt-modified shortcut, such as Alt+F4.
    public static KeyStroke alt(int keyCode)
    {
        return KeyStroke.getKeyStroke(keyCode, InputEvent.ALT_DOWN_MASK);
    }
}
