package checkers.logic;

import checkers.ui.Piece;

public class MoveResult {

    private final MoveType moveType;
    private Piece piece;

    public MoveType getMoveType() {
        return moveType;
    }

    public Piece getPiece() {
        return piece;
    }

    public MoveResult (MoveType moveType){ //Используется для обычного и бракованного шага
        this.moveType = moveType;
    }

    public MoveResult(MoveType moveType, Piece piece){ //Используется для KILL - шага. Передает съеденную фигуру
        this.moveType = moveType;
        this.piece = piece;
    }
}
