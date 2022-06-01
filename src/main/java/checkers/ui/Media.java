package checkers.ui;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;


public class Media {
    private static Image imgBlack, imgWhite, imgCrown, imgBlackTile, imgWhiteTile, icon, imgUndoButton;

   static  {
        try {
            imgWhite = new Image("whitePiece.png");
            imgBlack = new Image("blackPiece.png");
            imgCrown = new Image("crown.png");
            imgBlackTile = new Image("blackTile.jpg");
            imgWhiteTile = new Image("whiteTile.jpg");
            icon = new Image("icon.png");
            imgUndoButton = new Image("undo.png");
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
