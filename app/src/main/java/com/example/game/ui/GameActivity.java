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
import android.widget.Button;
import android.widget.LinearLayout;
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
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {
    // Основные поля
    private ConstraintLayout layout;
    private Board board;
    private View swipeDetector;
    private LinearLayout scoreBoard;
    // Текст на экране
    private TextView score;
    private TextView time;
    // Копки
    private Button quick;
    private Button restart;
    // Таймер
    private Timer timer;
    // Текст при конце игры
    private TextView endGameText;
    // Само игровое поле
    private Game game;
    private boolean gameIsEnd;
    // Пары {Координата на поле, View на этой координате}
    private final Map<Coordinate, Square> squares = new HashMap<>();

    // Константа для отсчета длительностей анимаций
    private final int durationAnimations = 100;
    // Тег для логирования
    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setViews();
        quick.setOnClickListener(view -> {
            Log.i(TAG, "Quick button pressed");
            finish();
        });
        restart.setOnClickListener(view -> {
            Log.i(TAG, "Restart button pressed");
            restart();
        });
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
        start();
    }

    /**
     * Функция, отображающая действия после свапа
     *
     * @param moves перемещения, которые получены при свайпе из core части
     */
    private void doIteration(@NonNull List<Coordinate.Move> moves) {
        Log.i(TAG, "\tMoves:" + moves);
        if (moves.isEmpty()) return;
        // Отключаем возможность движения
        interactionsOff();
        // Лист квадратов, которые совместились
        List<Pair<Square, Square>> mergedSquares = moveSquares(moves);
        // Запускаем после всех анимаций перемещения
        layout.postDelayed(() -> mergeSquares(mergedSquares), durationAnimations);
        updateScore();
        Log.d(TAG, "\tBoard after move:" + squares + squares.size());
    }

    /**
     * Перемещение квадратов на поле
     *
     * @param moves лист перемещений
     * @return лист с квадратами, которые совместились
     */
    @NonNull
    private List<Pair<Square, Square>> moveSquares(@NonNull List<Coordinate.Move> moves) {
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
        return mergedSquares;
    }

    /**
     * Совмещение квадратов на поле
     *
     * @param mergedSquares лист квадратов, которые необходимо совместить
     */
    private void mergeSquares(@NonNull List<Pair<Square, Square>> mergedSquares) {
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
        // Запускаем после всех объединений
        layout.postDelayed(() -> {
            if (finalMaxNumber == 2048) {
                Log.i(TAG, "Win");
                endGame("You won game!");
            } else if (game.gameIsLost()) {
                Log.i(TAG, "Lose");
                endGame("You lost game!");
            } else
                interactionsOn();
        }, durationAnimations);
    }

    /**
     * Создает новую Square с координатами из game.spawnSquare().
     * Длительность анимации durationAnimations * 2, ожидать анимацию не обязателньно
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
        animation.setDuration(durationAnimations * 2);
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
     * Опускает вниз score panel
     *
     * @param text текст, который вывод над опущенной score panel
     */
    private void endGame(String text) {
        // TODO сделать нормальное маштабирование
        timer.cancel();
        scoreBoard.animate().y((float) (scoreBoard.getY() + layout.getHeight() * 0.12)).
                setDuration(durationAnimations * 3);
        layout.postDelayed(() -> {
            endGameText.setText(text);
            gameIsEnd = true;
            restart.setClickable(true);
        }, durationAnimations * 3);
    }

    /**
     * Делает подготовления для старта игры
     */
    @SuppressLint("SetTextI18n")
    private void start() {
        gameIsEnd = false;
        // Устанавливает значение очков на 0
        score.setText("0");
        // Устанавливает время на 00:00
        time.setText("00:00");
        // Ждет пока пройдет анимация появления поля
        board.postDelayed(() -> {
            game = new Game(Board.BOARD_SIZE);
            Log.i(TAG, "Game started");
            spawnSquare();
            // Возвращает возможность свайпать после спавна квадрата
            layout.postDelayed(this::interactionsOn, durationAnimations);
            timer = new Timer();
            timer.schedule(new TimerTask() {
                private int timeNow = 0;

                @SuppressLint("DefaultLocale")
                @Override
                public void run() {
                    timeNow++;
                    if (timeNow > 6000) timeNow = 0;
                    time.setText(String.format("%02d:%02d", timeNow / 60, timeNow % 60));
                }
            }, 1000, 1000);
        }, durationAnimations);
    }

    /**
     * Зануляет необходимые вещи перед рестартом
     */
    private void restart() {
        interactionsOff();
        if (gameIsEnd) {
            scoreBoard.animate().y((float) (scoreBoard.getY() - layout.getHeight() * 0.12)).
                    setDuration(durationAnimations * 3);
            endGameText.setText("");
        }
        for (Map.Entry<Coordinate, Square> i : squares.entrySet())
            layout.removeView(i.getValue());
        squares.clear();
        timer.cancel();
        start();
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
     * Включает View, отвечающий за считывание свайпов. Включает кнопку рестарта.
     */
    private void interactionsOn() {
        restart.setClickable(true);
        swipeDetector.setVisibility(View.VISIBLE);
    }

    /**
     * Отключает View, отвечающий за считывание свайпов. Отключает кнопку рестарта.
     */
    private void interactionsOff() {
        restart.setClickable(false);
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
        time = findViewById(R.id.timeNumber);
        quick = findViewById(R.id.quick);
        restart = findViewById(R.id.restart);
        scoreBoard = findViewById(R.id.scoreBoard);
        endGameText = findViewById(R.id.endGameText);
    }
}