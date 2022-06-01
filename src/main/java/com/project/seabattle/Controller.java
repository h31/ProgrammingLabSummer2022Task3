package com.project.seabattle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    //region FXML variables
    @FXML
    private Text textWin;

    @FXML
    private Button buttonRestartGame;

    @FXML
    private Button buttonStart;

    @FXML
    private Button buttonClear;
    @FXML
    private AnchorPane pane;

    @FXML
    private Button buttonRotation;

    @FXML
    private Button buttonSize;

    @FXML
    private Button buttonQuickPlacement;

    @FXML
    private GridPane fieldAIView;

    @FXML
    private GridPane fieldPlayerView;

    @FXML
    private Text textCount;

    @FXML
    private Text textRotation;

    @FXML
    private Text textSize;
    //endregion

    Phase gamePhase = Phase.PLACEMENT;

    Field fieldPlayer = new Field(fieldPlayerView);
    Field fieldAI = new Field(fieldAIView);

    List<ShipType> shipList = new ArrayList<>();
    private int selectedShip = 0;

    private AIController aiController = new AIController(fieldPlayer);

    public boolean isTest;

    public void setShipList() {
        shipList = Arrays.asList(
                new ShipType(true, 4, 1),
                new ShipType(true, 3, 2),
                new ShipType(true, 2, 3),
                new ShipType(true, 1, 4));
    }

    void newGame() {
        if (isTest) {
            fieldPlayer = new Field(true);
            fieldAI = new Field(true);
            setShipList();
            return;
        }
        setShipList();

        fieldPlayerView.setGridLinesVisible(false);
        fieldPlayerView.getChildren().clear();
        fieldPlayerView.setGridLinesVisible(true);

        fieldAIView.setGridLinesVisible(false);
        fieldAIView.getChildren().clear();
        fieldAIView.setGridLinesVisible(true);

        fieldPlayer.clear();
        fieldAI.clear();

        addTiles(fieldPlayerView);
        addTiles(fieldAIView);
    }

    private void randomShipPlacement(Field field, boolean isVisible) {
        for (ShipType shipType : shipList) {
            while (shipType.count > 0) {
                if (new Random().nextInt(2) == 0) shipType.changeRotation();

                List<Coordinate> allowList = field.allowPlaceCells(shipType.size, shipType.isHorisontal);
                Coordinate randomCoordinate = allowList.get(new Random().nextInt(allowList.size()));

                for (int i = 0; i < shipType.size; i++) {
                    int dx = randomCoordinate.getX() + (shipType.isHorisontal ? i : 0);
                    int dy = randomCoordinate.getY() + (shipType.isHorisontal ? 0 : i);
                    field.fillCell(dx, dy, Cell.SHIP, isVisible);
                }

                shipType.count--;
            }
        }
    }

    @FXML
    void clearField() {
        newGame();

        gamePhase = Phase.PLACEMENT;

        updateTexts();
    }

    @FXML
    void quickPlacement() {
        randomShipPlacement(fieldPlayer, true);

        gamePhase = Phase.READY_START;

        updateTexts();
    }

    @FXML
    void startGame() {
        if (gamePhase == Phase.READY_START) {
            gamePhase = Phase.MOVE_PLAYER;;

            pane.getChildren().remove(buttonRotation);
            pane.getChildren().remove(buttonSize);
            pane.getChildren().remove(buttonQuickPlacement);
            pane.getChildren().remove(textCount);
            pane.getChildren().remove(textRotation);
            pane.getChildren().remove(textSize);
            pane.getChildren().remove(buttonStart);
            pane.getChildren().remove(buttonClear);

            setShipList();
            randomShipPlacement(fieldAI, false);

            aiController = new AIController(fieldPlayer);
        }
    }

    private void clickField(int x, int y, boolean isPlayer) {
        if (gamePhase == Phase.PLACEMENT && isPlayer) placeShip(shipList.get(selectedShip), x, y);

        if (gamePhase == Phase.MOVE_PLAYER && !isPlayer && fieldAI.isAllowFire(x, y)) {
            if (!fieldAI.attackCell(x, y)) {
                gamePhase = Phase.MOVE_AI;
                aiController.startAttack();

                if (fieldPlayer.checkWin()) endGame(false);
                else gamePhase = Phase.MOVE_PLAYER;
            }
            else if (fieldAI.checkWin()) endGame(true);
        }
    }

    private void endGame(boolean isPlayerWin) {
        textWin.setText(isPlayerWin ? "Победа" : "Поражение");
        gamePhase = Phase.END;

        pane.getChildren().add(textWin);
        pane.getChildren().add(buttonRestartGame);
    }

    void placeShip(ShipType shipType, int x, int y) {
        if (shipType.count > 0 && fieldPlayer.isAllowPlaceShip(shipType.size, shipType.isHorisontal, x, y)) {
            for (int i = 0; i < shipType.size; i++) {
                int dx = x + (shipType.isHorisontal ? i : 0);
                int dy = y + (shipType.isHorisontal ? 0 : i);
                fieldPlayer.fillCell(dx, dy, Cell.SHIP, true);
            }

            shipType.count--;

            updateTexts();

            boolean hasShips = true;
            for (ShipType s : shipList)
                if (s.count > 0) {
                    hasShips = false;
                    break;
                }
            if (hasShips) gamePhase = Phase.READY_START;
        }
    }

    @FXML
    private void changeRotation() {
        shipList.get(selectedShip).changeRotation();

        updateTexts();
    }

    @FXML
    private void changeSize() {
        selectedShip++;
        if (selectedShip >= 4) selectedShip = 0;

        updateTexts();
    }

    private void updateTexts() {
        if (isTest) return;
        textCount.setText("Кол-во: " + shipList.get(selectedShip).count);
        textRotation.setText(shipList.get(selectedShip).isHorisontal ? "гориз." : "верт.");
        textSize.setText("" + shipList.get(selectedShip).size);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setShipList();

        addTiles(fieldPlayerView);
        addTiles(fieldAIView);

        fieldPlayer = new Field(fieldPlayerView);
        fieldAI = new Field(fieldAIView);

        pane.getChildren().remove(textWin);
        pane.getChildren().remove(buttonRestartGame);

        updateTexts();
    }

    private void addTiles(GridPane gridPane) {
        for (int j = 0; j < Constants.fieldSize; j++) {
            for (int i = 0; i < Constants.fieldSize; i++) {
                Tile tile = new Tile(i, j, gridPane == fieldPlayerView);
                tile.setPrefSize(20, 20);
                gridPane.add(tile, i, j);
            }
        }
    }

    class Tile extends Pane {
        private final int x;
        private final int y;
        private final boolean isPlayer;

        public Tile(int x, int y, boolean isPlayer) {
            this.x = x;
            this.y = y;
            this.isPlayer = isPlayer;
            setOnMouseClicked(e -> clickField(this.x, this.y, this.isPlayer));
        }
    }

    @FXML
    void restartGame() {
        Stage stage = (Stage) buttonRestartGame.getScene().getWindow();
        stage.close();
        try {
            new Main().start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}