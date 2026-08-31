package com.willclay.pngconverter.actions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;

public class Shortcuts
{
    /** Ctrl on Windows and Linux, Command on macOS. */
    private static final int MENU_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();

    private Shortcuts() { }

    /** e.g. Ctrl+S */
    public static KeyStroke menu(int keyCode)
    {
        return KeyStroke.getKeyStroke(keyCode, MENU_MASK);
    }

    /** e.g. Ctrl+Shift+S */
    public static KeyStroke menuShift(int keyCode)
    {
        return KeyStroke.getKeyStroke(keyCode, MENU_MASK | InputEvent.SHIFT_DOWN_MASK);
    }

    /** e.g. Ctrl+Alt+S */
    public static KeyStroke menuAlt(int keyCode)
    {
        return KeyStroke.getKeyStroke(keyCode, MENU_MASK | InputEvent.ALT_DOWN_MASK);
    }

    /** An unmodified key, e.g. F5. */
    public static KeyStroke plain(int keyCode)
    {
        return KeyStroke.getKeyStroke(keyCode, 0);
    }

    /** e.g. Alt+F4 */
    public static KeyStroke alt(int keyCode)
    {
        return KeyStroke.getKeyStroke(keyCode, InputEvent.ALT_DOWN_MASK);
    }
}
