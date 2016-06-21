package com.rz.alon.smartslim;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class TimerActivity extends AppCompatActivity {

    //Constants
    private static final long TIMER_STEP = 100;
    private static final long ONE_HOUR_IN_MILLIS = 60 * 60 * 1000;
    private static final long THREE_HOURS_IN_MILLIS = 3 * ONE_HOUR_IN_MILLIS;
    private static final long THREE_AND_HALF_HOURS_IN_MILLIS = 3 * ONE_HOUR_IN_MILLIS + (ONE_HOUR_IN_MILLIS / 2);
    private static final long TWO_AND_HALF_HOURS_IN_MILLIS = 2 * ONE_HOUR_IN_MILLIS + (ONE_HOUR_IN_MILLIS / 2);

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
                startNewTimer(THREE_HOURS_IN_MILLIS);
            }
        });
        btnAdd15.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startNewTimer(mMillisLeft + 15 * 60 * 1000);
            }
        });
    }

    private void startNewTimer(long milliseconds) {
        if(mCounter != null)
            mCounter.cancel();

        mCounter = new ExtendedCounterDownTimer(milliseconds, TIMER_STEP).start();
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
