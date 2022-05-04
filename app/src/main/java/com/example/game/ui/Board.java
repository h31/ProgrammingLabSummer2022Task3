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

public class Board extends View {

    public Board(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void drawRoundRect(Canvas canvas, int x, int y, int size, String color) {
        @SuppressLint("DrawAllocation") Paint paint = new Paint();
        paint.setColor(Color.parseColor(color));
        @SuppressLint("DrawAllocation") RectF rect = new RectF();
        rect.set(x, y, x + size, y + size);
        canvas.drawRoundRect(rect, (float) (getWidth() * 0.05), (float) (getWidth() * 0.05), paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int indent = (int) (getWidth() * 0.05);
        drawRoundRect(canvas, indent, getHeight() - getWidth(), getWidth() - indent * 2, "#bbada0");

    }
}
