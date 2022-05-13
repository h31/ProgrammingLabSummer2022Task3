package com.example.game.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.example.game.R;

import java.util.Objects;

/**
 * Квадрат, который отображается на поле
 */
public class Square extends View {

    // Число на квадрате
    private int number = 0;
    // Размер самого квадрата для рисования
    private int size = 0;

    public Square(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Square(Context context) {
        super(context);
    }

    /**
     * @param number число, которое будет на клетке
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * @param size размер стороны квадрата
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return значение на квадрате
     */
    public int getNumber() {
        return number;
    }

    /**
     * @return цвет, которого должен быть квадрат с числами (2-2^11)
     */
    private String getColor() {
        switch (number) {
            case 2:
                return "#eee4da";
            case 4:
                return "#ede0c8";
            case 8:
                return "#f2b179";
            case 16:
                return "#f59563";
            case 32:
                return "#f67c5f";
            case 64:
                return "#f65e3b";
            case 128:
                return "#edcf72";
            case 256:
                return "#edcc61";
            case 512:
                return "#edc850";
            case 1024:
                return "#edc53f";
            case 2048:
                return "#edc22e";
            default:
                return "#3c3a32";
        }
    }

    /**
     * Рисует квадрат со стороной size цвета, соответсвующего числу
     */
    private void drawRectF(@NonNull Canvas canvas) {
        // Рисование прямоугольника
        @SuppressLint("DrawAllocation") Paint paint = new Paint();
        paint.setColor(Color.parseColor(getColor()));
        @SuppressLint("DrawAllocation") RectF rect = new RectF();
        rect.set(0, 0, size, size);
        canvas.drawRoundRect(rect, (float) (getWidth() * 0.03), (float) (getWidth() * 0.03), paint);
    }

    /**
     * Рисует в центре квадрата цифру
     */
    private void drawText(@NonNull Canvas canvas) {
        @SuppressLint("DrawAllocation") Paint paint = new Paint();
        String numberToString = String.valueOf(number);
        paint.setTypeface(ResourcesCompat.getFont(getContext(), R.font.clear_sans_bold));
        paint.setColor(Color.parseColor(number < 8 ? "#776e65" : "#f9f6f2"));
        paint.setTextSize((int) (size * 0.6) - (int) (size * (numberToString.length() - 1) * 0.1));
        // Для получения высоты текста
        @SuppressLint("DrawAllocation") Rect textBounds = new Rect();
        // Отрисовка самого цвета
        paint.getTextBounds(numberToString, 0, numberToString.length(), textBounds);
        canvas.drawText(numberToString, (float) size / 2 - paint.measureText(numberToString) / 2,
                (float) size / 2 + (float) textBounds.height() / 2, paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (number != 0) {
            drawRectF(canvas);
            drawText(canvas);
        }
    }

    @NonNull
    @Override
    public String toString() {
        return String.valueOf(number);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return number == square.number && size == square.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, size);
    }
}
