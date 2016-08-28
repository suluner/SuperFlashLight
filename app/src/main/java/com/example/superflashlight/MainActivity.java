package com.example.superflashlight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends WarningLight{
    public void onClick_ToFlashlight(View view){
        hideAllUI();
        mUIFlashlight.setVisibility(View.VISIBLE);
        mLastUIType = UIType.UI_TYPE_FLASHLIGHT;
        mCurrentUIType = UIType.UI_TYPE_FLASHLIGHT;
    }
    public void onClick_ToWarningLight(View view)
    {
        hideAllUI();
        mUIWarningLight.setVisibility(View.VISIBLE);
        mLastUIType = UIType.UI_TYPE_WARNINGLIGHT;
        mCurrentUIType = UIType.UI_TYPE_WARNINGLIGHT;
        screenBrightness(1f);
        new WarningLightThread().start();
    }
    public void onClick_Controller(View view){
        hideAllUI();
        if (mCurrentUIType != UIType.UI_TYPE_MAIN){
            mUIMain.setVisibility(View.VISIBLE);
            mCurrentUIType = UIType.UI_TYPE_MAIN;
            mWarningLightFlicker = false;
            screenBrightness(mDefaultBrightness/255f);
        }
        else{
            switch (mLastUIType){
                case UI_TYPE_FLASHLIGHT:
                    mUIFlashlight.setVisibility(View.VISIBLE);
                    mCurrentUIType = UIType.UI_TYPE_FLASHLIGHT;
                    break;
                case UI_TYPE_WARNINGLIGHT:
                    mUIWarningLight.setVisibility(View.VISIBLE);
                    mCurrentUIType = UIType.UI_TYPE_WARNINGLIGHT;
                    screenBrightness(1f);
                    new WarningLightThread().start();
                    break;
                default:
                    break;
            }
        }

    }


}
