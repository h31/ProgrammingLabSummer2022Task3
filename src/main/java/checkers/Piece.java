package checkers;

import static checkers.Checkers.TILE_SIZE;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Piece extends StackPane {
    private final PieceType pieceType;
    private static Image imgBlack, imgWhite;
    private double mouseX, mouseY;
    private double oldX, oldY;


    static {
        try {
            imgWhite = new Image(new FileInputStream(
                    "C:\\ProgrammingLabSummer2022Task3\\src\\main\\resources\\whitePiece.png"));
            imgBlack = new Image(new FileInputStream(
                    "C:\\ProgrammingLabSummer2022Task3\\src\\main\\resources\\blackPiece.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public Piece(PieceType pieceType, int x, int y) {
        this.pieceType = pieceType;

        move(x, y);// Перемещение к левому верхнему углу клетки

        ImageView imageView = pieceType == PieceType.BLACK ? new ImageView(imgBlack) : new ImageView(imgWhite);
        imageView.setFitHeight(TILE_SIZE);
        imageView.setFitWidth(TILE_SIZE);

        getChildren().add(imageView);

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY));

    }

    public void move(int x, int y) {
        oldX = x * TILE_SIZE;
        oldY = y * TILE_SIZE;
        relocate(oldX, oldY);
    }

    public void abortMove(){
         relocate(oldX, oldY);
    }

}