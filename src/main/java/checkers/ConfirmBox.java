package checkers;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {

    static boolean answer;

    public static boolean confirmation(String title, String message) {

            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);//Нельзя использовать приложения,
            // пока не подтвердил/отклонил сдачу
            window.setTitle(title);
            window.setMinWidth(175);
            window.setMinHeight(75);

            Label label = new Label(message);

            Button yesButton = new Button("Да");
            Button noButton = new Button("Нет");

            yesButton.setOnAction(e -> {
                answer = true;
                window.close();
            });
            noButton.setOnAction(e -> {
                answer = false;
                window.close();
            });


            VBox layout = new VBox(10);
            layout.getChildren().addAll(label, yesButton, noButton);
            layout.setAlignment(Pos.CENTER);


            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
            return answer;



    }
}
