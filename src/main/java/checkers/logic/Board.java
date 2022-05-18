package checkers.logic;

import checkers.ui.Piece;
import checkers.ui.Tile;

import static java.util.Arrays.copyOf;
import static java.util.Arrays.mismatch;

public class Board {
    private Tile[][] boardState;
    private boolean currTurn;
    private MoveType moveType;
    private int killCount;

    public int getKillCount() {
        return killCount;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public void setCurrTurn(boolean currTurn) {
        this.currTurn = currTurn;
    }

    public boolean isCurrTurn() {
        return currTurn;
    }

    public Tile[][] getBoardState() {
        return boardState;
    }

    public void setBoardState(Tile[][] boardState) {
        this.boardState = boardState;

    }

    public Board(Tile[][] boardState, boolean turn, MoveType moveType, int killCount) {
        this.boardState = boardCopy(boardState);
        currTurn = turn;
        this.moveType = moveType;
        this.killCount = killCount;

    }

    public static Tile[][] boardCopy(Tile[][] boardState) {
        Tile[][] copy = new Tile[Logic.WIDTH][Logic.HEIGHT];
        BoardSaver.setCopyBoard(copy);
        BoardSaver.setOriginalBoard(boardState);
        for (int x = 0; x < boardState.length; x++) {


           for (int y = 0; y < boardState[x].length; y++) {
               BoardSaver multiThread = new BoardSaver(x,y);
               multiThread.start();
//                Tile tile = new Tile((x + y) % 2 == 0, x, y);
//                if (boardState[x][y].hasPiece()) {
//                    piece = new Piece(boardState[x][y].getPiece().getPieceType(), x, y);
//                    tile.setPiece(piece);
//                }
//                copy[x][y] = tile;
//
        }
//            int finalX = x;
//            Arrays.stream(boardState[x]).parallel().forEach(i -> {
//                for (int y = 0; y < boardState[finalX].length; y++) {
//                    copy[finalX][y] = i;
//                    if (i.hasPiece()) {
//                        copy[finalX][y].setPiece(new Piece(i.getPiece().getPieceType(), finalX, y));
//                    }
//                }
//            });


        }
        copy = BoardSaver.getCopyBoard();
        return copy;

    }

}
