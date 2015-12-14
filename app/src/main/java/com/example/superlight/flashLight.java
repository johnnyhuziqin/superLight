package com.example.superlight;

import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.TransitionDrawable;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class flashLight extends baseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mImageView_flashLight.setTag(false);
		mUIFlashlight.setVisibility(View.VISIBLE);
	}

	public void onClick_FlashLight(View view) {
		if (!getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA_FLASH)) {
			Toast.makeText(this, "当前设备没有闪光灯", Toast.LENGTH_LONG).show();
			return;
		}
		if (((Boolean) mImageView_flashLight.getTag()) == false) {
			openFlashlight();
   
		} else {
			closeFlashlight();
		}
	}
	
	// 打开闪光灯
		protected void openFlashlight() {
			TransitionDrawable drawable = (TransitionDrawable) mImageView_flashLight
					.getDrawable();
			drawable.startTransition(200);
			mImageView_flashLight.setTag(true);

			try {
				mCamera = Camera.open();
				int textureId = 0;
				mCamera.setPreviewTexture(new SurfaceTexture(textureId));
				mCamera.startPreview();

				mParameters = mCamera.getParameters();

				mParameters.setFlashMode(mParameters.FLASH_MODE_TORCH);
				mCamera.setParameters(mParameters);

			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		// 关闭闪光灯
		protected void closeFlashlight() {
			TransitionDrawable drawable = (TransitionDrawable) mImageView_flashLight
					.getDrawable();
			if (((Boolean) mImageView_flashLight.getTag())) {
				drawable.reverseTransition(200);
				mImageView_flashLight.setTag(false);
				if (mCamera != null) {
					mParameters = mCamera.getParameters();
					mParameters.setFlashMode(Parameters.FLASH_MODE_OFF);
					mCamera.setParameters(mParameters);
					mCamera.stopPreview();
					mCamera.release();
					mCamera = null;

				}
			}
		}
}
