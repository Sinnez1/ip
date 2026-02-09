package pinggu.ui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the user's face
 * and a label containing text from the user.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        assert text != null : "Text cannot be null";
        assert img != null : "Image cannot be null";
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    private void changeDialogStyle(String commandType) {
        if (commandType.equals("default")) {
            return;
        }
        switch(commandType) {
        case "add":
            dialog.getStyleClass().add("add-label");
            break;
        case "MARK":
            dialog.getStyleClass().add("marked-label");
            break;
        case "DELETE":
            dialog.getStyleClass().add("delete-label");
            break;
        default:
            assert false; // should not reach this point.
        }
    }


    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    /**
     * Returns a DialogBox with given text and user image for GUI.
     *
     * @param text User input.
     * @param img User image.
     * @return DialogBox with user input and user image.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Returns a DialogBox with given text and Pinggu image for GUI.
     *
     * @param text Text to be output by Pinggu.
     * @param img Pinggu image.
     * @return DialogBox with output and Pinggu image.
     */
    public static DialogBox getPingguDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }

    /**
     * Returns DialogBox with different CSS styles for add, mark and delete commands.
     *
     * @param text Text to be output by Pinggu.
     * @param img Pinggu image.
     * @param commandType Command that is input by user.
     * @return DialogBox with different CSS styles for add, mark and delete commands.
     */
    public static DialogBox getPingguDialog(String text, Image img, String commandType) {
        var db = new DialogBox(text, img);
        db.flip();
        db.changeDialogStyle(commandType);
        return db;
    }
}
