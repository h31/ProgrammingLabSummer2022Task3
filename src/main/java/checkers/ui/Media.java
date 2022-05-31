package checkers.ui;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Media {
    private static Image imgBlack, imgWhite, imgCrown,
            imgBlackTile, imgWhiteTile, icon, imgUndoButton;

   static  {
        try {
            imgWhite = new Image(new FileInputStream("src/main/resources/whitePiece.png"));
            imgBlack = new Image(new FileInputStream("src/main/resources/blackPiece.png"));
            imgCrown = new Image(new FileInputStream("src/main/resources/crown.png"));
            imgBlackTile = new Image(new FileInputStream("src/main/resources/blackTile.jpg"));
            imgWhiteTile = new Image(new FileInputStream("src/main/resources/whiteTile.jpg"));
            icon = new Image(new FileInputStream("src/main/resources/icon.png"));
            imgUndoButton = new Image(new FileInputStream("src/main/resources/undo.png"));
        } catch (NullPointerException | FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Media Error");
            alert.setContentText("Произошла ошибка при считывании файлов." +
                    " Продолжение игры невозможно");
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
