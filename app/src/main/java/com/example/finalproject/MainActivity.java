package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private Button resetButton;
    private Button polishButton;

    private TextView cmThing;
    int count;

    private TextView countDown;
    private CountDownTimer countDownTimer;
    private long timeLeftMilliSec = 604800000;
    int week = 5;
    int sec;
    int currentImage = 1;

    private static ImageView imageView;
    private static ImageView imageViewNoPolish;
    private static ImageView imageViewYesPolish;
    private static Button noPolishButton;
    private Handler mHandler = new Handler();
    int[] images = {R.drawable.troll, R.drawable.startlength, R.drawable.secondlength, R.drawable.thirdlength};
    int[] noPolish = {R.drawable.no_polish_not_chosen, R.drawable.no_polish_chosen, R.drawable.the_real_not_available};
    int[] yesPolish = {R.drawable.nail_polish_not_chosen, R.drawable.nail_polish_chosen};





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageView =  findViewById(R.id.imageView);
        imageViewNoPolish = findViewById(R.id.imageViewNoPolish);
        imageViewYesPolish = findViewById(R.id.imageViewYesPolish);


        cmThing = findViewById(R.id.cmThing);

        Thread t = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(1000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                count++;
                                double cm = count * 0.00000077;
                                DecimalFormat df = new DecimalFormat("0.00000000");
                                String cmthingText;
                                cmthingText = "Toenail growth length: " + df.format(cm) + " cm";
                                cmThing.setText(cmthingText);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();


        countDown = findViewById(R.id.countDown);
        startCounting();


        resetButton = findViewById(R.id.buttonReset);
        resetButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOpeningScreen();
                //Intent intent = new Intent(MainActivity.this, Fingerprint.class);
                //startActivity(intent);
            }
        }));


    }






    public void buttonClick () {

        noPolishButton = findViewById(R.id.buttonNoNailPolish);
        noPolishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageView.setImageResource(images[0]);
                imageViewNoPolish.setImageResource(noPolish[1]);
                imageViewYesPolish.setImageResource(yesPolish[0]);
                mHandler.postDelayed(aRunnable, 2000);

            }
        });

    }

    public void buttonNotClick() {
        noPolishButton = findViewById(R.id.buttonNoNailPolish);
        noPolishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.postDelayed(bRunnable,0);
            }
        });



    }


    public Runnable bRunnable = new Runnable() {
        @Override
        public void run() {
            imageViewNoPolish.setImageResource(noPolish[2]);
            Toast.makeText(MainActivity.this, "Option no longer available", Toast.LENGTH_SHORT).show();
        }
    };


    public Runnable aRunnable = new Runnable() {
        @Override
        public void run() {
            imageView.setImageResource(images[1]);
            imageViewNoPolish.setImageResource(noPolish[0]);
            imageViewYesPolish.setImageResource(yesPolish[1]);
            Toast.makeText(MainActivity.this, "Just kidding you get nail polish anyway", Toast.LENGTH_SHORT).show();
        }
    };


    public void openOpeningScreen() {
        Intent intent = new Intent(this, OpeningActivity.class);
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
        int hour = (int) timeLeftMilliSec % 604800000 % 86400000 / 3600000;
        int min = (int) timeLeftMilliSec % 604800000 % 86400000 % 3600000 / 60000;
        sec = (int) timeLeftMilliSec %604800000 % 86400000 % 3600000 % 60000 / 1000;
        String timeLeftText;

//        if (day == 0) {
//            week = week - 1;
//        }
        if (sec == 55) {
            week = week - 1;
            if (week < 5 && week % 2 != 0) {
                currentImage++;
                imageView.setImageResource(images[currentImage]);
            }
        }


        timeLeftText = week + " weeks " + day + " days " + hour + " hours " + min + " mins " + sec + " secs ";


        countDown.setText(timeLeftText);

        if (week > 3) {
            buttonClick();
        } else {
            buttonNotClick();
        }

        if (week == 0 && sec == 0) {
            alarm();
        }
    }

    public void alarm() {

    }

}
