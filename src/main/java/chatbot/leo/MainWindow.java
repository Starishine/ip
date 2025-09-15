package chatbot.leo;

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

    private Leo leo;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/userImage.png"));
    private Image leoImage = new Image(this.getClass().getResourceAsStream("/images/leoImage.png"));

    /**
     * Initialises the chatbot with welcome message
     */
    @FXML
    public void initialize() {
        userInput.clear();
        String welcomeMessage = "Hello, I'm Leo, your favorite chatbot!\n"
                + "What can I do for you today?";
        dialogContainer.getChildren().add(DialogBox.getLeoDialog(welcomeMessage, leoImage));
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Leo instance */
    public void setLeo(Leo l) {
        this.leo = l;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();

        if (input.equalsIgnoreCase("bye")) {
            String exitMsg = "Bye ! Hope to see you soon!";
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getLeoDialog(exitMsg, leoImage)
            );
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> {
                Platform.exit();
            });
            pause.play();
        } else {
            String response = leo.start(input);
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getLeoDialog(response, leoImage)
            );
        }

        userInput.clear();
    }
}
