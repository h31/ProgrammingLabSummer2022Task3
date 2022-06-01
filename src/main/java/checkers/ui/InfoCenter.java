package checkers.ui;

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
    private GameStage gameStage;

    public InfoCenter() {
        pane = new StackPane();
        pane.setMinSize(Constants.APP_WIDTH, Constants.INFO_CENTER_HEIGHT);
        pane.setTranslateX((double) Constants.APP_WIDTH / 2);
        pane.setTranslateY((double) Constants.INFO_CENTER_HEIGHT / 2);

        message = new Label("English Checkers");
        message.setMinSize(Constants.APP_WIDTH, Constants.INFO_CENTER_HEIGHT);
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

    private EventHandler<ActionEvent> createNewField() { //Инициирует создание новой расстановки шашек и переключает
        return event -> { // кнопку в режим начала игры
            updateMessage("Start New Game");
            startGameButton.setText("Start New Game");
            setStartButtonOnAction(startNewGame());
            gameStage = new GameStage(WelcomeStage.root, this);
        };
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

    private EventHandler<ActionEvent> restart() { //пересоздаем поле
        return event -> {
            updateMessage("Start New Game");
            startGameButton.setText("Start New Game");
            setStartButtonOnAction(startNewGame());
            CheckersBoard.initAllForRestart();
        };
    }
}