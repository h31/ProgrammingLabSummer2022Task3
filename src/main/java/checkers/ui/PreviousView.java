package checkers.ui;

import checkers.logic.Board;

import static checkers.logic.Logic.*;
import static checkers.logic.Logic.getBoard;
import static checkers.ui.ContentCreator.*;

public class PreviousView implements Runnable {

    Board previousBoard;

    public PreviousView(Board previousBoard) {
        this.previousBoard = previousBoard;
    }

    @Override
    public void run() {
        getPieceGroup().getChildren().clear();

        setBoard(previousBoard.getBoardState());
        turn = !turn;
        changingTurn();


        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {

                Piece piece;
                if (getBoard()[x][y].hasPiece()) {

                    piece = getBoard()[x][y].getPiece();

                    getRight().getChildren().removeIf(node -> (node.equals(piece)));
                    getLeft().getChildren().removeIf(node -> (node.equals(piece)));
                    System.out.println(piece.getPieceType().toString() + x + y);
                    getPieceGroup().getChildren().add(piece);

                }
            }
        }

    }
}
