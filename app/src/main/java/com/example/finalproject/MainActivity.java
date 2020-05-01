package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.TimeUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button resetButton;
    private Button polishButton;
    private Button noPolishButton;

    private TextView countDown;
    private CountDownTimer countDownTimer;
    private long timeLeftMilliSec = 604800000;
    int week = 5;
    int sec;

    private static ImageView imageView;
    private static Button noPolishButton;
    private int currentImage;
    int[] images = {R.drawable.startlength, R.drawable.troll, R.drawable.startlength};

    int[] progressImages = {R.drawable.progrees2, R.drawable.progress3, R.drawable.progress_cuoi};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        countDown = findViewById(R.id.countDown);
        startCounting();


        resetButton = findViewById(R.id.buttonReset);
        resetButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOpeningScreen();
            }
        }));

        buttonClick();
    }









    public void buttonClick () {
        imageView =  findViewById(R.id.imageView);
        noPolishButton = findViewById(R.id.buttonNoNailPolish);
        noPolishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageView.setImageResource(images[1]);

                try {
                    Thread.sleep(2000);
                    imageView.setImageResource(images[0]);

                } catch (InterruptedException e) {
                    //System.err.print(e.getMessage());
                }

            }
        });

    }


    public void openOpeningScreen() {
        Intent intent = new Intent(this, OpeningActivity.class);
        startActivity(intent);
    }

    public void openTrollActivity() {
        Intent intent = new Intent(this, TrollActivity.class);
        startActivity(intent);

    }

    public void startCounting() {
        countDownTimer = new CountDownTimer(timeLeftMilliSec, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftMilliSec = l;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    public void updateTimer() {

        int day = (int) timeLeftMilliSec % 604800000 / 86400000;
        if (day == 0) {
            week = week - 1;
        }
        int hour = (int) timeLeftMilliSec % 604800000 % 86400000 / 3600000;
        int min = (int) timeLeftMilliSec % 604800000 % 86400000 % 3600000 / 60000;
        sec = (int) timeLeftMilliSec %604800000 % 86400000 % 3600000 % 60000 / 1000;
        String timeLeftText;

        //timeLeftText = week + " weeks " + days + " day " + hour + " hour " + min + " min " + sec + " sec ";
        timeLeftText = week + " week " + day + " day " + hour + " hour " + min + " min " + sec + " sec ";

        countDown.setText(timeLeftText);
    }

}
