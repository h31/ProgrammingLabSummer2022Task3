package Checkers.UI;

import Checkers.logic.Turner;
import Checkers.logic.VerifierTurns;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.Objects;

public class CheckBoard {
    public Background backWhite= new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY));
    public Background backBlack= new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY));
    public Background backNo= new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY));
    public Background backGo= new Background(new BackgroundFill(Color.GOLD, CornerRadii.EMPTY, Insets.EMPTY));
    private final StackPane pane;
    private final VerifierTurns verifierTurns = new VerifierTurns();
    boolean someToEatAllWhite = false;
    boolean someToEatAllBlack = false;
    public final InfoCenter infoCenter;
    private static final byte size = 8;
    public static Check[][] checks = new Check[size][size];

    public static boolean isGame = false;
    Turner turner;

    private boolean isTurn = false;

    private int checkerTurnRow;
    private boolean checkerTurnIsKing;

    private int checkerTurnCol;
    boolean checkerSomeToEat = false;
    private byte lastX = 0;
    private String checkerTurnColor;

    private String playerTurn = "White";
    private int cntBlack = 12;
    private int cntWhite = 12;

    public CheckBoard(InfoCenter infoCenter) {
        this.infoCenter = infoCenter;
        pane = new StackPane();
        pane.setMinSize(UIConstants.APP_WIDTH, UIConstants.TILE_BOARD_HEIGHT);
        pane.setTranslateX(UIConstants.APP_WIDTH / 2);
        pane.setTranslateY((UIConstants.TILE_BOARD_HEIGHT / 2) + UIConstants.INFO_CENTER_HEIGHT);
        addAllChecks();
        turner = new Turner(infoCenter);
    }

    private void addAllChecks() {

        for (byte row = 0; row < size; row ++) {
            for(byte col = 0; col < size; col++) {
                Check check = new Check(row, col);
                check.getStackPane().setTranslateX((col * 60) - 210);
                check.getStackPane().setTranslateY((row * 60) - 210);
                pane.getChildren().add(check.getStackPane());
                checks[row][col] = check;
            }
        }
    }

    public void changePlayerTurn() {
        if (Objects.equals(playerTurn, "White")) {
            playerTurn = "Black";
        } else {
            playerTurn = "White";
        }
        infoCenter.updateMessage("Player " + playerTurn + "'s turn");
    }

    public StackPane getStackPane() {
        return pane;
    }

    public class Check {
        public boolean someToEat = false;
        public int row;
        public boolean isKing;
        public int col;
        private StackPane pane;
        public Label label;
        Rectangle border = new Rectangle();

        public String color;



        public Check(int row, int col) {
            this.row = row;
            this.col = col;

            pane = new StackPane();
            pane.setMinSize(51, 51);

            border.setWidth(51);
            border.setHeight(51);
            border.setFill(Color.TRANSPARENT);

            pane.getChildren().add(border);

            label = new Label("");
            label.setMinWidth(51);
            label.setMinHeight(51);
            label.setAlignment(Pos.CENTER);
            label.setFont(Font.font(30));
            if ((row + col) % 2 == 1) {
                if (row < 3) {
                    label.setBackground(backBlack);
                    color = "Black";
                } else
                if (row > 4) {
                    label.setBackground(backWhite);
                    color = "White";
                } else {
                    label.setBackground(backNo);
                    color = "No";
                }
            } else {
                label.setBackground(backNo);
                color = "No";
            }
            pane.getChildren().add(label);



            pane.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY) {
                    System.out.println(someToEat);
                } else if(isGame) turner.makeATurn(row, col, checks);
            });



        }

        public void delete(int row, int col) {
            Check check = checks[row][col];
            check.label.setBackground(backNo);
            check.label.setText("");
            check.color = "No";
            check.someToEat = false;
        }

        public void makeAKing(int row, int col) {
            Check check = checks[row][col];
            check.label.setText("X");
            check.isKing = true;
        }

        public void inline(int row, int col) {
            Check check = checks[row][col];
            check.label.setBackground(backGo);
        }

        public void unline(int row, int col) {
            Check check = checks[row][col];
            if (check.color.equals("White")){
                check.label.setBackground(backWhite);
            } else {
                check.label.setBackground(backBlack);
            }
        }

        public boolean isWhiteTurn() {
            boolean result;
            result = playerTurn.equals("White");
            return result;
        }

        public StackPane getStackPane() {
            return pane;
        }




    }





}
