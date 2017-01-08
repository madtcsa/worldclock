package com.madchen.worldclock.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.madchen.worldclock.R;
import com.madchen.worldclock.view.DialPlateView;

import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout worldCityLayout = (LinearLayout) findViewById(R.id.activity_main);

        DialPlateView dialPlateViewBlack = new DialPlateView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(1000,1000);
        dialPlateViewBlack.setLayoutParams(layoutParams);
        dialPlateViewBlack.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        dialPlateViewBlack.setCircleBackgroundColor(Color.BLACK);
        dialPlateViewBlack.setCityNameText("Beijing");
        dialPlateViewBlack.setShowTimeMove(true);
        dialPlateViewBlack.setTextColor(Color.WHITE);
        dialPlateViewBlack.setTimeTextSize(200);
        dialPlateViewBlack.setDateTextSize(100);
        dialPlateViewBlack.setCityNameTextSize(100);
        worldCityLayout.addView(dialPlateViewBlack,layoutParams);
        dialPlateViewBlack.start();


        DialPlateView dialPlateViewWhite = new DialPlateView(this);
         layoutParams = new LinearLayout.LayoutParams(800,800);
        layoutParams.setMargins(30,30,30,30);
        dialPlateViewWhite.setLayoutParams(layoutParams);
        dialPlateViewWhite.setTimeZone(java.util.TimeZone.getTimeZone("Europe/London"));
        dialPlateViewWhite.setCircleBackgroundColor(getResources().getColor(R.color.defaultBackgroundColor));
        dialPlateViewWhite.setCityNameText("London");
        dialPlateViewWhite.setShowTimeMove(true);
        dialPlateViewWhite.setTextColor(Color.BLACK);
        dialPlateViewWhite.setTimeTextSize(140);
        dialPlateViewWhite.setDateTextSize(90);
        dialPlateViewWhite.setCityNameTextSize(90);
        worldCityLayout.addView(dialPlateViewWhite,layoutParams);
        dialPlateViewWhite.start();
    }
}
