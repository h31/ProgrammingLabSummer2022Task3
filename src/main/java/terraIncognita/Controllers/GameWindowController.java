package terraIncognita.Controllers;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.jetbrains.annotations.NotNull;

import terraIncognita.App;
import terraIncognita.Model.MovementDirection;
import terraIncognita.Model.Player;
import terraIncognita.Utils.Point;
import terraIncognita.Utils.Utils;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class GameWindowController extends BasicController{

    @FXML
    private GridPane root;
    @FXML
    private GridPane deskGrid;
    @FXML
    private Circle playerShape;
    @FXML
    private Label playerNameLabel;

    private static final double PLAYER_MIN_RADIUS = 5.0;
    private static final int REFRESH_RATE = 100;
    private static final int SHOW_NEW_TURN_TIME = 1000;

    private boolean isCanMove = true;
    private boolean isNewTurnShowing = false;

    private int hTileAmount;
    private int vTileAmount;
    private double borderSize;

    private final StringProperty labelText = new SimpleStringProperty("");
    private final IntegerProperty r = new SimpleIntegerProperty(0);
    private final IntegerProperty g = new SimpleIntegerProperty(0);
    private final IntegerProperty b = new SimpleIntegerProperty(0);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playerNameLabel.textProperty().bind(labelText);
        playerShape.fillProperty().bind(Bindings.createObjectBinding(() -> Color.rgb(r.get(), g.get(), b.get()), r, g, b));
    }

    private void movePlayer(MovementDirection movementDirection) {
        isCanMove = false;
        Point oldPos = App.game.getActivePlayer().getPosition();
        List<Point> revealTiles = App.game.getActivePlayer().move(movementDirection);
        for (Point p : revealTiles) {
            revealTileAt(p);
        }
        App.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(!isCanMove && !isNewTurnShowing) {
                    isNewTurnShowing = true;
                    try {
                        Thread.sleep(SHOW_NEW_TURN_TIME);
                    } catch (InterruptedException e) {
                        Utils.logErrorWithExit(e);
                    }
                    Platform.runLater(() -> {
                        if (App.game.getActivePlayer().isEndGame()) {
                            App.stageController.prepareScene(App.END_WINDOW_SCENE_NAME);
                            App.stageController.getControllerOf(App.END_WINDOW_SCENE_NAME).setup(App.game.getActivePlayer().getName());
                            App.stageController.showScene();
                        }
                        loadDeskFrom(App.game.nextPlayer());
                        isNewTurnShowing = false;
                    });
                }
            }
        }, 0);

        if (oldPos != App.game.getActivePlayer().getPosition()) {
            placePlayerTo(App.game.getActivePlayer().getPosition());
        }
    }

    private void revealTileAt(Point pos) {
        Optional<Node> tileOp = deskGrid.getChildren().stream().filter(el ->
                GridPane.getRowIndex(el) == pos.y &&
                GridPane.getColumnIndex(el) == pos.x &&
                el.getClass() == ImageView.class
        ).findFirst();

        ImageView tile = null;
        try {
            tile = (ImageView) tileOp.get();
        } catch (NoSuchElementException e) {
            Utils.logErrorWithExit(e);
        }
        deskGrid.getChildren().remove(tile);

        InputStream imageInputStream = App.resourceLoader.getInputStreamOf(
                App.TILES_IMG_RELATIVE_DIR +
                        App.game.getActivePlayer().getDesk().getTileAt(pos).getImageFileName()
        );
        deskGrid.add(createTileImageView(imageInputStream), pos.x, pos.y);
    }

    private void placePlayerTo(Point newPos) {
        deskGrid.getChildren().remove(playerShape);
        deskGrid.add(playerShape, newPos.x, newPos.y);
    }

    private ImageView createTileImageView(@NotNull InputStream input) {
        ImageView imgView = new ImageView();
        imgView.setImage(new Image(input));
        imgView.setFitHeight(borderSize);
        imgView.setFitWidth(borderSize);
        imgView.setSmooth(false);
        return imgView;
    }

    private void changeCircleColor(java.awt.Color color) {
        r.set(color.getRed());
        g.set(color.getGreen());
        b.set(color.getBlue());
    }

    private void loadDeskFrom(Player player) {
        changeCircleColor(player.getColor());
        clearGrid();
        for (int rowIndex = 0; rowIndex < vTileAmount; rowIndex++) {
            for (int colIndex = 0; colIndex < hTileAmount; colIndex++) {
                String relativePath = App.TILES_IMG_RELATIVE_DIR + player.getDesk().getTileAt(new Point(colIndex, rowIndex)).getImageFileName();
                deskGrid.add(createTileImageView(App.resourceLoader.getInputStreamOf(relativePath)), colIndex, rowIndex);
            }
        }

        labelText.set(player.getName());
        placePlayerTo(player.getPosition());
        isCanMove = true;
    }

    private void clearGrid() {
        deskGrid.getChildren().clear();
    }

    @Override
    public void setup(Object... args) {
        this.ruledScene.setOnKeyPressed(event -> {
            if (isCanMove && !isNewTurnShowing) {
                switch (event.getCode()) {
                    case UP -> movePlayer(MovementDirection.UP);
                    case DOWN -> movePlayer(MovementDirection.DOWN);
                    case LEFT -> movePlayer(MovementDirection.LEFT);
                    case RIGHT -> movePlayer(MovementDirection.RIGHT);
                }
            }
        });
        InputStream labyrinthIS = App.resourceLoader.getInputStreamOf(
                ((StartWindowController) App.stageController.getControllerOf(App.START_WINDOW_SCENE_NAME)).getLabyrinthName()
        );
        Player activePlayer = App.game.startGame(labyrinthIS);

        hTileAmount = App.game.getLabyrinthHorSize();
        vTileAmount = App.game.getLabyrinthVerSize();

        borderSize = Math.min(
                deskGrid.getHeight() / vTileAmount,
                deskGrid.getWidth() / hTileAmount
        );

        playerShape.setRadius(Math.max(PLAYER_MIN_RADIUS, borderSize / 5));

        for (int i = 0; i < vTileAmount; i++) {
            deskGrid.getRowConstraints().add(new RowConstraints(borderSize, borderSize, borderSize));
        }

        for (int i = 0; i < hTileAmount; i++) {
            deskGrid.getColumnConstraints().add(new ColumnConstraints(borderSize, borderSize, borderSize));
        }

        loadDeskFrom(activePlayer);

    }
}
