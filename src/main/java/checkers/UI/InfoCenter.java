package checkers.UI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class InfoCenter {
    private final StackPane pane;
    private final Label message;
    private final Button startGameButton;
    private PrimaryStage primaryStage;

    public InfoCenter() {
        pane = new StackPane();
        pane.setMinSize(UIConstants.APP_WIDTH, UIConstants.INFO_CENTER_HEIGHT);
        pane.setTranslateX((double) UIConstants.APP_WIDTH / 2);
        pane.setTranslateY((double) UIConstants.INFO_CENTER_HEIGHT / 2);

        message = new Label("English Checkers");
        message.setMinSize(UIConstants.APP_WIDTH, UIConstants.INFO_CENTER_HEIGHT);
        message.setFont(Font.font(24));
        message.setAlignment(Pos.CENTER);
        message.setTranslateY(-20);
        pane.getChildren().add(message);

        startGameButton = new Button("Create New Field");
        startGameButton.setMinSize(135, 30);
        startGameButton.setTranslateY(20);
        setStartButtonOnAction(createNewField());

        pane.getChildren().add(startGameButton);
    }

    public StackPane getStackPane() {
        return pane;
    }

    public void updateMessage(String message) {
        try {
            this.message.setText(message);
        } catch (NullPointerException ignored) {}
    }

    public void showStartButton() {
        startGameButton.setVisible(true);
    }

    private void hideStartButton() {
        startGameButton.setVisible(false);
    }

    private void setStartButtonOnAction(EventHandler<ActionEvent> onAction) {
        startGameButton.setOnAction(onAction);
    }

    private EventHandler<ActionEvent> startNewGame() { //стартует игру
        return event -> {
            hideStartButton();
            updateMessage("Black's Turn");
            CheckersBoard.isGame = true;
            startGameButton.setText("Restart");
            setStartButtonOnAction(restart());
        };
    }

    private EventHandler<ActionEvent> createNewField() { //Инициирует создание новой расстановки шашек и переключает
        return event -> { // кнопку в режим начала игры
            updateMessage("Start New Game");
            startGameButton.setText("Start New Game");
            setStartButtonOnAction(startNewGame());
            primaryStage = new PrimaryStage(FirstStage.root, this);
        };
    }

    private EventHandler<ActionEvent> restart() { //пересоздаем поле
        return event -> {
            updateMessage("Start New Game");
            startGameButton.setText("Start New Game");
            setStartButtonOnAction(startNewGame());
            CheckersBoard.initAllForRestart();
        };
    }
}