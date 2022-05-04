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

    @Override
    protected void onDraw(Canvas canvas) {
        @SuppressLint("DrawAllocation") Paint paint = new Paint();
        paint.setColor(Color.parseColor("#bbada0"));
        @SuppressLint("DrawAllocation") RectF rect = new RectF();
        rect.set(0, 0, getWidth(), getWidth());
        canvas.drawRoundRect(rect, 100, 100, paint);
    }
}
