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

    //10 как константа
    //enum для phase

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

    private int gamePhase = 0;

    private Field fieldPlayer = new Field(fieldPlayerView);
    private Field fieldAI = new Field(fieldAIView);

    // Не изменять это
    private final List<ShipType> shipTypeList = new ArrayList<>();
    private int selectedShip = 0;

    private AIController aiController = new AIController(fieldPlayer);


    //!!!
    public void setShipList() {
        shipTypeList.clear();

        shipTypeList.add(new ShipType(true, 4, 1));
        shipTypeList.add(new ShipType(true, 3, 2));
        shipTypeList.add(new ShipType(true, 2, 3));
        shipTypeList.add(new ShipType(true, 1, 4));
    }

    private void newGame() {
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
        for (ShipType shipType : shipTypeList) {
            while (shipType.count > 0) {
                if (new Random().nextInt(2) == 0) shipType.changeRotation();

                List<Coordinate> allowList = field.allowPlaceCells(shipType.size, shipType.isHorisontal);
                Coordinate randomCoordinate = allowList.get(new Random().nextInt(allowList.size()));

                for (int i = 0; i < shipType.size; i++) {
                    field.fillCell(randomCoordinate.getX() + (shipType.isHorisontal ? i : 0),
                            randomCoordinate.getY() + (shipType.isHorisontal ? 0 : i),
                            Cell.SHIP, isVisible);
                }

                shipType.count--;
            }
        }
    }

    @FXML
    void clearField() {
        newGame();

        gamePhase = 0;

        updateTexts();
    }

    @FXML
    void quickPlacement() {
        randomShipPlacement(fieldPlayer, true);

        gamePhase = 1;

        updateTexts();
    }

    @FXML
    void startGame() {
        if (gamePhase == 1) {
            gamePhase = 2;

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
        if (gamePhase == 0 && isPlayer) placeShip(shipTypeList.get(selectedShip), x, y);

        if (gamePhase == 2 && !isPlayer && fieldAI.isAllowFire(x, y)) {
            if (!fieldAI.attackCell(x, y)) {
                gamePhase = 3;
                aiController.startAttack();

                if (fieldPlayer.checkWin()) endGame(false);
                else gamePhase = 2;
            }
            else if (fieldAI.checkWin()) endGame(true);
        }
    }

    private void endGame(boolean isPlayerWin) {
        textWin.setText(isPlayerWin ? "Победа" : "Поражение");
        gamePhase = 4;

        pane.getChildren().add(textWin);
        pane.getChildren().add(buttonRestartGame);
    }

    //в отдельный метод
    //fieldPlayer.fillCell(x + (shipType.isHorisontal ? i : 0), y + (!shipType.isHorisontal ? i : 0), Cell.SHIP, true);
    private void placeShip(ShipType shipType, int x, int y) {
        if (shipType.count > 0 && fieldPlayer.isAllowPlaceShip(shipType.size, shipType.isHorisontal, x, y)) {
            for (int i = 0; i < shipType.size; i++)
                fieldPlayer.fillCell(x + (shipType.isHorisontal ? i : 0), y + (!shipType.isHorisontal ? i : 0), Cell.SHIP, true);

            shipType.count--;

            updateTexts();

            boolean hasShips = true;
            for (ShipType s : shipTypeList)
                if (s.count > 0) {
                    hasShips = false;
                    break;
                }
            if (hasShips) gamePhase = 1;
        }
    }

    @FXML
    private void changeRotation() {
        //метод с ротацией!!!!!
        shipTypeList.get(selectedShip).changeRotation();

        updateTexts();
    }

    @FXML
    private void changeSize() {
        selectedShip++;
        if (selectedShip >= 4) selectedShip = 0;

        updateTexts();
    }

    private void updateTexts() {
        textCount.setText("Кол-во: " + shipTypeList.get(selectedShip).count);
        textRotation.setText(shipTypeList.get(selectedShip).isHorisontal ? "гориз." : "верт.");
        textSize.setText("" + shipTypeList.get(selectedShip).size);
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
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < 10; i++) {
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