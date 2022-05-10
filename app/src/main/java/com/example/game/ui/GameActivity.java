package com.example.game.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;

import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.example.game.R;
import com.example.game.core.Coordinate;
import com.example.game.core.Game;

public class GameActivity extends AppCompatActivity {

    private ConstraintLayout layout;
    private Board board;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setViews();
        layout.addView(board = new Board(this));
        board.postDelayed(() -> {
            game = new Game(Board.BOARD_SIZE);
            spawnSquare();
        }, 500);
    }

    private void spawnSquare() {
        Square square = new Square(this);
        Pair<Coordinate, Integer> squareCoordinate = game.spawnSquare();
        Pair<Integer, Integer> squarePosition =
                board.getCoordinate(squareCoordinate.first.x, squareCoordinate.first.y);
        square.setX(squarePosition.first);
        square.setY(squarePosition.second);
        square.setSize(board.getSquareSize());
        square.setNumber(squareCoordinate.second);
        Animation animation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                squarePosition.first + (float) board.getSquareSize() / 2,
                squarePosition.second + (float) board.getSquareSize() / 2);
        animation.setDuration(300);
        animation.setInterpolator(new AccelerateInterpolator());
        layout.addView(square);
        square.startAnimation(animation);
    }

    private void setViews() {
        layout = (ConstraintLayout) findViewById(R.id.layout);
    }
}