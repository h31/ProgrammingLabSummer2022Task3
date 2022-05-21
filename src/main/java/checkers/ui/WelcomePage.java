package checkers.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;

import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


import static checkers.logic.Listeners.start;

public class WelcomePage {
    private static final Pane pane = new Pane();
    private static final Button startButton = new Button("START");
    private static final VBox vbox = new VBox();

    public static Parent startPage(Stage stage){
        pane.setStyle("-fx-background-color: #e37d17");
        Label label1 = new Label("Добро пожаловать!");
        Label label2 = new Label("Для начала нажмите START");
        label1.setFont(Font.font("Times New Roman", FontWeight.BOLD, 36));
        label2.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));

        label1.setPadding(new Insets(0,5,0,5));
        vbox.setAlignment(Pos.BASELINE_CENTER);
        vbox.getChildren().addAll(label1,label2);
        label1.setAlignment(Pos.CENTER);
        label2.setAlignment(Pos.CENTER);
        vbox.setSpacing(40);
        Region spacer = new Region();
        spacer.setPrefSize(100,100);

        vbox.getChildren().add(spacer);

        vbox.getChildren().add(startButton);
        startButton.setAlignment(Pos.BOTTOM_CENTER);






        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, start(stage));
        startButton.setFont(Font.font("Times New Roman", FontWeight.BOLD, 12));



        pane.setPrefSize(320,350);
        pane.getChildren().add(vbox);




        return pane;
    }
}
