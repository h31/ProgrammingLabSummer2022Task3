package checkers.logic;

import checkers.Checkers;
import checkers.ui.ContentCreator;
import checkers.ui.Piece;
import checkers.ui.StepBackDrawer;
import checkers.ui.Tile;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.util.Arrays;

import static checkers.logic.Logic.*;
import static checkers.logic.Logic.eatAlarm;
import static checkers.ui.ConfirmBox.confirmation;
import static checkers.ui.ContentCreator.*;
import static java.util.Arrays.copyOf;

public class Listeners{


    public static EventHandler<MouseEvent> moveStart(Piece piece) {
        return e -> {
            piece.setMouseX(e.getSceneX());
            piece.setMouseY(e.getSceneY());
        };

    }

    public static EventHandler<MouseEvent> movement(Piece piece) {
        return e -> piece.relocate(e.getSceneX() - piece.getMouseX() + piece.getOldX(),
                e.getSceneY() - piece.getMouseY() + piece.getOldY());
    }

    public  static EventHandler<MouseEvent> moveEnd(Piece piece) {
        return e -> Platform.runLater(() ->{


        int newX = toBoard(piece.getLayoutX());
        int newY = toBoard((piece.getLayoutY()));


        if (newX >= 0 && newX < WIDTH // Проверка на нахождение шашки в пределах игрового поля
                && newY >= 0 && newY < HEIGHT) {

            MoveResult result = (piece.isCrown() ? tryMoveCrown(piece, newX, newY) :
                    tryMove(piece, newX, newY)); //Вернёт результат шага и новыe координаты

            int y0 = toBoard(piece.getOldY());
            int x0 = toBoard(piece.getOldX());


            Logic.anyThreat();
            if (isKillNeed() && !piece.isKiller()) {
                piece.abortMove(); //Если какая-то шашка должна съесть, а выбрана другая - сброс

            }

            if (isKillNeed() && piece.isKiller()) {
                switch (result.getMoveType()) {
                    case NONE:
                    case NORMAL: //Если шашка должна съедать дальше, эти движения не устраивают
                        piece.abortMove();
                        break;

                    case KILL: //Перешли через шашку
                        piece.move(newX, newY);
                        getBoard()[x0][y0].setPiece(null); // Очищаем предыдущую клетку
                        getBoard()[newX][newY].setPiece(piece); //Ставим на новую


                        Piece killedPiece = result.getPiece(); // Убитая шашка
                        //По координатам убитой шашки удаляем её из board
                        getBoard()[toBoard(killedPiece.getOldX())][toBoard(killedPiece.getOldY())].setPiece(null);
                        //Очищаем с поля
                        getPieceGroup().getChildren().remove(killedPiece);
                        if (piece.getPieceType() == Piece.PieceType.BLACK && newY == (HEIGHT - 1) ||
                                piece.getPieceType() == Piece.PieceType.WHITE && newY == 0) {
                            piece.setCrown(true);
                            piece.getCrownImgView().setVisible(true); //Стала дамкой = видно корону
                        }


                        setLastKiller(piece);//Отмечаем последнего убийцу

                        if (canKill(piece, newX, newY)) { //Если после хода шашка может убить ещё,
                            // появляется флаг killer и ход не переходит
                            piece.setKiller(true);
                            setKillCount(getKillCount() + 1);
                            getEatenPieces().push(killedPiece);
                            System.out.println(getEatenPieces().size());

                        } else {
                            setKillNeed(false);
                            piece.setKiller(false);
                            turn = !turn;//Смена хода
//                                getHistory().push(new Board(getBoard(), turn, result.getMoveType(), getKillCount()));//Запоминаем расположение
                            setKillCount(0);
                        }

                        getStepsStack().push(new Step(x0, y0, result, piece));
                        changingTurn();
                        deadPiece(killedPiece);



                        break;
                }

            } else if (!isKillNeed()) {
                switch (result.getMoveType()) {
                    case NONE:
                        piece.abortMove();
                        break;
                    case NORMAL:
                        piece.move(newX, newY);
                        getBoard()[x0][y0].setPiece(null); // Очищаем предыдущую клетку
                        getBoard()[newX][newY].setPiece(piece);//Ставим на новую
                        if (piece.getPieceType() == Piece.PieceType.BLACK && newY == (HEIGHT - 1) ||
                                piece.getPieceType() == Piece.PieceType.WHITE && newY == 0) {
                            piece.setCrown(true);
                            piece.getCrownImgView().setVisible(true); //Стала дамкой = видно корону
                        }

//                            getHistory().push(new Board(getBoard(), turn, result.getMoveType(), getKillCount()));//Запоминаем расположение

                        getStepsStack().push(new Step(x0, y0, result, piece));
                        turn = !turn; //Смена хода
                        changingTurn();


                        break;
                }
            }
            eatAlarm(); //Напоминание о том, что нужно есть
        } else piece.abortMove(); //Если не в пределах доски - сброс

        });
    }

    public static EventHandler<MouseEvent> stepBack() {
        return e -> {
            Step step = getStepsStack().pop();

//            //Удаляем последнее состояние
//            getHistory().pop();
//            setBoard(getHistory().peek().getBoardState()); //Достаю состояние доски и присваиваю как текущее
//            turn = !turn; //Каждый ход - ход разных команд
//            //Передаем предыдущее состояние доски в отрисовщик
//            boardPainter(getHistory().peek(), false);
//            System.out.println(getHistory().size());
            switch (step.getMoveResult().getMoveType()){
                case NORMAL:
                    StepBackDrawer.normalMove(step.getX(), step.getY(), step.getPiece());
                    break;
                case KILL:
                    StepBackDrawer.killMove(step.getX(), step.getY(), step.getMoveResult());
            }

        };
    }


    public static EventHandler<MouseEvent> surrender() {
        return e -> {
            String message = turn ? "\nПоражение чёрных" : "\n Поражение белых";
            if (confirmation("Surrender", "Вы точно хотите сдаться?" + message)) {
                boardPainter(null, true);
                changingTurn();
            }

        };
    }


}
