package terraIncognita.Controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Circle;
import org.jetbrains.annotations.NotNull;
import terraIncognita.Main;
import terraIncognita.Model.Desk.Desk;
import terraIncognita.Model.MovementDirection;
import terraIncognita.Model.Player;
import terraIncognita.Utils.Point;
import terraIncognita.Utils.Utils;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class GameWindowController extends BasicController{

    @FXML
    private GridPane deskGrid;
    @FXML
    private Circle playerShape;
    private static final double PLAYER_MIN_RADIUS = 5.0;

    private int hTileAmount;
    private int vTileAmount;
    private double borderSize;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void movePlayer(MovementDirection movementDirection) {
        Point oldPos = Main.game.getActivePlayer().getPosition();
        Point revealTile = Main.game.getActivePlayer().move(movementDirection);
        if (revealTile != null) {
            revealTileAt(revealTile);
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

    private void loadGridFrom(Desk desk) {
        clearGrid();
        for (int rowIndex = 0; rowIndex < vTileAmount; rowIndex++) {
            for (int colIndex = 0; colIndex < hTileAmount; colIndex++) {
                deskGrid.add(
                        createTileImageView(
                                new File(Main.TILES_IMG_DIR + desk.getTileAt(new Point(colIndex, rowIndex)
                                ).getImageFileName()).toURI().toString()
                        ),
                        colIndex, rowIndex
                );
            }
        }
    }

    private void clearGrid() {
        deskGrid.getChildren().clear();
    }

    @Override
    public void setup(Object... args) {
        this.ruledScene.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCharacter()) {
                    case "w" -> movePlayer(MovementDirection.UP);
                    case "s" -> movePlayer(MovementDirection.DOWN);
                    case "a" -> movePlayer(MovementDirection.LEFT);
                    case "d" -> movePlayer(MovementDirection.RIGHT);
                }
            }
        });

        Player activePlayer = Main.game.startGame();

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

        loadGridFrom(activePlayer.getDesk());
        placePlayerTo(activePlayer.getPosition());
    }
}
