package terraIncognita.Controllers;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.jetbrains.annotations.NotNull;
import terraIncognita.Main;
import terraIncognita.Model.MovementDirection;
import terraIncognita.Model.Player;
import terraIncognita.Utils.Point;
import terraIncognita.Utils.Utils;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class GameWindowController extends BasicController{

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
        Point oldPos = Main.game.getActivePlayer().getPosition();
        Point[] revealTiles = Main.game.getActivePlayer().move(movementDirection);
        for (Point p : revealTiles) {
            revealTileAt(p);
        }
        if (oldPos == Main.game.getActivePlayer().getPosition()) {
            return;
        }

        placePlayerTo(Main.game.getActivePlayer().getPosition());
    }

    private void revealTileAt(Point pos) {
        ImageView tile = null;
        for(Node child : deskGrid.getChildren()) {
            if (GridPane.getRowIndex(child) == pos.y() &&
                    GridPane.getColumnIndex(child) == pos.x() &&
                    child.getClass() == ImageView.class) {
                tile = (ImageView) child;
                break;
            }
        }
        if(tile == null) {
            throw new RuntimeException("Unexpected error occurred");
        }
        deskGrid.getChildren().remove(tile);
        deskGrid.add(createTileImageView(Utils.genUrlOf(
                        Main.TILES_IMG_DIR + Main.game.getActivePlayer().getDesk().getTileAt(pos).getImageFileName()
                )),
                pos.x(), pos.y()
        );
    }

    private void placePlayerTo(Point newPos) {
        deskGrid.getChildren().remove(playerShape);
        deskGrid.add(playerShape, newPos.x(), newPos.y());
    }

    private ImageView createTileImageView(@NotNull String url) {
        ImageView imgView = new ImageView(url);
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
                deskGrid.add(
                        createTileImageView(
                                new File(Main.TILES_IMG_DIR + player.getDesk().getTileAt(new Point(colIndex, rowIndex)
                                ).getImageFileName()).toURI().toString()
                        ),
                        colIndex, rowIndex
                );
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
        this.ruledScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (isCanMove && !isNewTurnShowing) {
                    switch (event.getCode()) {
                        case UP -> movePlayer(MovementDirection.UP);
                        case DOWN -> movePlayer(MovementDirection.DOWN);
                        case LEFT -> movePlayer(MovementDirection.LEFT);
                        case RIGHT -> movePlayer(MovementDirection.RIGHT);
                    }
                }
            }
        });
        Player activePlayer = Main.game.startGame(
                ((StartWindowController) Main.stageController.getControllerOf(Main.START_WINDOW_SCENE_NAME)).getLabyrinthSource()
        );

        hTileAmount = Main.game.getLabyrinthHorSize();
        vTileAmount = Main.game.getLabyrinthVerSize();

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

        Main.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(!isCanMove && !isNewTurnShowing) {
                    isNewTurnShowing = true;
                    try {
                        Thread.sleep(SHOW_NEW_TURN_TIME);
                    } catch (InterruptedException e) {
                        Utils.logError(e);
                    }
                    Platform.runLater(() -> {
                        if (Main.game.getActivePlayer().isEndGame()) {
                            Main.stageController.prepareScene(Main.END_WINDOW_SCENE_NAME);
                            Main.stageController.getControllerOf(Main.END_WINDOW_SCENE_NAME).setup(Main.game.getActivePlayer().getName());
                            Main.stageController.showScene();
                        }
                        loadDeskFrom(Main.game.nextPlayer());
                        isNewTurnShowing = false;
                    });
                }
            }
        }, 0, REFRESH_RATE);

    }
}
