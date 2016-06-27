package com.rz.alon.smartslim;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class TimerActivity extends AppCompatActivity implements TimerFragment.OnFragmentInteractionListener{

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_timer);
        fragmentManager = getSupportFragmentManager();

        if(savedInstanceState == null) {
            TimerFragment timerFragment = new TimerFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_placeholder, timerFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
