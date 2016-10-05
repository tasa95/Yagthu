package com.app.android.yagthu.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.app.android.yagthu.fragments.FragmentCheck;

/**
 * Object: Animation of canvas
 * Used by: FragmentCheck
 *
 * @author Fllo (Florent Blot) & Tasa (Thierry Allard Saint Albin)
 * @version 1.0
 */
public class LoadingView extends View {

    private Context context;
    private Paint paint;
    private int width, height, color,
            index; // 0: Back - 1: Light - 2: Blue
    private int cornerX, cornerY;
    private static int margins = 100;

    // Constructors
    public LoadingView(Context context_) {
        super(context_);
    }

    public LoadingView(Context context_, int width_, int height_, int color_, int index_) {
        super(context_);
        this.context = context_;
        this.width = width_ - margins;
        this.height = height_ - margins;
        this.color = color_;
        this.index = index_;

        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);

        switch(index) {
            case FragmentCheck.FIRST_VIEW_POSITION:
                paint.setStrokeWidth(18); break;

            case FragmentCheck.SECOND_VIEW_POSITION:
                paint.setStrokeWidth(20); break;

            case FragmentCheck.LAST_VIEW_POSITION:
                paint.setStrokeWidth(18); break;
        }

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch(index) {
            case FragmentCheck.FIRST_VIEW_POSITION:
                cornerX = 200; cornerY = 220; break;

            case FragmentCheck.SECOND_VIEW_POSITION:
                cornerX = 225; cornerY = 190; break;

            case FragmentCheck.LAST_VIEW_POSITION:
                cornerX = 177; cornerY = 215; break;
        }
        canvas.drawRoundRect(new RectF(margins, margins, width, height), cornerX, cornerY, paint);
    }
}
