package checkers.logic;

import checkers.ui.ContentCreator;
import checkers.ui.Piece;
import checkers.ui.StepBackDrawer;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import static checkers.logic.Logic.*;

import static checkers.ui.BoardPainter.boardPainter;
import static checkers.ui.ConfirmBox.confirmation;
import static checkers.ui.ContentCreator.*;
import static checkers.ui.Piece.deadPiece;
import static checkers.ui.changeContent.changingTurn;
import static checkers.ui.changeContent.eatAlarm;

public class Listeners {
    public static EventHandler<MouseEvent> moveStart(Piece piece) {
        return e -> {
            piece.setMouseX(e.getSceneX());
            piece.setMouseY(e.getSceneY());
        };

    }

    public static EventHandler<MouseEvent> movement(Piece piece) {
        return e -> piece.relocate(e.getSceneX() - piece.getMouseX() + piece.getStartFromX(),
                e.getSceneY() - piece.getMouseY() + piece.getStartFromY());
    }

    public static EventHandler<MouseEvent> moveEnd(Piece piece) {
        return e -> Platform.runLater(() -> {

            int newX = toBoard(piece.getLayoutX());
            int newY = toBoard((piece.getLayoutY()));

            if (newX < 0 || newX >= WIDTH // Проверка на нахождение шашки в пределах игрового поля
                    || newY < 0 || newY >= HEIGHT) {
                piece.abortMove(); //Если не в пределах доски - сброс
            }

            MoveResult result = tryMove(piece, newX, newY);

            int y0 = toBoard(piece.getStartFromY());
            int x0 = toBoard(piece.getStartFromX());

            if (isKillNeed() && !piece.isKiller()) {
                piece.abortMove(); //Если какая-то шашка должна съесть, а выбрана другая - сброс

            }
            //Отмена неправильного хода
            if (result.getMoveType() == MoveType.NONE) {
                piece.abortMove();

            }
            if (result.getMoveType() == MoveType.NORMAL && !isKillNeed() ||
                    result.getMoveType() == MoveType.KILL && isKillNeed()) { //Если шашка должна съедать дальше, эти движения не устраивают

                //Запоминаем расположение
                getStepsStack().push(new Step(x0, y0, result, piece));

                //Перемещаем на доске в логике
                move(piece, newX, newY, result);
                if (result.getMoveType() == MoveType.NORMAL) {
                    switchTurn(); //Смена хода
                    changingTurn();
                } else {
                    if (canKill(piece, newX, newY)) { //Если после хода шашка может убить ещё,
                        // появляется флаг killer и ход не переходит
                        piece.setKiller(true);
                        setKillCount(getKillCount() + 1);
                    } else {
                        setKillNeed(false);
                        piece.setKiller(false);
                        switchTurn();//Смена хода
                        setKillCount(0);
                    }
                    changingTurn();
                    deadPiece(result.getPiece());

                    if (getLeft().getChildren().size() == amountOfPieces ||
                            getRight().getChildren().size() == amountOfPieces) {
                        String message = turn == Piece.PieceType.BLACK ?
                                "\n          Победа белых" :
                                "\n          Победа чёрных";
                        if (confirmation("End of the game",
                                "Хотите начать игру заново?" +
                                        message)) {
                            boardPainter();
                            changingTurn();
                        }
                    }

                }
            }
            eatAlarm(); //Напоминание о том, что нужно есть
            Logic.anyThreat();
            Logic.allowedMovements();
        });
    }

    public static EventHandler<MouseEvent> stepBack() {
        return e -> {
            if (getStepsStack().size() != 0) {
                Step step = getStepsStack().pop();

                switch (step.getMoveResult().getMoveType()) {
                    case NORMAL:
                        //Очищаем клетку
                        getBoard()[toBoard(step.getPiece().getLayoutX())][toBoard(step.getPiece().getLayoutY())].setPiece(null);
                        switchTurn();
                        StepBackDrawer.normalMove(step);

                        getBoard()[toBoard(step.getPiece().getStartFromX())][toBoard(step.getPiece().getStartFromY())].setPiece(step.getPiece());
                        setKillNeed(false);
                        break;
                    case KILL:

                        Piece killedPiece = step.getMoveResult().getPiece();
                        Piece killerPiece = step.getPiece();
                        //Восстанавливаем убитого
                        getBoard()[toBoard(killedPiece.getStartFromX())][toBoard(killedPiece.getStartFromY())].setPiece(killedPiece);
                        //Очищаем клетку занимаемую убившим
                        getBoard()[toBoard(killerPiece.getStartFromX())][toBoard(killerPiece.getStartFromY())].setPiece(null);
                        //Возвращаем шашку в предыдущую клетку
                        getBoard()[step.getX()][step.getY()].setPiece(killerPiece);

                        //Смена хода, если шашка ела подряд и не завершила,
                        // то не меняем ход. Логика по типу: убийца съел - значит точно был его ход
                        turn = step.getPiece().getPieceType();

                        StepBackDrawer.killMove(step);

                        setKillNeed(true);
                        setKillCount(getKillCount() > 0 ? getKillCount() - 1 : 0);
                }

                //Если шашка стала дамкой в отмененном ходу, она перестает быть дамкой
                //Отрисовку сделаю в ui.StepBackDrawer
                if (step.getMoveResult().WasCrowned()) {
                    step.getPiece().setCrown(false);
                }
            }

        };
    }


    public static EventHandler<MouseEvent> surrender() {
        return e -> {
            String message = turn == Piece.PieceType.BLACK ? "\n      Поражение чёрных" : "\n      Поражение белых";
            if (confirmation("Вы точно хотите сдаться?", message)) {
                setTurn(Piece.PieceType.WHITE); //Чтобы при перезапуске белые ходили всегда первыми
                boardPainter();
                changingTurn();
                getStepsStack().clear();
            }
        };
    }

    public static EventHandler<MouseEvent> start(Stage stage) {
        return e -> {
            ContentCreator.createContent();
            stage.close();
        };
    }

    public static EventHandler<WindowEvent> closeProgram(Stage stage) {
        return e -> {
            e.consume(); //Без этого закрывается всегда

            if (confirmation("Вы точно хотите закрыть приложение?", "")) {
                stage.close();
            }

        };
    }


}
