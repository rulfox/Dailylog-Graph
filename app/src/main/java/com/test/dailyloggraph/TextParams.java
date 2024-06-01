package com.test.dailyloggraph;

import android.graphics.Paint;

class TextParams {
    private int x;
    private int y;
    private String text;
    private Paint textPaint;

    public TextParams(int x, int y, String text, Paint textPaint) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.textPaint = textPaint;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getText() {
        return text;
    }

    public Paint getTextPaint() {
        return textPaint;
    }
}
