package com.test.dailyloggraph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DailyLogGraph extends View {

    DutyStatus[] dutyStatusLogs;

    Paint textPaint, textPaintForHours;
    Paint canvasBackground;
    Paint linePaint, dutyStatusLinePaint;

    private int screenWidth, screenHeight;

    private float graphStartingOffset = 300;
    private float graphEndingOffset = 100;

    private float graphTopOffset = 100;
    private float graphBottomOffset = 100;

    private int hoursTopOffset = 100;

    private int graphUnitHeight = 50;

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

        textPaintForHours = new Paint();
        textPaintForHours.setColor(Color.BLACK);
        textPaintForHours.setTextSize(16);

        linePaint = new Paint();
        linePaint.setColor(Color.BLACK);

        dutyStatusLinePaint = new Paint();
        dutyStatusLinePaint.setColor(Color.RED);
        dutyStatusLinePaint.setStrokeWidth(5f);

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
        drawHoursInGraphAtTop(canvas);
        drawGraphTopBaseLine(canvas, linePaint);
        drawDutyStatusSectionLines(canvas);
        drawVerticalGraphLines(canvas);
        drawHoursInGraphAtBottom(canvas);
        drawDutyStatusLogs(canvas);
    }

    private void drawDutyStatusLogs(Canvas canvas) {
        if(dutyStatusLogs != null && dutyStatusLogs.length > 0){
            List<Coordinates> coordinates = new ArrayList<>();
            for (int i = 0; i < dutyStatusLogs.length; i++) {
                coordinates.add(new Coordinates(
                        getXCoordinates(i),
                        getYCoordinates(dutyStatusLogs[i].getValue()) - ((float) graphUnitHeight /2)
                ));
            }
            for (int i = 0; i < coordinates.size(); i++) {
                canvas.drawLine(coordinates.get(i).getX(), coordinates.get(i).getY(), coordinates.get(i).getX() + (getWidthOfGraph() / 24), coordinates.get(i).getY(), dutyStatusLinePaint);
                if(i+1 < coordinates.size()){
                    canvas.drawLine(coordinates.get(i).getX() + (getWidthOfGraph() / 24), coordinates.get(i).getY(), coordinates.get(i).getX() + (getWidthOfGraph() / 24), coordinates.get(i+1).getY(), dutyStatusLinePaint);
                }
            }
        }
    }

    private void drawVerticalGraphLines(Canvas canvas) {
        for (int hour = 0; hour <= 24; hour++){
            int x = (int) getXCoordinates(hour);
            int startY = (int) getYCoordinates(0);
            int endY = (int) getYCoordinates(4);
            canvas.drawLine(x,startY,x,endY, linePaint);
        }
    }

    private void drawDutyStatusSectionLines(Canvas canvas) {
        for (int dutyStatus = 1; dutyStatus <= 4; dutyStatus++){
            canvas.drawLine(getXCoordinates(0), getYCoordinates(dutyStatus), getXCoordinates(24), getYCoordinates(dutyStatus), linePaint);
        }
    }

    private void drawHoursInGraphAtTop(Canvas canvas){
        for (int hour = 0; hour <= 24; hour++){
            drawText(canvas, getTextParamsForHoursAtStart(hour));
        }
    }

    private void drawHoursInGraphAtBottom(Canvas canvas){
        for (int hour = 0; hour <= 24; hour++){
            drawText(canvas, getTextParamsForHoursAtEnd(hour));
        }
    }

    private void drawGraphTopBaseLine(Canvas canvas, Paint linePaint) {
        float coordinateYWithOffset = getTopYOffsetForGraphTopBaseLine();
        canvas.drawLine(getXCoordinates(0), coordinateYWithOffset, getXCoordinates(24), coordinateYWithOffset, linePaint);
    }

    //This is used to draw text opn canvas
    private void drawText(Canvas canvas, TextParams params){
        canvas.drawText(params.getText(), params.getX(), params.getY(), params.getTextPaint());
    }

    //This is used to get params for static text to be drawn on canvas
    private TextParams getDutyStatusText(){
        return new TextParams(
                0,
                getHeightOfText(textPaint),
                "DUTY STATUS",
                textPaint
        );
    }

    //This is used to get params for static text to be drawn on canvas
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

    private TextParams getTextParamsForHoursAtStart(int hour){
        int widthOfText = getWidthOfText(textPaintForHours, String.valueOf(hour));
        return new TextParams(
                (int) getXCoordinates(hour) - (widthOfText/2),
                hoursTopOffset + getHeightOfText(textPaintForHours),
                String.valueOf(hour),
                textPaintForHours
        );
    }

    private TextParams getTextParamsForHoursAtEnd(int hour){
        int widthOfText = getWidthOfText(textPaintForHours, String.valueOf(hour));
        return new TextParams(
                (int) getXCoordinates(hour) - (widthOfText/2),
                (int) getYCoordinates(4) + getHeightOfText(textPaintForHours),
                String.valueOf(hour),
                textPaintForHours
        );
    }

    //This is used to calculate height of text based on paint used
    private int getHeightOfText(Paint paint){
        Paint.FontMetrics fm = paint.getFontMetrics();
        int height = (int) (fm.descent - fm.ascent);
        return height;
    }

    //This is used to calculate width of text based on paint and text used
    private int getWidthOfText(Paint paint, String text){
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.width();
    }

    //This is used to get width of graph only
    private float getWidthOfGraph(){
        return screenWidth - graphStartingOffset - graphEndingOffset;
    }

    //This is used to get height of graph only
    private float getHeightOfGraph(){
        return screenWidth - graphStartingOffset - graphEndingOffset;
    }

    //This is used get x coordinates of hours
    private float getXCoordinates(int hour){
        return ((getWidthOfGraph() / 24) * hour) + graphStartingOffset;
    }

    private float getYCoordinates(int dutyStatusValue){
        return (graphUnitHeight * dutyStatusValue) + getTopYOffsetForGraphTopBaseLine();
    }

    private float getTopYOffsetForGraphTopBaseLine(){
        return graphTopOffset + getHeightOfText(textPaintForHours) + 10;
    }

    public void setDutyStatusLogs(DutyStatus[] dutyStatusLogs) {
        this.dutyStatusLogs = dutyStatusLogs;
    }
}