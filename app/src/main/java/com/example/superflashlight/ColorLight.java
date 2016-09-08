package com.example.superflashlight;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.superflashlight.widget.HideTextView;

/**
 * Created by Suluner on 2016/9/2.
 */
public class ColorLight extends Bulb implements ColorPickerDialog.OnColorChangedListener {
    protected int mCurrentColorLight = Color.RED;
    protected HideTextView mHideTextViewColorLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHideTextViewColorLight = (HideTextView)findViewById(R.id.textview_hide_colorlight);
    }

    @Override
    public void colorChanged(int color) {
        mUIColorLight.setBackgroundColor(color);
        mCurrentColorLight = color;
    }

    public void onClick_ShowColorPicker(View view)
    {
        new ColorPickerDialog(this, this, Color.RED).show();
    }
}
