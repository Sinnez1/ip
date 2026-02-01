package pinggu.ui;

import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public class PingguGuiLauncher {
    public static void main(String[] args) {
        Application.launch(PingguGui.class, args);
    }
}
