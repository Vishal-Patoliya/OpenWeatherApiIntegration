package com.openweatherapp.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Base Fragment Implementation.
 * @author Vishal
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        setListener();
        process();
    }

    /**
     * Initialise class member.
     * @param view parent view.
     */
    public abstract void init(View view);

    /**
     * Set listener event for class member.
     */
    public abstract void setListener();

    /**
     * Execute initialisation process.
     */
    public abstract void process();

    /**
     * Call when get any update from activity
     */
    public abstract void onUpdate(Object object);

    public boolean onBackPressed(){
        return false;
    }

    /**
     * This method is call from Activity in Fragment is attached and In activity it calls when Time or TimeZone or Date updated
     * @param intent intent data which receive from BroadcastReceiver override method onReceive
     */
    public void onSettingsChanged(Intent intent) {}
}
