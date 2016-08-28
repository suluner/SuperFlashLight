package com.example.superflashlight;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Created by Suluner on 2016/8/27.
 */
public class WarningLight extends FlashLight {
    protected boolean mWarningLightFlicker;   //true 闪烁 false 不闪烁
    protected boolean ismWarningLightState;   //true on-off false off-on

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWarningLightFlicker = true;
    }

    class WarningLightThread extends Thread
    {
        public void run()
        {
            mWarningLightFlicker = true;
            while (mWarningLightFlicker)
            {
                try{
                    Thread.sleep(300);
                    mWarningHandler.sendEmptyMessage(0);
                }
                catch (Exception e)
                {

                }
            }
        }
    }
    private Handler mWarningHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            if (ismWarningLightState)
            {
                mImageViewWarningLight1.setImageResource(R.drawable.warning_light_on);
                mImageViewWarningLight2.setImageResource(R.drawable.warning_light_off);
                ismWarningLightState = false;
            }
            else
            {
                mImageViewWarningLight1.setImageResource(R.drawable.warning_light_off);
                mImageViewWarningLight2.setImageResource(R.drawable.warning_light_on);
                ismWarningLightState = true;
            }
            super.handleMessage(msg);
        }
    };
}
