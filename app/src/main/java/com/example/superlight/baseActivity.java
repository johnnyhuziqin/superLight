package com.example.superlight;

import com.example.superlight.widget.autoHideText;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
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

public class baseActivity extends Activity {
	protected enum UIType {
		UI_TYPE_MAIN, UI_TYPE_FLASHLIGHT, UI_TYPE_WARNINGLIGHT, UI_TYPE_MORSE, UI_TYPE_BLUB, UI_TYPE_COLOR, UI_TYPE_POLICE, UI_TYPE_SETTINGS
	}
	
	protected int mCurrentWarningLightInterval = 500;
	protected int mCurrentPoliceLightInterval = 100;
	
	protected UIType mCurrentUIType = UIType.UI_TYPE_FLASHLIGHT;
	protected UIType mLastUIType = UIType.UI_TYPE_FLASHLIGHT;

	protected ImageView mImageView_flashLight;
	protected ImageView mImageView_flashLightController;
	protected ImageView mImageView_warnning_on;
	protected ImageView mImageView_warnning_off;
	protected ImageView mImageView_bulb;	
	
	protected EditText mEditText_morse;
	protected Button mButton_send_morse;
	protected autoHideText mAutoHideText_police;
	protected SeekBar mSeekbar_warning;
	protected SeekBar mSeekbar_police;
	protected Button mButton_addShortCut;
	protected Button mButton_delShortCut;
	protected SharedPreferences mMemory;
	
	protected Camera mCamera;
	protected Parameters mParameters;
	
	protected int mFinishCount = 0;

	protected FrameLayout mUIFlashlight;
	protected LinearLayout mUIMain;
	protected LinearLayout mUIWarnnig;
	protected LinearLayout mUIMorse;
	protected FrameLayout mUIBulb;
	protected FrameLayout mUIColor;
	protected FrameLayout mUIPolice;
	protected LinearLayout mUISetting;
	
	protected int mDefaultScreenBrightness;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mImageView_flashLight = (ImageView) findViewById(R.id.imageview_flashlight);
		mImageView_flashLightController = (ImageView) findViewById(R.id.imageview_flashlight_controller);
		mImageView_warnning_on = (ImageView)findViewById(R.id.ImageView_warning_on);
		mImageView_warnning_off = (ImageView)findViewById(R.id.ImageView_warning_off);
		mEditText_morse = (EditText)findViewById(R.id.editText_morse);
		mButton_send_morse = (Button)findViewById(R.id.button_send_morse);
		mImageView_bulb = (ImageView)findViewById(R.id.imageview_bulb);
		mAutoHideText_police = (autoHideText)findViewById(R.id.aotoHideText_policelight);
		mSeekbar_warning = (SeekBar)findViewById(R.id.SeekBar1);
		mSeekbar_police = (SeekBar)findViewById(R.id.seekBar2);
		mButton_addShortCut = (Button)findViewById(R.id.buttonAddShortCut);
		mButton_delShortCut = (Button)findViewById(R.id.buttonDelShortCut);
		mMemory = getSharedPreferences("config", Context.MODE_PRIVATE);		
				
		mUIFlashlight = (FrameLayout) findViewById(R.id.framelayout_flashlight);
		mUIMain = (LinearLayout) findViewById(R.id.linearlayout_main);
		mUIWarnnig = (LinearLayout)findViewById(R.id.LinearLayout_warnnig);
		mUIMorse = (LinearLayout)findViewById(R.id.linearlayout_morse);
		mUIBulb = (FrameLayout)findViewById(R.id.framelayout_bulb);
		mUIColor = (FrameLayout)findViewById(R.id.framelayout_color_light);
		mUIPolice = (FrameLayout)findViewById(R.id.framelayout_police_light);
		mUISetting = (LinearLayout)findViewById(R.id.linearlayout_setting);
		
		mDefaultScreenBrightness = getScreenBrightness();
		mCurrentWarningLightInterval = mMemory.getInt("memory_warning_interval", 100);
		mCurrentPoliceLightInterval = mMemory.getInt("memory_police_interval", 100);
		
		mSeekbar_warning.setProgress(mCurrentWarningLightInterval-100);
		mSeekbar_police.setProgress(mCurrentPoliceLightInterval-100);
	}

	protected void hideAllUI() {
		mUIMain.setVisibility(View.GONE);
		mUIFlashlight.setVisibility(View.GONE);
		mUIWarnnig.setVisibility(View.GONE);
		mUIMorse.setVisibility(View.GONE);
		mUIBulb.setVisibility(View.GONE);
		mUIColor.setVisibility(View.GONE);
		mUIPolice.setVisibility(View.GONE);
		mUISetting.setVisibility(View.GONE);
	}
	
	protected void screenBrightness(float value) {
		try {
			WindowManager.LayoutParams layout = getWindow().getAttributes();
			layout.screenBrightness = value;
			getWindow().setAttributes(layout);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	protected int getScreenBrightness() {
		int value = 100;
		try {
			value = android.provider.Settings.System.getInt(
					getContentResolver(),
					android.provider.Settings.System.SCREEN_BRIGHTNESS);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return value;

	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		mFinishCount = 0;
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public void finish() {
		mFinishCount++;
		if (mFinishCount == 1) {
			Toast.makeText(this, "press again to quit", Toast.LENGTH_LONG).show();
		} else if (mFinishCount == 2) {
			super.finish();
		}
	}
}
