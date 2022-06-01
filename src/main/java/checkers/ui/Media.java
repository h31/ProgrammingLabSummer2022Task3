package checkers.ui;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import java.io.File;

public class Media {
    private static Image imgBlack, imgWhite, imgCrown, imgBlackTile, imgWhiteTile, icon, imgUndoButton;
    private final File imgBlackF = new File("blackPiece.png"),
            imgWhiteF = new File("whitePiece.png"),
            imgCrownF = new File("crown.png"),
            imgBlackTileF = new File("blackTile.jpg"),
            imgWhiteTileF = new File("whiteTile.jpg"),
            iconF = new File("icon.png"),
            imgUndoButtonF= new File("undo.png");

    {
        try {
            imgWhite = new Image(imgWhiteF.getPath());
            imgBlack = new Image(imgBlackF.getPath());
            imgCrown = new Image(imgCrownF.getPath());
            imgBlackTile = new Image(imgBlackTileF.getPath());
            imgWhiteTile = new Image(imgWhiteTileF.getPath());
            icon = new Image(iconF.getPath());
            imgUndoButton = new Image(imgUndoButtonF.getPath());
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Media Error");
            alert.setContentText("Произошла ошибка при считывании файлов." + " Продолжение игры невозможно");
            alert.show();
        }
    }

    public static Image getImgCrown() {
        return imgCrown;
    }

    public static Image getIcon() {
        return icon;
    }

    public static Image getImgUndoButton() {
        return imgUndoButton;
    }

    public static Image getImgBlackTile() {
        return imgBlackTile;
    }

    public static Image getImgWhiteTile() {
        return imgWhiteTile;
    }

    public static Image getImgWhite() {
        return imgWhite;
    }

    public static Image getImgBlack() {
        return imgBlack;
    }
}
