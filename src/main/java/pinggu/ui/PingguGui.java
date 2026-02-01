package pinggu.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pinggu.Pinggu;

/**
 * A GUI for Duke using FXML.
 */
public class PingguGui extends Application {

    private Pinggu pinggu = new Pinggu(Pinggu.FILEPATH);

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(PingguGui.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setPinggu(pinggu); // inject the Pinggu instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
