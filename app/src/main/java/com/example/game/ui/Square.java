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

public class Square extends View {

    private String number = null;
    private int size = 0;

    public Square(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Square(Context context) {
        super(context);
    }

    public void setNumber(int number) {
        this.number = String.valueOf(number);
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return Integer.parseInt(number);
    }

    private String getColor() {
        switch (number) {
            case "2":
                return "#eee4da";
            case "4":
                return "#ede0c8";
            case "8":
                return "#f2b179";
            case "16":
                return "#f59563";
            case "32":
                return "#f67c5f";
            case "64":
                return "#f65e3b";
            case "128":
                return "#edcf72";
            case "256":
                return "#edcc61";
            case "512":
                return "#edc850";
            case "1024":
                return "#edc53f";
            case "2048":
                return "#edc22e";
            default:
                return "#3c3a32";
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (number != null) {
            // Рисование прямоугольника
            @SuppressLint("DrawAllocation") Paint paint = new Paint();
            paint.setColor(Color.parseColor(getColor()));
            @SuppressLint("DrawAllocation") RectF rect = new RectF();
            rect.set(0, 0, size, size);
            canvas.drawRoundRect(rect, (float) (getWidth() * 0.03), (float) (getWidth() * 0.03), paint);
            // Рисование текста
            paint.setTypeface(ResourcesCompat.getFont(getContext(), R.font.clear_sans_bold));
            paint.setColor(Color.parseColor(Integer.parseInt(number) < 8 ? "#776e65" : "#f9f6f2"));
            paint.setTextSize((int) (size * 0.6) - (int) (size * (number.length() - 1) * 0.1));
            // Для получения высоты текста
            @SuppressLint("DrawAllocation") Rect textBounds = new Rect();
            paint.getTextBounds(number, 0, number.length(), textBounds);
            canvas.drawText(number, (float) size / 2 - paint.measureText(number) / 2,
                    (float) size / 2 + (float) textBounds.height() / 2, paint);
        }
    }

    @NonNull
    @Override
    public String toString() {
        return number;
    }
}
