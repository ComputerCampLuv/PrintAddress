import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by compu on 03/06/2018.
 */
public class Notification {

    public static void displayNotification(String title, String message) {

        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setMinWidth(350);
        stage.initModality(Modality.APPLICATION_MODAL);

        Label label = new Label(message);
        Button button = new Button("Close");
        button.setOnAction(event -> stage.close());

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, button);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
    }
}
