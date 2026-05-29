package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class SimplePaint extends View {

    private Paint paint;
    private Path currentPath;
    private ArrayList<Drawing> drawings = new ArrayList<>();
    private float startX, startY;
    private int currentColor = Color.BLACK;
    private float currentGrossura = 10f;
    private boolean isDrawing = false;
    private String modoTraco = TRACOLIVRE;

    public static final String TRACOLIVRE = "tracoLivre";
    public static final String TRACOCIRCULO = "tracoCirculo";
    public static final String TRACOQUADRADO = "tracoQuadrado";
    public static final String TRACORETA = "tracoReta";

    public void setTraco() {modoTraco = TRACOLIVRE;}
    public void setCirculo() {modoTraco = TRACOCIRCULO;}
    public void setQuadrado() {modoTraco = TRACOQUADRADO;}
    public void setReta() {modoTraco = TRACORETA;}

    private static class Drawing {
        Path path;
        Paint paint;

        Drawing(Path path, Paint paint) {
            this.path = path;
            this.paint = new Paint(paint);
        }
    }

    public SimplePaint(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupPaint();
    }

    private void setupPaint() {
        paint = new Paint();
        paint.setColor(currentColor);
        paint.setStrokeWidth(currentGrossura);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
    }

    public void setColor(int color) {
        currentColor = color;
        paint.setColor(color);
    }

    public void setGrossura(float width) {
        this.currentGrossura = width;
        paint.setStrokeWidth(width);
    }

    public void clearCanvas() {
        drawings.clear();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Drawing drawing : drawings) {
            canvas.drawPath(drawing.path, drawing.paint);
        }
        if (currentPath != null) {
            canvas.drawPath(currentPath, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTouch(x, y);
                return true;

            case MotionEvent.ACTION_MOVE:
                if (modoTraco.equals(TRACOLIVRE)) {
                    moveTouchLivre(x, y);
                } else {
                    // Para formas, apenas atualizamos a visualização durante o movimento
                    updateShapePreview(x, y);
                }
                return true;

            case MotionEvent.ACTION_UP:
                if (modoTraco.equals(TRACOLIVRE)) {
                    upTouch();
                } else {
                    finishShape(x, y);
                }
                return true;
        }
        return false;
    }

    private void startTouch(float x, float y) {
        startX = x;
        startY = y;
        currentPath = new Path();

        if (modoTraco.equals(TRACOLIVRE)) {
            currentPath.moveTo(x, y);
        }

        isDrawing = true;
        invalidate();
    }

    private void moveTouchLivre(float x, float y) {
        if (!isDrawing) return;

        float dx = Math.abs(x - startX);
        float dy = Math.abs(y - startY);

        if (dx >= 4 || dy >= 4) {
            currentPath.quadTo(startX, startY, (x + startX) / 2, (y + startY) / 2);
            startX = x;
            startY = y;
            invalidate();
        }
    }

    private void updateShapePreview(float x, float y) {
        if (!isDrawing) return;

        currentPath = new Path();

        if (modoTraco.equals(TRACOCIRCULO)) {
            float radius = (float) Math.hypot(x - startX, y - startY);
            currentPath.addCircle(startX, startY, radius, Path.Direction.CW);
        }
        else if (modoTraco.equals(TRACOQUADRADO)) {
            currentPath.addRect(startX, startY, x, y, Path.Direction.CW);
        }
        else if (modoTraco.equals(TRACORETA)) {
            currentPath.moveTo(startX, startY);
            currentPath.lineTo(x, y);
        }

        invalidate();
    }

    private void finishShape(float x, float y) {
        if (!isDrawing) return;

        Path finalPath = new Path();

        if (modoTraco.equals(TRACOCIRCULO)) {
            float radius = (float) Math.hypot(x - startX, y - startY);
            finalPath.addCircle(startX, startY, radius, Path.Direction.CW);
        }
        else if (modoTraco.equals(TRACOQUADRADO)) {
            finalPath.addRect(startX, startY, x, y, Path.Direction.CW);
        }
        else if (modoTraco.equals(TRACORETA)) {
            finalPath.moveTo(startX, startY);
            finalPath.lineTo(x, y);
        }

        Paint savedPaint = new Paint(paint);
        drawings.add(new Drawing(finalPath, savedPaint));
        currentPath = null;
        isDrawing = false;
        invalidate();
    }

    private void upTouch() {
        if (!isDrawing) return;
        Paint savedPaint = new Paint(paint);
        drawings.add(new Drawing(currentPath, savedPaint));
        currentPath = null;
        isDrawing = false;
        invalidate();
    }
}