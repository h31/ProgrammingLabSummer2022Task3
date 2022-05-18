package checkers.logic;

import checkers.ui.Piece;
import checkers.ui.Tile;

public class BoardSaver extends Thread {
    private final int x,y;
    private static Tile[][] copyBoard;
    private static Tile[][] originalBoard;

    public static void setOriginalBoard(Tile[][] originalBoard) {
        BoardSaver.originalBoard = originalBoard;
    }

    public static Tile[][] getCopyBoard() {
        return copyBoard;
    }

    public static void setCopyBoard(Tile[][] copyBoard) {
        BoardSaver.copyBoard = copyBoard;
    }



    public BoardSaver(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public void run(){
       for (int y = 0; y < Logic.HEIGHT; y++) {
           Tile tile = new Tile((x + y) % 2 == 0, x, y);
                if (originalBoard[x][y].hasPiece()) {
                    tile.setPiece(new Piece(originalBoard[x][y].getPiece().getPieceType(), x, y));
                }
                copyBoard[x][y] = tile;
           
       }
   }
}
