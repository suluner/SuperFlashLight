package com.example.superflashlight;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.TransitionDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import java.security.Policy;

/**
 * Created by Suluner on 2016/8/24.
 */
public class BaseActivity extends Activity {
    protected enum UIType
    {
        UI_TYPE_MAIN,UI_TYPE_FLASHLIGHT,UI_TYPE_WARNINGLIGHT,UI_TYPE_MORSE,UI_TYPE_BLUB,UI_TYPE_COLOR,UI_TYPE_POLICE,UI_TYPE_SETTINGS
    }

    protected int mCurrentWarningLightInterval = 50;
    protected int mCurrentPoliceLightInterval = 100;
    protected ImageView mImageViewFlashlight;
    protected ImageView mImageViewFlashlightController;
    protected ImageView mImageViewWarningLight1;
    protected ImageView mImageViewWarningLight2;
    protected ImageView mImageViewBulb;
    protected EditText mEditTextMorseCode;
    protected SeekBar mSeekBarWarningLight;
    protected SeekBar mSeekBarPoliceLight;
    protected Button mButtonAddShortCut;
    protected Button mButtonRemoveShortCut;


    protected Camera mCamera;
    protected Camera.Parameters mParameters;

    protected FrameLayout mUIFlashlight;
    protected LinearLayout mUIMain;
    protected LinearLayout mUIWarningLight;
    protected LinearLayout mUIMorse;
    protected FrameLayout mUIBulb;
    protected FrameLayout mUIColorLight;
    protected FrameLayout mUIPoliceLight;
    protected LinearLayout mUISettings;

    protected UIType mCurrentUIType = UIType.UI_TYPE_FLASHLIGHT;
    protected UIType mLastUIType = UIType.UI_TYPE_FLASHLIGHT;

    protected int mDefaultBrightness;
    protected int mFinishCount = 0;
    protected SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUIFlashlight = (FrameLayout)findViewById(R.id.framelayout_flashlight);
        mUIMain = (LinearLayout)findViewById(R.id.linearlayout_main);
        mImageViewFlashlight = (ImageView) findViewById(R.id.imageview_flashlight);
        mImageViewFlashlightController = (ImageView) findViewById(R.id.imageview_flashlight_controller);
        mImageViewWarningLight1 = (ImageView)findViewById(R.id.imageview_warning_light1);
        mImageViewWarningLight2 = (ImageView)findViewById(R.id.imageview_warning_light2);
        mSeekBarWarningLight = (SeekBar)findViewById(R.id.seekbar_warning_light);
        mSeekBarPoliceLight = (SeekBar)findViewById(R.id.seekbar_police_light);
        mButtonAddShortCut = (Button)findViewById(R.id.button_add_shortcut);
        mButtonRemoveShortCut = (Button)findViewById(R.id.button_remove_shortcut);
        mUIWarningLight = (LinearLayout)findViewById(R.id.linearlayout_warning_light);
        mUIMorse = (LinearLayout)findViewById(R.id.linearlayout_morse);
        mUIBulb = (FrameLayout)findViewById(R.id.framelayout_bulb);
        mUIColorLight = (FrameLayout)findViewById(R.id.framelayout_colorlight);
        mUIPoliceLight = (FrameLayout)findViewById(R.id.framelayout_police_light);
        mUISettings = (LinearLayout)findViewById(R.id.linearlayout_settings);
        mImageViewBulb = (ImageView)findViewById(R.id.imageview_bulb);
        mEditTextMorseCode = (EditText)findViewById(R.id.edittext_morse_code);
        mSharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        mDefaultBrightness = getScreenBrightness();
        mSeekBarPoliceLight.setProgress(mCurrentPoliceLightInterval-100);
        mSeekBarWarningLight.setProgress(mCurrentWarningLightInterval-50);
        mCurrentWarningLightInterval = mSharedPreferences.getInt("warning_light_interval",50);
        mCurrentPoliceLightInterval = mSharedPreferences.getInt("police_light_interval",100);

    }

    protected void hideAllUI(){
        mUIFlashlight.setVisibility(View.GONE);
        mUIMain.setVisibility(View.GONE);
        mUIWarningLight.setVisibility(View.GONE);
        mUIMorse.setVisibility(View.GONE);
        mUIBulb.setVisibility(View.GONE);
        mUIColorLight.setVisibility(View.GONE);
        mUIPoliceLight.setVisibility(View.GONE);
        mUISettings.setVisibility(View.GONE);
    }
    protected void screenBrightness(float value)
    {
        try
        {
            WindowManager.LayoutParams layout = getWindow().getAttributes();
            layout.screenBrightness = value;
            getWindow().setAttributes(layout);
        }
        catch (Exception e)
        {

        }
    }

    protected int getScreenBrightness()
    {
        int value = 0;
        try
        {
            value = android.provider.Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        }
        catch (Exception e)
        {

        }
        return value;

    }

    @Override
    public void finish() {
        mFinishCount++;
        if (mFinishCount == 1)
        {
            Toast.makeText(this,"再按一次退出",Toast.LENGTH_LONG).show();
        }
        else if (mFinishCount == 2)
        {
            super.finish();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mFinishCount = 0;
        return super.dispatchTouchEvent(ev);
    }
}