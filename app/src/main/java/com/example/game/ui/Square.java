package com.example.game.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class Square extends View {

    private int number = 0;
    private int size = 0;

    public Square(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Square(Context context) {
        super(context);
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (number != 0) {
            @SuppressLint("DrawAllocation") Paint paint = new Paint();
            paint.setColor(Color.parseColor("#F00FAA"));
            @SuppressLint("DrawAllocation") RectF rect = new RectF();
            rect.set(0, 0, size, size);
            canvas.drawRoundRect(rect, (float) (getWidth() * 0.03), (float) (getWidth() * 0.03), paint);
        }
    }
}
