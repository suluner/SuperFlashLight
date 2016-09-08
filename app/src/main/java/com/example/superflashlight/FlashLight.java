package com.example.superflashlight;

import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.TransitionDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

/**
 * Created by Suluner on 2016/8/24.
 */
public class FlashLight extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageViewFlashlight.setTag(false);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        LayoutParams laParams = (LayoutParams)mImageViewFlashlightController.getLayoutParams();
        laParams.height = point.y*3/4;
        laParams.width = point.x/3;
        mImageViewFlashlightController.setLayoutParams(laParams);
    }

    public void onClick_Flashlight(View v){
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH))
        {
            Toast.makeText(this,"当前设备没有闪光灯",Toast.LENGTH_LONG).show();
            return;
        }
        if(((boolean)mImageViewFlashlight.getTag())==false)
        {
            openFlashlight();
        }
        else
        {
            closeFlashlight();
        }
    }

    //打开闪光灯
    protected void openFlashlight()
    {
        TransitionDrawable drawable = (TransitionDrawable)mImageViewFlashlight.getDrawable();
        drawable.startTransition(200);
        mImageViewFlashlight.setTag(true);

        try {
            mCamera = Camera.open();
            int textureId = 0;
            mCamera.setPreviewTexture(new SurfaceTexture(textureId));
            mCamera.startPreview();

            mParameters = mCamera.getParameters();
            mParameters.setFlashMode(mParameters.FLASH_MODE_TORCH);
            mCamera.setParameters(mParameters);
        }catch (Exception e){

        }
    }
    protected void closeFlashlight()
    {
        TransitionDrawable drawable = (TransitionDrawable)mImageViewFlashlight.getDrawable();
        if(((boolean)mImageViewFlashlight.getTag()))
        {
            drawable.reverseTransition(200);
            mImageViewFlashlight.setTag(false);

            if(mCamera != null)
            {
                mParameters = mCamera.getParameters();
                mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(mParameters);
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }

        }
    }
    @Override
    public void onPause()
    {
        super.onPause();
        closeFlashlight();
    }

}
