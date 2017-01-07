package com.madchen.worldclock.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.madchen.worldclock.R;

/**
 * Created by chenwei on 2017/1/7.
 */

public class DialPlateView extends View {

    private int backgroundColor;
    private int cityNameTextSize;
    private int timeTextSize;
    private int dateTextSize;
    private String cityNameText;
    private String timeText;
    private String dateText;
    private int plateRadius;
    private Paint mPaint;
    private TextPaint mTextPaint;
    private int textColor;

    public DialPlateView(Context context) {
        this(context, null);
    }

    public DialPlateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DialPlateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DialPlateView);
        if (typedArray != null) {
            backgroundColor = typedArray.getColor(R.styleable.DialPlateView_BackgroundColor, getResources().getColor(R.color.defaultBackgroundColor));
            cityNameTextSize = typedArray.getInt(R.styleable.DialPlateView_CityNameTextSize, R.dimen.default_city_name_text_size);
            timeTextSize = typedArray.getInt(R.styleable.DialPlateView_TimeTextSize, R.dimen.default_time_text_size);
            dateTextSize = typedArray.getInt(R.styleable.DialPlateView_DateTextSize, R.dimen.default_city_name_text_size);
            cityNameText = typedArray.getString(R.styleable.DialPlateView_CityNameText);
            timeText = typedArray.getString(R.styleable.DialPlateView_TimeText);
            dateText = typedArray.getString(R.styleable.DialPlateView_DateText);
            plateRadius = typedArray.getInt(R.styleable.DialPlateView_PlateRadius, R.dimen.default_plate_radius);
            textColor = typedArray.getColor(R.styleable.DialPlateView_TextColor, getResources().getColor(R.color.defaultTextColor));
            typedArray.recycle();
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(backgroundColor);
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(textColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, plateRadius, mPaint);
        float cityNameTextLength = mPaint.measureText(cityNameText);
        mPaint.setColor(textColor);
        mPaint.setTextSize(cityNameTextSize);
        canvas.drawText(cityNameText, (getWidth() / 2 - cityNameTextLength) / 2, getHeight() / 7, mPaint);

        //绘制时间
        mPaint.setTextSize(timeTextSize);
        int timeTextLength = (int) mPaint.measureText(timeText);
        int timeTextHigh = (int) (Math.ceil(mPaint.getFontMetrics().descent - mPaint.getFontMetrics().ascent) + 1);
        canvas.drawText(timeText, (getWidth() / 2 - timeTextLength) / 2, (getHeight() / 2 - timeTextHigh / 2), mPaint);

        //绘制日期
        mPaint.setTextSize(dateTextSize);
        int dateTextWidth = (int) mPaint.measureText(dateText);
        int dateTextHigh = (int) (Math.ceil(mPaint.getFontMetrics().descent - mPaint.getFontMetrics().ascent) + 1);
        canvas.drawText(dateText, (getWidth() / 2 - dateTextWidth / 2), (getHeight() * 6 / 7), mPaint);
    }
}
