package terraIncognita.Controllers;

import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import terraIncognita.Main;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class GameWindowController extends BasicController{

    public GridPane deskGrid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void setup() {
        final int hAmount = Main.game.getLabyrinthHorSize();
        final int vAmount = Main.game.getLabyrinthVerSize();

        final double borderSize = Math.min(
                deskGrid.getHeight() / vAmount,
                deskGrid.getWidth() / hAmount
        );

        for (int i = 0; i < vAmount; i++) {
            deskGrid.getRowConstraints().add(new RowConstraints(borderSize, borderSize, borderSize));
        }

        for (int i = 0; i < hAmount; i++) {
            deskGrid.getColumnConstraints().add(new ColumnConstraints(borderSize, borderSize, borderSize));
        }

        for (int rowIndex = 0; rowIndex < vAmount; rowIndex++) {
            for (int colIndex = 0; colIndex < hAmount; colIndex++) {
                ImageView imgView = new ImageView(new File(
                        Main.TILES_IMG_DIR + Main.game.getLabyrinthTileAt(rowIndex, colIndex).getImageFileName()).toURI().toString()
                );
                imgView.setFitHeight(borderSize);
                imgView.setFitWidth(borderSize);
                imgView.setSmooth(false);
                deskGrid.add(imgView, colIndex, rowIndex);
            }
        }



    }
}
