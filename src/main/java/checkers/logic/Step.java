package checkers.logic;

import checkers.ui.Piece;

public class Step {
    private final int x;
    private final int y;
    private final MoveResult moveResult;
    private final Piece piece;

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public MoveResult getMoveResult() {
        return moveResult;
    }

    public Piece getPiece() {
        return piece;
    }

    public Step(int x, int y, MoveResult moveResult, Piece piece) {
        this.x = x;
        this.y = y;
        this.moveResult = moveResult;
        this.piece = piece;
    }
}
