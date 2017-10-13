package lions_and_men.util;


import javafx.scene.control.ContextMenu;

/**
 * Singleton which makes sure that there is only one context menu opened at the same time.
 */
public class ContextMenuHolder {
    private static ContextMenu singleton;


    private ContextMenuHolder() {
        // disable constructor
    }

    public static ContextMenu getFreshContextMenu() {
        if (singleton != null) {
            singleton.hide();
        }

        singleton = new ContextMenu();
        return singleton;
    }
}
