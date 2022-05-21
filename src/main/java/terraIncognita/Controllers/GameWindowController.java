package terraIncognita.Controllers;

import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import terraIncognita.Main;
import terraIncognita.Model.Desk.Desk;
import terraIncognita.Model.Player;
import terraIncognita.Utils.Point;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class GameWindowController extends BasicController{

    public GridPane deskGrid;
    private int hTileAmount;
    private int vTileAmount;
    private double borderSize;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void loadGridFrom(Desk desk) {
        clearGrid();
        for (int rowIndex = 0; rowIndex < vTileAmount; rowIndex++) {
            for (int colIndex = 0; colIndex < hTileAmount; colIndex++) {
                ImageView imgView = new ImageView(new File(
                        Main.TILES_IMG_DIR + desk.getTileAt(
                                new Point(colIndex, rowIndex)
                        ).getImageFileName()
                ).toURI().toString());
                imgView.setFitHeight(borderSize);
                imgView.setFitWidth(borderSize);
                imgView.setSmooth(false);
                deskGrid.add(imgView, colIndex, rowIndex);
            }
        }
    }

    public void clearGrid() {
        deskGrid.getChildren().clear();
    }

    @Override
    public void setup() {
        Player activePlayer = Main.game.startGame();

        hTileAmount = Main.game.getLabyrinthHorSize();
        vTileAmount = Main.game.getLabyrinthVerSize();

        borderSize = Math.min(
                deskGrid.getHeight() / vTileAmount,
                deskGrid.getWidth() / hTileAmount
        );

        for (int i = 0; i < vTileAmount; i++) {
            deskGrid.getRowConstraints().add(new RowConstraints(borderSize, borderSize, borderSize));
        }

        for (int i = 0; i < hTileAmount; i++) {
            deskGrid.getColumnConstraints().add(new ColumnConstraints(borderSize, borderSize, borderSize));
        }

        loadGridFrom(activePlayer.getDesk());
    }
}
