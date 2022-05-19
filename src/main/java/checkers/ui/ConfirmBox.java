package checkers.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {

    static boolean answer;

    public static boolean confirmation(String title, String message) {

        Stage window = new Stage();
        window.getIcons().add(ContentCreator.getIcon());
        window.initModality(Modality.APPLICATION_MODAL);//Нельзя использовать приложения,
        // пока не подтвердил/отклонил сдачу
        window.setTitle(title);
        window.setMinWidth(175);
        window.setMinHeight(75);

        Label label = new Label(message);
        label.setAlignment(Pos.BASELINE_CENTER);
        label.setStyle("-fx-text-fill: black; -fx-font-size: 16pt; -fx-font-weight: bold; -fx-text-color: black");

        Button yesButton = new Button("Да");
        yesButton.setStyle("-fx-text-fill: black; -fx-border-color:green; -fx-font-size: 10pt; -fx-font-weight: bold; -fx-text-fill: green");
        yesButton.setMinSize(30,30);

        Button noButton = new Button("Нет");
        noButton.setStyle("-fx-text-fill: black; -fx-border-color:red; -fx-font-size: 10pt; -fx-font-weight: bold; -fx-text-fill: red");
        noButton.setMinSize(30,30);


        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });
        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });


        VBox layout = new VBox(10);
        HBox buttons = new HBox(10);
        buttons.setSpacing(15);
        buttons.setAlignment(Pos.BOTTOM_CENTER);
        buttons.getChildren().addAll(yesButton, noButton);
        layout.getChildren().addAll(label, buttons);
        layout.setAlignment(Pos.CENTER);


        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        return answer;


    }
}
