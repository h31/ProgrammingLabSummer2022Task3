package checkers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static checkers.Checkers.*;
import static checkers.Logic.*;

public class Piece extends StackPane {
    private final PieceType pieceType;
    private static Image imgBlack, imgWhite, imgCrown;
    private double mouseX, mouseY;
    private double oldX, oldY;
    private boolean killer;
    private boolean crown;


    public enum PieceType { //Тип шашки
        BLACK(1), WHITE(-1);

        final int moveDir;

        PieceType(int moveDir) {
            this.moveDir = moveDir;
        }


    }

    static {
        try {
            imgWhite = new Image(new FileInputStream(
                    "C:\\ProgrammingLabSummer2022Task3\\src\\main\\resources\\whitePiece.png"));
            imgBlack = new Image(new FileInputStream(
                    "C:\\ProgrammingLabSummer2022Task3\\src\\main\\resources\\blackPiece.png"));
            imgCrown = new Image(new FileInputStream(
                    "C:\\ProgrammingLabSummer2022Task3\\src\\main\\resources\\crown.png"));
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

        ImageView crown = new ImageView(imgCrown);
        crown.setFitWidth(TILE_SIZE);
        crown.setFitHeight(TILE_SIZE);
        crown.setOpacity(0.67);
        crown.setVisible(false);//Ставлю сразу на все шашки, но делаю видимой только у дамок

        getChildren().addAll(imageView, crown);


        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
            Logic.anyThreat();
        });

        setOnMouseDragged(e -> relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY));

        setOnMouseReleased(e -> {

            int newX = toBoard(this.getLayoutX());
            int newY = toBoard((this.getLayoutY()));


            if (newX >= 0 && newX < WIDTH // Проверка на нахождение шашки в пределах игрового поля
                    && newY >= 0 && newY < HEIGHT) {

                MoveResult result = (isCrown() ? tryMoveCrown(this, newX, newY) :
                        tryMove(this, newX, newY)); //Вернёт результат шага и новыe координаты

                int x0 = toBoard(this.getOldX());
                int y0 = toBoard(this.getOldY());



                if (isKillNeed() && !isKiller()) {
                    abortMove(); //Если какая-то шашка должна съесть, а выбрана другая - сброс

                }

                if (isKillNeed() && isKiller()) {
                    switch (result.getMoveType()) {
                        case NONE:
                        case NORMAL: //Если шашка должна съедать дальше, эти движения не устраивают
                            this.abortMove();
                            break;

                        case KILL: //Перешли через шашку
                            this.move(newX, newY);
                            board[x0][y0].setPiece(null); // Очищаем предыдущую клетку
                            board[newX][newY].setPiece(this); //Ставим на новую

                            Piece killedPiece = result.getPiece(); // Убитая шашка
                            //По координатам убитой шашки удаляем её из board
                            board[toBoard(killedPiece.getOldX())][toBoard(killedPiece.getOldY())].setPiece(null);
                            //Очищаем с поля
                            getPieceGroup().getChildren().remove(killedPiece);
                            if (getPieceType() == PieceType.BLACK && newY == (HEIGHT - 1) ||
                                    getPieceType() == PieceType.WHITE && newY == 0) {
                                setCrown(true);
                                crown.setVisible(true); //Стала дамкой = видно корону
                            }

                            if (canKill(this, newX, newY)) { //Если после хода шашка может убить ещё,
                                // появляется флаг killer и ход не переходит
                                killer = true;

                            } else {
                                setKillNeed(false);
                                killer = false;
                                turn = !turn;//Смена хода
                            }
                            changingTurn();
                            deadPiece(killedPiece);

                            break;
                    }

                } else if (!isKillNeed()) {
                    switch (result.getMoveType()) {
                        case NONE:
                            this.abortMove();
                            break;
                        case NORMAL:
                            this.move(newX, newY);
                            board[x0][y0].setPiece(null); // Очищаем предыдущую клетку
                            board[newX][newY].setPiece(this);//Ставим на новую
                            if (getPieceType() == PieceType.BLACK && newY == (HEIGHT - 1) ||
                                    getPieceType() == PieceType.WHITE && newY == 0) {
                                setCrown(true);
                                crown.setVisible(true); //Стала дамкой = видно корону
                            }

                            turn = !turn; //Смена хода
                            changingTurn();

                            break;
                    }
                }
                eatAlarm();
            } else this.abortMove(); //Если не в пределах доски - сброс

        });

    }


    private void move(int x, int y) { //Движение по доске
        oldX = x * TILE_SIZE;
        oldY = y * TILE_SIZE;
        relocate(oldX, oldY);
    }

    private void abortMove() { //Отмена движения
        relocate(oldX, oldY);
    }


}