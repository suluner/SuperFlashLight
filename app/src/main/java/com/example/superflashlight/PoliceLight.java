package com.example.superflashlight;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.superflashlight.widget.HideTextView;

/**
 * Created by Suluner on 2016/9/6.
 */
public class PoliceLight extends ColorLight {
    protected boolean mPoliceState;
    protected HideTextView mHideTextviewPoliceLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHideTextviewPoliceLight = (HideTextView)findViewById(R.id.textview_hide_police_light);
    }

    class PoliceThread extends Thread
    {
        public void run()
        {
            mPoliceState = true;
            while(mPoliceState)
            {
                mHandler.sendEmptyMessage(Color.BLUE);
                sleepExt(mCurrentPoliceLightInterval);
                mHandler.sendEmptyMessage(Color.BLACK);
                sleepExt(mCurrentPoliceLightInterval);
                mHandler.sendEmptyMessage(Color.RED);
                sleepExt(mCurrentPoliceLightInterval);
                mHandler.sendEmptyMessage(Color.BLACK);
                sleepExt(mCurrentPoliceLightInterval);
            }
        }
    }
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int color = msg.what;
            mUIPoliceLight.setBackgroundColor(color);
        }
    };
}
