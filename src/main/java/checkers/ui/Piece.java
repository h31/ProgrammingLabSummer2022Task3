package checkers.ui;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;


import static checkers.logic.Listeners.*;
import static checkers.logic.Logic.*;
import static checkers.ui.ContentCreator.getLeft;
import static checkers.ui.ContentCreator.getRight;

public class Piece extends StackPane {
    private final PieceType pieceType;
    private double mouseX, mouseY;
    private double startFromX, startFromY; //С этой точки мы начинаем движения при ходе шашки
    private boolean killer;
    private boolean crown;
    private static ImageView crownView;



    public enum PieceType { //Тип шашки
        BLACK(1), WHITE(-1);

        public final int moveDir;

        PieceType(int moveDir) {
            this.moveDir = moveDir;
        }


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

    public double getStartFromX() {
        return startFromX;
    }

    public double getStartFromY() {
        return startFromY;
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

    public ImageView getCrownView() {
        return crownView;
    }

    public Piece(Piece.PieceType pieceType, int x, int y) {
        this.pieceType = pieceType;
        crownView = new ImageView(Media.getImgCrown());


        drawer(x, y);// Перемещение к левому верхнему углу клетки при начальной отрисовке

        ImageView imageView = pieceType == PieceType.BLACK ? new ImageView(Media.getImgBlack()) : new ImageView(Media.getImgWhite());
        imageView.setFitHeight(TILE_SIZE);
        imageView.setFitWidth(TILE_SIZE);


        crownView.setFitWidth(TILE_SIZE);
        crownView.setFitHeight(TILE_SIZE);
        crownView.setOpacity(0.67);
        crownView.setVisible(false);//Ставлю сразу на все шашки, но делаю видимой только у дамок

        getChildren().addAll(imageView, crownView);

        //Слушатель, отвечающий за начало движения
        addEventHandler(MouseEvent.MOUSE_PRESSED, moveStart(this));
        //Слушатель, отвечающий за передвижение
        addEventHandler(MouseEvent.MOUSE_DRAGGED, movement(this));
        //Слушатель, отвечающий за конец перемещения
        addEventHandler(MouseEvent.MOUSE_RELEASED, moveEnd(this));

    }

    //Подгонка изображения к левому верхнему краю клетки
    public void drawer(int x, int y) {
        //Меняем поля, отвечающие за начальную точку при движении
        startFromX = x * TILE_SIZE;
        startFromY = y * TILE_SIZE;
        relocate(startFromX, startFromY);
    }

    //Отмена движения
    public void abortMove() { //Отмена движения
        relocate(startFromX, startFromY);
    }

    //Отправляет съеденную шашку за доску
    public static void deadPiece(Piece piece) {
        FlowPane pane = piece.getPieceType() == PieceType.WHITE ? getRight() : getLeft();
        pane.getChildren().add(piece);
    }

}