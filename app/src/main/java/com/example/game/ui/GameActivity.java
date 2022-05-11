package com.example.game.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.example.game.R;
import com.example.game.core.Coordinate;
import com.example.game.core.Direction;
import com.example.game.core.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GameActivity extends AppCompatActivity {

    private ConstraintLayout layout;
    private Board board;
    private Game game;
    private View swipeDetector;
    private TextView score;

    // Пары {Координата на поле, View на этой координате}
    private final Map<Coordinate, Square> squares = new HashMap<>();

    // Константа для отсчета длительностей анимаций
    private final int durationAnimations = 50;
    // Тег для логирования
    private final String TAG = this.getClass().getSimpleName();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setViews();
        // Устанавливает значение очков на 0
        score.setText("0");
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

    private void doIteration(@NonNull List<Coordinate.Move> moves) {
        Log.i(TAG, "\tMoves:" + moves);
        if (moves.isEmpty()) return;
        // Отключаем возможность движения
        swipesOff();
        // Лист квадратов, которые совместились
        List<Pair<Square, Square>> mergedSquares = new ArrayList<>();
        Log.i(TAG, "\tSquares to merge:");
        for (Coordinate.Move move : moves) {
            // Координаты на поле и квадрат, который двигается
            Square squareFrom = squares.get(move.from);
            // Координаты на поле и клетка, на которую двигаются
            Square squareTarget = squares.get(move.to);
            // Координаты на layout клетки, на которую двигают
            Pair<Integer, Integer> squareTargetCoordinate = board.getCoordinate(move.to.x, move.to.y);
            // Анимация перемещения к таргету
            Objects.requireNonNull(squareFrom).animate().x(squareTargetCoordinate.first).
                    y(squareTargetCoordinate.second).setDuration(durationAnimations);
            // Убираем из мапы координаты с которых двигалась фигура
            squares.remove(move.from);
            // Проверка, было ли слияние
            if (squareTarget != null) {
                Log.i(TAG, "\t\t" + move.from + "=" + squareFrom.getNumber() +
                        "; " + move.to + "=" + squareFrom.getNumber());
                // Добавляем в лист квадратов, которые совместились
                mergedSquares.add(Pair.create(squareFrom, squareTarget));
            } else {
                // Просто поменяли координату клетки
                squares.put(move.to, squareFrom);
            }
        }
        // Запускаем после всех анимаций перемещения
        layout.postDelayed(() -> {
            int maxNumber = 0;
            for (Pair<Square, Square> square : mergedSquares) {
                // Удаляем один из совмещенных квадратов
                layout.removeView(square.first);
                // Анимируем второй, чтоб он увеличился и обратно уменьшился
                Animation scaleAnimation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                        square.second.getX() + (float) board.getSquareSize() / 2,
                        square.second.getY() + (float) board.getSquareSize() / 2);
                scaleAnimation.setDuration(durationAnimations / 2);
                scaleAnimation.setRepeatMode(Animation.REVERSE);
                scaleAnimation.setRepeatCount(1);
                square.second.startAnimation(scaleAnimation);
                // Меняем цифру на квадрате
                int newNumber = square.second.getNumber() * 2;
                maxNumber = Math.max(maxNumber, newNumber);
                square.second.setNumber(square.second.getNumber() * 2);
            }
            spawnSquare();
            int finalMaxNumber = maxNumber;
            layout.postDelayed(() -> {
                if (finalMaxNumber == 2048)
                    Log.d(TAG, "Win");
                else if (game.gameIsLost())
                    Log.d(TAG, "Lose");
                else
                    swipesOn();
            }, durationAnimations);
        }, durationAnimations);
        updateScore();
        Log.d(TAG, "\tBoard after move:" + squares + squares.size());
    }

    /**
     * Создает новую Square с координатами из game.spawnSquare().
     * Длительность анимации durationAnimations * 6, ожидать анимацию не обязателньно
     */
    private void spawnSquare() {
        Square square = new Square(this);
        Pair<Coordinate, Integer> squareCoordinate = game.spawnSquare();
        Pair<Integer, Integer> squarePosition =
                board.getCoordinate(squareCoordinate.first.x, squareCoordinate.first.y);
        // Поставили клетку в правильное положение на доску
        square.setX(squarePosition.first);
        square.setY(squarePosition.second);
        // Установили клетке размер
        square.setSize(board.getSquareSize());
        // Установили цифру
        square.setNumber(squareCoordinate.second);
        // Создание анимации
        Animation animation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                squarePosition.first + (float) board.getSquareSize() / 2,
                squarePosition.second + (float) board.getSquareSize() / 2);
        animation.setDuration(durationAnimations * 6);
        animation.setInterpolator(new AccelerateInterpolator());
        // Добавляем квадрат на поле
        layout.addView(square);
        squares.put(squareCoordinate.first, square);
        // Запуск анимации
        square.startAnimation(animation);
        Log.i(TAG, "\tSquare " + squareCoordinate.second +
                " spawned at: " + squareCoordinate.first);
    }

    /**
     * Функция для смены счета
     */
    private void updateScore() {
        ValueAnimator valueAnimator =
                ValueAnimator.ofInt(Integer.parseInt(String.valueOf(score.getText())), game.getScore());
        valueAnimator.setDuration(durationAnimations * 2);
        valueAnimator.addUpdateListener(animatorValue ->
                score.setText(animatorValue.getAnimatedValue().toString()));
        valueAnimator.start();
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
        score = findViewById(R.id.scoreNumber);
    }
}