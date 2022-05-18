package checkers.ui;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static checkers.logic.Logic.TILE_SIZE;

public class Tile extends Rectangle {
    private Piece piece;

    private Image imgBlack, imgWhite;

    {
        try {
            imgBlack = new Image(new FileInputStream(
                    "C:\\ProgrammingLabSummer2022Task3\\src\\main\\resources\\blackTile2.jpg"));
            imgWhite = new Image(new FileInputStream(
                    "C:\\ProgrammingLabSummer2022Task3\\src\\main\\resources\\whiteTile2.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean hasPiece(){
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Tile(boolean light, int x, int y) {
        setWidth(TILE_SIZE);
        setHeight(TILE_SIZE);

        relocate(x * TILE_SIZE, y * TILE_SIZE);
        setFill(light ? new ImagePattern(imgWhite): new ImagePattern(imgBlack));

    }

}
