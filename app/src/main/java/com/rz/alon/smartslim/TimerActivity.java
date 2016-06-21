package com.rz.alon.smartslim;

import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimerActivity extends AppCompatActivity {

    //Constants
    private static final long ONE_SECOND = 100;

    //UI components
    private TextView txtTimer;
    private Button btnAdd3;
    private Button btnAdd2Half;
    private Button btnAdd3Half;
    private Button btnStop;
    private Button btnAdd15;
    private Button btnDeduct15;

    //Variables
    CountDownTimer mCounter;
    long mMillisLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        btnAdd3 = (Button) findViewById(R.id.btnAdd3);
        btnAdd3Half = (Button) findViewById(R.id.btnAdd3Half);
        btnAdd2Half = (Button) findViewById(R.id.btnAdd2Half);
        btnStop = (Button) findViewById(R.id.btnStop);
        txtTimer = (TextView) findViewById(R.id.txtTimer);
        btnAdd15 = (Button) findViewById(R.id.btn_add15);
        btnDeduct15 = (Button) findViewById(R.id.btn_deduct15);
        addClickActions();
    }

    private void addClickActions() {
        btnAdd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCounter = new ExtendedCounterDownTimer(100000, ONE_SECOND).start();
            }
        });
        btnAdd15.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mCounter = new ExtendedCounterDownTimer(mMillisLeft + 15*1000, ONE_SECOND).start();
            }
        });
    }

    class ExtendedCounterDownTimer extends CountDownTimer {

        //Default ctor
        public ExtendedCounterDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mMillisLeft = millisUntilFinished;
            txtTimer.setText(String.format("%d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours((millisUntilFinished))),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((millisUntilFinished)))));
        }

        @Override
        public void onFinish() {
            txtTimer.setText(R.string.zero_time);
        }
    }
}
