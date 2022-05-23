package checkers.ui;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static checkers.logic.Listeners.*;
import static checkers.logic.Logic.*;
import static checkers.ui.ContentCreator.getLeft;
import static checkers.ui.ContentCreator.getRight;

public class Piece extends StackPane {
    private final PieceType pieceType;
    private static Image imgBlack, imgWhite, imgCrown;
    private double mouseX, mouseY;
    private double oldX, oldY;
    private boolean killer;
    private boolean crown;

    private final ImageView crownImgView = new ImageView(imgCrown);


    public enum PieceType { //Тип шашки
        BLACK(1), WHITE(-1);

        public final int moveDir;

        PieceType(int moveDir) {
            this.moveDir = moveDir;
        }


    }

    static {
        try {
            imgWhite = new Image(new FileInputStream(
                    "input\\whitePiece.png"));
            imgBlack = new Image(new FileInputStream(
                    "input/blackPiece.png"));
            imgCrown = new Image(new FileInputStream(
                    "input/crown.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }




    public ImageView getCrownImgView() {
        return crownImgView;
    }

    public void setMouseX(double mouseX) {
        this.mouseX = mouseX;
    }

    public  void setMouseY(double mouseY) {
        this.mouseY = mouseY;
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
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

    public boolean isKiller() {
        return killer;
    }

    public void setKiller(boolean killer) {
        this.killer = killer;
    }

    public boolean isCrown() {
        return crown;
    }

    public void setCrown(boolean crown) {
        this.crown = crown;
    }

    public Piece(Piece.PieceType pieceType, int x, int y) {
        this.pieceType = pieceType;


        move(x, y);// Перемещение к левому верхнему углу клетки при начальной отрисовке

        ImageView imageView = pieceType == PieceType.BLACK ? new ImageView(imgBlack) : new ImageView(imgWhite);
        imageView.setFitHeight(TILE_SIZE);
        imageView.setFitWidth(TILE_SIZE);


        crownImgView.setFitWidth(TILE_SIZE);
        crownImgView.setFitHeight(TILE_SIZE);
        crownImgView.setOpacity(0.67);
        crownImgView.setVisible(false);//Ставлю сразу на все шашки, но делаю видимой только у дамок

        getChildren().addAll(imageView, crownImgView);

        //Слушатель, отвечающий за начало движения
        addEventHandler(MouseEvent.MOUSE_PRESSED, moveStart(this));
        //Слушатель, отвечающий за передвижение
        addEventHandler(MouseEvent.MOUSE_DRAGGED, movement(this));
        //Слушатель, отвечающий за конец перемещения
        addEventHandler(MouseEvent.MOUSE_RELEASED, moveEnd(this));

    }

    public void move(int x, int y) { //Подгонка изображения к левому верхнему краю клетки
        oldX = x * TILE_SIZE;
        oldY = y * TILE_SIZE;
        relocate(oldX, oldY);
    }

    public void abortMove() { //Отмена движения
        relocate(oldX, oldY);
    }
    public static void deadPiece(Piece piece) {

        if ((piece.getPieceType() == Piece.PieceType.WHITE)) {
            getRight().getChildren().add(piece);
        } else {
            getLeft().getChildren().add(piece);
        }
    }


}