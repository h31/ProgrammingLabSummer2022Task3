package com.example.game.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.example.game.R;
import com.example.game.core.Coordinate;
import com.example.game.core.Direction;
import com.example.game.core.Game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class GameActivity extends AppCompatActivity {

    private ConstraintLayout layout;
    private Board board;
    private Game game;
    private View swipeDetector;
    private final int durationAnimations = 100;
    private final Map<Coordinate, Square> squares = new HashMap<>();
    private final String TAG = this.getClass().getSimpleName();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setViews();
        swipesOff();
        // Ждет пока пройдет анимация появления поля
        board.postDelayed(() -> {
            game = new Game(Board.BOARD_SIZE);
            Log.i(TAG, "Game started");
            spawnSquare();
            // Возвращает возможность свайпать после спавна квадрата
            layout.postDelayed(this::swipesOn, durationAnimations);
        }, durationAnimations);
        // Устанавливает считывание свайпов
        swipeDetector.setOnTouchListener(new OnSwipeTouchListener(GameActivity.this) {
            public void onSwipeTop() {
                Log.i(TAG, "Top swipe");
                doIteration(game.doMove(Direction.UP));
            }

            public void onSwipeRight() {
                Log.i(TAG, "Right swipe");
                doIteration(game.doMove(Direction.RIGHT));
            }

            public void onSwipeLeft() {
                Log.i(TAG, "Left swipe");
                doIteration(game.doMove(Direction.LEFT));
            }

            public void onSwipeBottom() {
                Log.i(TAG, "Down swipe");
                doIteration(game.doMove(Direction.DOWN));
            }
        });
    }

    private void doIteration(@NonNull Set<Coordinate.Move> moves) {
        Log.i(TAG, "\tMoves:" + moves);
        if (moves.isEmpty()) return;
        swipesOff();
        Set<Pair<Square, Square>> mergedSquares = new HashSet<>();
        Log.i(TAG, "\tSquares to merge:");
        for (Coordinate.Move move : moves) {
            // Координаты и фигура, которая двигается
            Square squareFrom = squares.get(move.from);
            // Координаты и фигура, на которую двигаются
            Square squareTarget = squares.get(move.to);
            Pair<Integer, Integer> squareTargetCoordinate = board.getCoordinate(move.to.x, move.to.y);
            // Анимация перемещения к таргету
            Objects.requireNonNull(squareFrom).animate().x(squareTargetCoordinate.first).
                    y(squareTargetCoordinate.second).setDuration(durationAnimations);
            // В любом случае убираем объект, который двигался
            squares.remove(move.from);
            // Если произошло слияние, то оставляем на потом, если нет, то просто добовляем фигуру в сет
            if (squareTarget != null) {
                Log.i(TAG, "\t\t" + move.from + "=" + squareFrom.getNumber() +
                        "; " + move.to + "=" + squareFrom.getNumber());
                mergedSquares.add(Pair.create(squareFrom, squareTarget));
            } else {
                squares.put(move.to, squareFrom);
            }
        }
        layout.postDelayed(() -> {
            for (Pair<Square, Square> square : mergedSquares) {
                layout.removeView(square.first);
                Animation scaleAnimation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                        square.second.getX() + (float) board.getSquareSize() / 2,
                        square.second.getY() + (float) board.getSquareSize() / 2);
                scaleAnimation.setDuration(durationAnimations / 2);
                scaleAnimation.setRepeatMode(Animation.REVERSE);
                scaleAnimation.setRepeatCount(1);
                square.second.startAnimation(scaleAnimation);
                square.second.setNumber(square.second.getNumber() * 2);
            }
            spawnSquare();
            layout.postDelayed(this::swipesOn, durationAnimations);
        }, durationAnimations);
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
        squares.put(squareCoordinate.first, square);
        square.startAnimation(animation);
        Log.i(TAG, "\tSquare " + squareCoordinate.second +
                " spawned at: " + squareCoordinate.first);
    }

    /**
     * Включает View, отвечающий за считывание свайпов
     */
    private void swipesOn() {
        swipeDetector.setVisibility(View.VISIBLE);
    }

    /**
     * Отключает View, отвечающий за считывание свайпов
     */
    private void swipesOff() {
        swipeDetector.setVisibility(View.INVISIBLE);
    }

    /**
     * Присваивает объектам их воплощения на рисунке
     */
    private void setViews() {
        layout = findViewById(R.id.layout);
        board = findViewById(R.id.board);
        swipeDetector = findViewById(R.id.swipeDetector);
    }
}