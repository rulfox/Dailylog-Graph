package com.test.dailyloggraph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DailyLogGraph extends View {

    Paint textPaint;
    Paint canvasBackground;

    private int screenWidth;
    private int screenHeight;

    private int graphStartingOffset;

    public DailyLogGraph(Context context) {
        super(context);
        init();
    }

    public DailyLogGraph(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DailyLogGraph(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public DailyLogGraph(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(20);

        canvasBackground = new Paint();
        canvasBackground.setColor(Color.WHITE);
        canvasBackground.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = w;
        screenHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPaint(canvasBackground);
        drawText(canvas, getDutyStatusText());
        drawText(canvas, getUseLocalTimeText());
    }

    private void drawText(Canvas canvas, TextParams params){
        canvas.drawText(params.getText(), params.getX(), params.getY(), params.getTextPaint());

    }

    private TextParams getDutyStatusText(){
        return new TextParams(
                0,
                getHeightOfText(textPaint),
                "DUTY STATUS",
                textPaint
        );
    }

    private TextParams getUseLocalTimeText() {
        String text = "Use Local time Standard at Home Terminal";
        int widthOfText = getWidthOfText(textPaint, text);
        return new TextParams(
                (screenWidth/2) - (widthOfText/2),
                getHeightOfText(textPaint),
                text,
                textPaint
        );
    }

    private int getHeightOfText(Paint paint){
        Paint.FontMetrics fm = paint.getFontMetrics();
        int height = (int) (fm.descent - fm.ascent);
        return height;
    }

    private int getWidthOfText(Paint paint, String text){
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.width();
    }

    private int getWidthOfGraph(){
        return screenWidth - 100;
    }
}

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