package com.madchen.worldclock.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.madchen.worldclock.R;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by chenwei on 2017/1/7.
 */

public class DialPlateView extends View {

    private int circleBackgroundColor;
    private float cityNameTextSize;
    private float timeTextSize;
    private float dateTextSize;
    private String cityNameText;
    private String timeText;
    private String dateText;
    private Paint mPaint;
    private int textColor;
    private boolean isShowTimeMove = false;
    private RectF rectF;
    private java.util.TimeZone mTimeZone;

    public DialPlateView(Context context) {
        super(context);
    }

    public DialPlateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DialPlateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DialPlateView);
        if (typedArray != null) {
            circleBackgroundColor = typedArray.getColor(R.styleable.DialPlateView_BackgroundColor, getResources().getColor(R.color.defaultBackgroundColor));
            cityNameTextSize = typedArray.getDimension(R.styleable.DialPlateView_CityNameTextSize, getResources().getDimension(R.dimen.default_city_name_text_size));
            timeTextSize = typedArray.getDimension(R.styleable.DialPlateView_TimeTextSize, getResources().getDimension(R.dimen.default_time_text_size));
            dateTextSize = typedArray.getDimension(R.styleable.DialPlateView_DateTextSize, getResources().getDimension(R.dimen.default_city_name_text_size));
            cityNameText = typedArray.getString(R.styleable.DialPlateView_CityNameText);
            timeText = typedArray.getString(R.styleable.DialPlateView_TimeText);
            dateText = typedArray.getString(R.styleable.DialPlateView_DateText);
            textColor = typedArray.getColor(R.styleable.DialPlateView_TextColor, getResources().getColor(R.color.defaultTextColor));
            isShowTimeMove = typedArray.getBoolean(R.styleable.DialPlateView_IsLocationCity, false);
            typedArray.recycle();
        }
        start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int halfWidth = width / 2;
        int radius = halfWidth - 2;

        mPaint.setColor(circleBackgroundColor);
        mPaint.setStyle(Paint.Style.FILL);

        Calendar calendar = Calendar.getInstance();
        if (mTimeZone != null) {
            calendar.setTimeZone(mTimeZone);
        }
        canvas.drawCircle(halfWidth, halfWidth, radius, mPaint);

        //绘制城市名称
        mPaint.setTextSize(cityNameTextSize);
        float cityNameTextLength = mPaint.measureText(cityNameText);
        mPaint.setColor(textColor);
        canvas.drawText(cityNameText, (halfWidth - cityNameTextLength / 2), (float) (halfWidth / 2), mPaint);

        //绘制时间
        int minute = calendar.get(Calendar.MINUTE);
        timeText = calendar.get(Calendar.HOUR_OF_DAY) + ":" + (minute < 10 ? "0" + minute : minute);
        mPaint.setTextSize(timeTextSize);
        int timeTextLength = (int) mPaint.measureText(timeText);
        int timeTextHigh = (int) (Math.ceil(mPaint.getFontMetrics().bottom - mPaint.getFontMetrics().top));
        canvas.drawText(timeText, (halfWidth - timeTextLength / 2), (float) (halfWidth + timeTextHigh * 0.205), mPaint);

        //绘制日期
        dateText = (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DATE) + " " + getWeek(calendar.get(Calendar.DAY_OF_WEEK));
        mPaint.setTextSize(dateTextSize);
        int dateTextWidth = (int) mPaint.measureText(dateText);
        canvas.drawText(dateText, (halfWidth - dateTextWidth / 2), (float) (height * 0.8), mPaint);

        if (isShowTimeMove) {
            float degrees = (calendar.get(Calendar.SECOND) * 1000 + calendar.get(Calendar.MILLISECOND)) / 60000.0f;
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.RED);
            mPaint.setStrokeWidth(4);
            if (rectF == null) {
                rectF = new RectF(2, 2, getWidth() - 2, height - 2);
            }
            canvas.drawArc(rectF, -90, degrees * 360, false, mPaint);
        }
    }

    Timer mTimer = new Timer();

    TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            postInvalidate();
        }
    };

    private String getWeek(int dayOfWeek) {
        String week = "";
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                week = getResources().getString(R.string.sunday);
                break;
            case Calendar.MONDAY:
                week = getResources().getString(R.string.sunday);
                break;
            case Calendar.TUESDAY:
                week = getResources().getString(R.string.tuesday);
                break;
            case Calendar.WEDNESDAY:
                week = getResources().getString(R.string.wednesday);
                break;
            case Calendar.THURSDAY:
                week = getResources().getString(R.string.thursday);
                break;
            case Calendar.FRIDAY:
                week = getResources().getString(R.string.friday);
                break;
            case Calendar.SATURDAY:
                week = getResources().getString(R.string.saturday);
                break;
        }
        return week;
    }

    public void setCircleBackgroundColor(int circleBackgroundColor) {
        this.circleBackgroundColor = circleBackgroundColor;
    }

    public void setCityNameText(String cityNameText) {
        this.cityNameText = cityNameText;
    }

    public void setCityNameTextSize(float cityNameTextSize) {
        this.cityNameTextSize = cityNameTextSize;
    }

    public void setShowTimeMove(boolean showTimeMove) {
        isShowTimeMove = showTimeMove;
    }

    public void setDateTextSize(float dateTextSize) {
        this.dateTextSize = dateTextSize;
    }

    public void setTimeZone(java.util.TimeZone timeZone) {
        mTimeZone = timeZone;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTimeTextSize(float timeTextSize) {
        this.timeTextSize = timeTextSize;
    }

    public void start() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTimer.schedule(mTimerTask, 0, 30);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasureSize(widthMeasureSpec), getMeasureSize(heightMeasureSpec));
    }

    private int getMeasureSize(int measureSpec) {
        int size = 400;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                size = specSize;
                break;
        }
        return size;
    }
}
