package b2;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for B2 using FXML.
 */
public class Main extends Application {
    private static final String FILE_PATH = "data/tasks.txt";

    private B2SuperBattleDroid b2 = new B2SuperBattleDroid(FILE_PATH);

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);

            stage.setMinHeight(220);
            stage.setMinWidth(417);

            fxmlLoader.<MainWindow>getController().setB2(b2);  // inject the B2 instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
