package checkers;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class Checkers extends Application {
    public static final int TILE_SIZE = 75;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    private final Tile[][] board = new Tile[WIDTH][HEIGHT];

    private final Group tileGroup = new Group();
    private final Group pieceGroup = new Group();

    private Parent createContent() {
        Pane root = new Pane();
        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: #3B4248");


        bp.setCenter(tileGroup);
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.setMaxSize((WIDTH + 2) * TILE_SIZE, (HEIGHT + 2) * TILE_SIZE );

        root.getChildren().addAll(tileGroup, pieceGroup);


        for (int y = 0; y < HEIGHT; y++) { //Строю начальное поле
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                //Закрашиваю клетки нужным цветом
                board[x][y] = tile;

                tileGroup.getChildren().add(tile);

                Piece piece = null;

                if (y <= 2 && (x + y) % 2 != 0) {
                    piece = makePiece(PieceType.BLACK, x, y);
                }

                if (y >= 5 && (x + y) % 2 != 0) {
                    piece = makePiece(PieceType.WHITE, x, y);
                }

                if (piece != null) {
                    tile.setPiece(piece);
                    pieceGroup.getChildren().add(piece);
                }


            }

        }


        bp.setCenter(root);

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        VBox vbox1 = new VBox();
        vbox1.setAlignment(Pos.BOTTOM_CENTER);

        Label label1 = new Label("Игрок 1");
        label1.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        label1.setTextFill(Color.ORANGE);

        Label label2 = new Label("Игрок 2");
        label2.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        label2.setTextFill(Color.ORANGE);

        vbox.getChildren().addAll(label1);
        bp.setLeft(vbox);


        vbox1.getChildren().add(label2);
        bp.setRight(vbox1);

        bp.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(20);


        return bp;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Checkers");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.centerOnScreen();


    }

    private Piece makePiece(PieceType pieceType, int x, int y) {
        Piece piece = new Piece(pieceType, x, y);

        piece.setOnMouseReleased(e -> {

            int newX = toBoard(piece.getLayoutX());
            int newY = toBoard((piece.getLayoutY()));

            MoveResult result = tryMove(piece, newX, newY); //Вернёт результат шага и новыe координаты

            int x0 = toBoard(piece.getOldX());
            int y0 = toBoard(piece.getOldY());
            switch (result.getMoveType()){
                case NONE:
                    piece.abortMove();
                    break;
                case NORMAL:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null); // Очищаем предыдущую клетку
                    board[newX][newY].setPiece(piece); //Ставим на новую

                    break;
                case KILL: //Перешли через шашку
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null); // Очищаем предыдущую клетку
                    board[newX][newY].setPiece(piece); //Ставим на новую

                    Piece killedPiece = result.getPiece(); //
                    board[toBoard(killedPiece.getOldX())][toBoard(killedPiece.getOldY())].setPiece(null);
                    pieceGroup.getChildren().remove(killedPiece);
                    break;
            }
        });

        return piece;
    }

    private MoveResult tryMove(Piece piece, int newX, int newY) {
        if (board[newX][newY].hasPiece() || (newX + newY) % 2 == 0) {
            return new MoveResult(MoveType.NONE);
        }
        int x0 = toBoard(piece.getOldX());
        int y0 = toBoard(piece.getOldY());

        if (Math.abs(newX - x0) == 1 && newY - y0 == piece.getPieceType().moveDir) {
            return new MoveResult(MoveType.NORMAL);
        } else if (Math.abs(newX - x0) == 2 && newY - y0 == piece.getPieceType().moveDir * 2) {
            // x1 и y1 координаты "убитой" шашки. Запоминаем, чтобы стереть
            int x1 = x0 + (newX - x0) / 2;
            int y1 = y0 + (newY - y0) / 2;
            if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getPieceType() != piece.getPieceType()) {
                return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
            }
        }

        return new MoveResult(MoveType.NONE);
    }

    private int toBoard(double pixel) {
        return (int) (pixel + TILE_SIZE / 2) / TILE_SIZE;
    }
}