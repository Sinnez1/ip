package pinggu.ui;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import pinggu.Pinggu;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Pinggu pinggu;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/Teemo.png"));
    private Image pingguImage = new Image(this.getClass().getResourceAsStream("/images/Pinggu.png"));

    /**
     * Initializes scrollPane.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        userInput.setPromptText("Type here...");
    }

    /**
     * Injects the Pinggu instance.
     */
    public void setPinggu(Pinggu p) {
        pinggu = p;
        if (pinggu.getHasLoadError()) {
            dialogContainer.getChildren().add(
                    DialogBox.getPingguDialog("Error loading file. Starting with empty task list.", pingguImage));
        }

        dialogContainer.getChildren().addAll(DialogBox.getPingguDialog(pinggu.getWelcomeMessage(), pingguImage));

    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply.
     * Appends them to the dialog container.
     * Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        assert pinggu != null : "Pinggu instance must exist before handling input";
        String input = userInput.getText();
        String response = pinggu.run(input);
        String commandType = pinggu.getCommandType();
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getPingguDialog(response, pingguImage, commandType)
        );
        userInput.clear();

        if (input.equals("bye")) {
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
    }

}
