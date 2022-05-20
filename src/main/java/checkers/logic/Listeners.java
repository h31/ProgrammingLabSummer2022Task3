package checkers.logic;


import checkers.ui.ContentCreator;
import checkers.ui.Piece;
import checkers.ui.StepBackDrawer;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;



import static checkers.logic.Logic.*;

import static checkers.ui.ConfirmBox.confirmation;
import static checkers.ui.ContentCreator.*;
import static checkers.ui.Piece.deadPiece;


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

                        //Если шашка уже дамка до проверки, то она стала ей не в последнем ходу
                        if(piece.isCrown()) piece.setCrownedLastTurn(false);

                        //Следим за превращением в дамку
                        if (piece.getPieceType() == Piece.PieceType.BLACK && newY == (HEIGHT - 1) && !piece.isCrown() ||
                                piece.getPieceType() == Piece.PieceType.WHITE && newY == 0 && !piece.isCrown()) {
                            piece.setCrownedLastTurn(true);
                            piece.setCrown(true);
                            piece.getCrownImgView().setVisible(true); //Стала дамкой = видно корону
                            //Передаем результату инф-ию, было ли в этом ходу перевоплощение в шашку
                            result.setWasCrowned(true);
                        }

                        //Запоминаем
                        getStepsStack().push(new Step(x0, y0, result, piece));

                        getBoard()[x0][y0].setPiece(null); // Очищаем предыдущую клетку
                        getBoard()[newX][newY].setPiece(piece); //Ставим на новую

                        piece.move(newX, newY);
                        Piece killedPiece = result.getPiece(); // Убитая шашка
                        //По координатам убитой шашки удаляем её из board
                        getBoard()[toBoard(killedPiece.getOldX())][toBoard(killedPiece.getOldY())].setPiece(null);
                        //Очищаем с поля
                        getPieceGroup().getChildren().remove(killedPiece);

                        if (canKill(piece, newX, newY)) { //Если после хода шашка может убить ещё,
                            // появляется флаг killer и ход не переходит
                            piece.setKiller(true);
                            setKillCount(getKillCount() + 1);


                        } else {
                            setKillNeed(false);
                            piece.setKiller(false);
                            turn = !turn;//Смена хода
                            setKillCount(0);
                        }

                        changingTurn();
                        deadPiece(killedPiece);

                        if(getLeft().getChildren().size() == 12 || getRight().getChildren().size()==12){
                            String message = turn ? "\n          Победа белых" :
                                                    "\n          Победа чёрных";
                            if (confirmation("End of the game", "Хотите начать игру заново?" + message)) {
                                boardPainter();
                                changingTurn();
                            }

                        }


                        break;
                }

            } else if (!isKillNeed()) {
                switch (result.getMoveType()) {
                    case NONE:
                        piece.abortMove();
                        break;
                    case NORMAL:
                        //Если шашка уже дамка до проверки, то она стала ей не в последнем ходу
                        if(piece.isCrown()) piece.setCrownedLastTurn(false);

                        //Следим за превращением в дамку
                        if (piece.getPieceType() == Piece.PieceType.BLACK && newY == (HEIGHT - 1) && !piece.isCrown() ||
                                piece.getPieceType() == Piece.PieceType.WHITE && newY == 0 && !piece.isCrown()) {
                            piece.setCrownedLastTurn(true);
                            piece.setCrown(true);
                            piece.getCrownImgView().setVisible(true); //Стала дамкой = видно корону
                            result.setWasCrowned(true);
                        }
                        //Запоминаем расположение
                        getStepsStack().push(new Step(x0, y0, result, piece));

                        getBoard()[x0][y0].setPiece(null); // Очищаем предыдущую клетку
                        getBoard()[newX][newY].setPiece(piece);//Ставим на новую

                        piece.move(newX, newY);

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
            if (getStepsStack().size()!=0) {
                Step step = getStepsStack().pop();

                switch (step.getMoveResult().getMoveType()) {
                    case NORMAL:
                        //Очищаем клетку
                        getBoard()[toBoard(step.getPiece().getLayoutX())][toBoard(step.getPiece().getLayoutY())].setPiece(null);
                        turn = !turn;
                        StepBackDrawer.normalMove(step);

                        getBoard()[toBoard(step.getPiece().getOldX())][toBoard(step.getPiece().getOldY())].setPiece(step.getPiece());
                        setKillNeed(false);
                        break;
                    case KILL:

                        Piece killedPiece = step.getMoveResult().getPiece();
                        Piece killerPiece = step.getPiece();
                        //Восстанавливаем убитого
                        getBoard()[toBoard(killedPiece.getOldX())][toBoard(killedPiece.getOldY())].setPiece(killedPiece);
                        //Очищаем клетку занимаемую убившим
                        getBoard()[toBoard(killerPiece.getOldX())][toBoard(killerPiece.getOldY())].setPiece(null);
                        //Возвращаем шашку в предыдущую клетку
                        getBoard()[step.getX()][step.getY()].setPiece(killerPiece);

                       //Смена хода, если шашка ела подряд и не завершила,
                        // то не меняем ход. Логика по типу: убийца съел - значит точно был его ход
                        turn = step.getPiece().getPieceType() != Piece.PieceType.WHITE;

                        StepBackDrawer.killMove(step);

                        setKillNeed(true);
                        setKillCount(getKillCount()>0 ? getKillCount() -1 : 0);


                }

                //Если шашка стала дамкой в отмененном ходу, она перестает быть дамкой
                //Отрисовку сделаю в ui.StepBackDrawer
                if(step.getMoveResult().WasCrowned()) {
                    step.getPiece().setCrown(false);
                }
            }

        };
    }


    public static EventHandler<MouseEvent> surrender() {
        return e -> {
            String message = turn ? "\n      Поражение чёрных" : "\n      Поражение белых";
            if (confirmation("Surrender", "Вы точно хотите сдаться?" + message)) {
                boardPainter();
                changingTurn();
                getStepsStack().clear();
            }
        };
    }

    public static EventHandler<MouseEvent> start(Stage stage){
        return e ->{
          ContentCreator.createContent();
          stage.close();
        };
    }


}
