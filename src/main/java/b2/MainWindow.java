package b2;

import java.util.Objects;

import b2.ui.Ui;
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

    private B2SuperBattleDroid b2;
    private Ui ui = new Ui();

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/mort.png"));
    private Image b2Image = new Image(this.getClass().getResourceAsStream("/images/b2.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        dialogContainer.getChildren().add(
                DialogBox.getB2Dialog("Hello I'm B2 Super Battle Droid!\nWhat can I do for you?\n Type \"command\" "
                        + "to see a list of available commands!", b2Image)
        );
    }

    /** Injects the B2 instance */
    public void setB2(B2SuperBattleDroid b2) {
        this.b2 = b2;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing B2's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = b2.getResponse(input);
        boolean isError = b2.getLastResponseWasError();

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                isError ? DialogBox.getErrorDialog(response, b2Image) : DialogBox.getB2Dialog(response, b2Image)
        );

        if (Objects.equals(response, ui.printExit())) {
            userInput.setDisable(true);
            sendButton.setDisable(true);

            PauseTransition delay = new PauseTransition(Duration.seconds(2));

            delay.setOnFinished(event -> Platform.exit());

            delay.play();
        }

        userInput.clear();
    }
}

