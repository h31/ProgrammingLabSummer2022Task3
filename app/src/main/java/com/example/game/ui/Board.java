package com.example.game.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.core.util.Pair;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * View, который рисует поле для игры в самом низу экрана (с поправкой на отступы).
 * Хранит в себе полезную информацию о расположении фигур на поле
 */
public class Board extends View {
    // Размер поля
    final static public int BOARD_SIZE = 4;
    // Пары координат на поле и координат на layout
    // TODO - переделать map в отдельный метод
    final private Map<Pair<Integer, Integer>, Pair<Integer, Integer>> map = new HashMap<>();
    // Размер клетки
    private int squareSize;

    public Board(Context context) {
        super(context);
    }

    public Board(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Рисует квадрат с закругленными краями
     *
     * @param x     координата x левого врехнего угла
     * @param y     координата y левого врехнего угла
     * @param size  длинна грани
     * @param color цвет квадрата
     */
    private void drawRoundRect(@NonNull Canvas canvas, int x, int y, int size, @NonNull String color) {
        @SuppressLint("DrawAllocation") Paint paint = new Paint();
        paint.setColor(Color.parseColor(color));
        @SuppressLint("DrawAllocation") RectF rect = new RectF();
        rect.set(x, y, x + size, y + size);
        canvas.drawRoundRect(rect, (float) (getWidth() * 0.03), (float) (getWidth() * 0.03), paint);
    }

    /**
     * @return координаты на layout по коориданатам на поле
     */
    public Pair<Integer, Integer> getCoordinate(int x, int y) {
        return map.get(Pair.create(x, y));
    }

    /**
     * @return размер клетки
     */
    public int getSquareSize() {
        return squareSize;
    }

    // Функция для рисования самого поля
    @Override
    protected void onDraw(Canvas canvas) {
        int indent = (int) (getWidth() * 0.04);
        int sizeBoard = getWidth() - indent * 2;
        drawRoundRect(canvas, indent, getHeight() - getWidth(), sizeBoard, "#bbada0");
        int indentSquares = (int) (sizeBoard * 0.03);
        squareSize = (sizeBoard - indentSquares * (BOARD_SIZE + 1)) / BOARD_SIZE;
        for (int y = 0; y < BOARD_SIZE; y++)
            for (int x = 0; x < BOARD_SIZE; x++) {
                map.put(Pair.create(x, y), Pair.create(indent + indentSquares * (x + 1) + squareSize * x,
                        getHeight() - getWidth() + indentSquares * (y + 1) + squareSize * y));
                drawRoundRect(canvas, Objects.requireNonNull(map.get(Pair.create(x, y))).first,
                        Objects.requireNonNull(map.get(Pair.create(x, y))).second, squareSize, "#cdc1b4");
            }
    }
}
