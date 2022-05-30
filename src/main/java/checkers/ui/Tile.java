package checkers.ui;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static checkers.logic.Logic.TILE_SIZE;

public class Tile extends Rectangle {
    private Piece piece;

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
        setFill(light ? new ImagePattern(Media.getImgWhiteTile()):
                new ImagePattern(Media.getImgBlackTile()));

    }

    public Tile(){

    }

}
