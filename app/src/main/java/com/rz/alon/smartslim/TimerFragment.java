package com.rz.alon.smartslim;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TimerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TimerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimerFragment extends Fragment {

    //Constants
    private static final long TIMER_STEP = 100;
    private static final long ONE_HOUR_IN_MILLIS = 60 * 60 * 1000;
    private static final long THREE_HOURS_IN_MILLIS = 3 * ONE_HOUR_IN_MILLIS;
    private static final long THREE_AND_HALF_HOURS_IN_MILLIS = 3 * ONE_HOUR_IN_MILLIS + (ONE_HOUR_IN_MILLIS / 2);
    private static final long TWO_AND_HALF_HOURS_IN_MILLIS = 2 * ONE_HOUR_IN_MILLIS + (ONE_HOUR_IN_MILLIS / 2);
    public static final int FIFTEEN_MINUTES = 15 * 60 * 1000;
    public static final String WALL_TIME_LEFT = "timeLeft";

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
    boolean isTimerRunning = false;
    AppReceiver mAlarm;
    Context mAppContext;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TimerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimerFragment newInstance(String param1, String param2) {
        TimerFragment fragment = new TimerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        mAlarm = new AppReceiver();
        btnAdd3 = (Button) view.findViewById(R.id.btnAdd3);
        btnAdd3Half = (Button) view.findViewById(R.id.btnAdd3Half);
        btnAdd2Half = (Button) view.findViewById(R.id.btnAdd2Half);
        btnStop = (Button) view.findViewById(R.id.btnStop);
        txtTimer = (TextView) view.findViewById(R.id.txtTimer);
        btnAdd15 = (Button) view.findViewById(R.id.btn_add15);
        btnDeduct15 = (Button) view.findViewById(R.id.btn_deduct15);
        addClickActions();
        long wallTimeLeft = getActivity().getPreferences(Context.MODE_PRIVATE).getLong(WALL_TIME_LEFT, 0);

        if(wallTimeLeft != 0) {
            mMillisLeft = wallTimeLeft - System.currentTimeMillis();
            startTimer(mMillisLeft);
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onPause() {
        super.onPause();

        //save time
        SharedPreferences.Editor editor = getActivity().getPreferences(Context.MODE_PRIVATE).edit();
        if(mMillisLeft != 0) {
            editor.putLong(WALL_TIME_LEFT, System.currentTimeMillis() + mMillisLeft);
        }
        else {
            editor.putLong(WALL_TIME_LEFT, 0);
        }
        editor.commit();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAlarm.setAlarm(getActivity(), mMillisLeft);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private void addClickActions() {
        btnAdd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer(THREE_HOURS_IN_MILLIS);
            }
        });
        btnAdd3Half.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer(THREE_AND_HALF_HOURS_IN_MILLIS);
            }
        });
        btnAdd2Half.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer(TWO_AND_HALF_HOURS_IN_MILLIS);
            }
        });
        btnAdd15.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startTimer(mMillisLeft + FIFTEEN_MINUTES);
            }
        });
        btnDeduct15.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startTimer(mMillisLeft - FIFTEEN_MINUTES);
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTimerRunning) {
                    stopTimer();
                } else {
                    startTimer(mMillisLeft);
                }
            }
        });
    }

    private void stopTimer() {
        if(mCounter != null) {
            mCounter.cancel();
            isTimerRunning = false;
            btnStop.setText(R.string.restart);
            btnStop.setVisibility(View.VISIBLE);
        }
    }
    private void startTimer(long milliseconds) {
        if(mCounter != null) mCounter.cancel();
        mCounter = new ExtendedCounterDownTimer(milliseconds, TIMER_STEP);
        mCounter.start();
        isTimerRunning = true;
        btnStop.setText(R.string.pause);
        btnStop.setVisibility(View.VISIBLE);
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
