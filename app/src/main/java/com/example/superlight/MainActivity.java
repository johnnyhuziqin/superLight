package com.example.superlight;

import android.view.View;

public class MainActivity extends setting {

	public void onClick_toFlashLight(View view) {
		hideAllUI();
		mUIFlashlight.setVisibility(View.VISIBLE);
		mLastUIType = UIType.UI_TYPE_FLASHLIGHT;
		mCurrentUIType = UIType.UI_TYPE_FLASHLIGHT;
	}

	public void onClick_toWarnnig(View view) {
		hideAllUI();
		mUIWarnnig.setVisibility(View.VISIBLE);
		mLastUIType = UIType.UI_TYPE_WARNINGLIGHT;
		mCurrentUIType = UIType.UI_TYPE_WARNINGLIGHT;

		screenBrightness(1f);
		new warnningThread().start();
	}

	public void onClick_toMorse(View view) {
		hideAllUI();
		mUIMorse.setVisibility(View.VISIBLE);
		mLastUIType = UIType.UI_TYPE_MORSE;
		mCurrentUIType = UIType.UI_TYPE_MORSE;
	}

	public void onClick_toBulb(View view) {
		hideAllUI();
		mUIBulb.setVisibility(View.VISIBLE);
		mLastUIType = UIType.UI_TYPE_BLUB;
		mCurrentUIType = UIType.UI_TYPE_BLUB;
		mText_bulb.hide();
	}

	public void onClick_toColor(View view) {
		hideAllUI();
		mUIColor.setVisibility(View.VISIBLE);
		mLastUIType = UIType.UI_TYPE_COLOR;
		mCurrentUIType = UIType.UI_TYPE_COLOR;
		mText_color.hide();
	}

	public void onClick_toPolice(View view) {
		hideAllUI();
		mUIPolice.setVisibility(View.VISIBLE);
		mLastUIType = UIType.UI_TYPE_POLICE;
		mCurrentUIType = UIType.UI_TYPE_POLICE;
		mAutoHideText_police.hide();
		new policeLightThread().start();
	}

	public void onClick_toSetting(View view) {
		hideAllUI();
		mUISetting.setVisibility(View.VISIBLE);
		mLastUIType = UIType.UI_TYPE_SETTINGS;
		mCurrentUIType = UIType.UI_TYPE_SETTINGS;
	}

	public void onClick_Controller(View view) {
		hideAllUI();

		if (mCurrentUIType != UIType.UI_TYPE_MAIN) {
			mUIMain.setVisibility(view.VISIBLE);
			mCurrentUIType = UIType.UI_TYPE_MAIN;
			mWarnningFlicker = false;
			mPoliceLightState = false;
			screenBrightness(0.5f);
			mMemory.edit()
					.putInt("memory_warning_interval",
							mCurrentWarningLightInterval)
					.putInt("memory_police_interval",
							mCurrentPoliceLightInterval).commit();

		} else {
			switch (mLastUIType) {
			case UI_TYPE_FLASHLIGHT:
				mUIFlashlight.setVisibility(View.VISIBLE);
				mCurrentUIType = UIType.UI_TYPE_FLASHLIGHT;
				break;

			case UI_TYPE_WARNINGLIGHT:
				mUIWarnnig.setVisibility(View.VISIBLE);
				mCurrentUIType = UIType.UI_TYPE_WARNINGLIGHT;
				screenBrightness(1f);
				new warnningThread().start();
				break;

			case UI_TYPE_MORSE:
				mUIMorse.setVisibility(View.VISIBLE);
				mCurrentUIType = UIType.UI_TYPE_MORSE;
				break;

			case UI_TYPE_BLUB:
				mUIBulb.setVisibility(View.VISIBLE);
				mCurrentUIType = UIType.UI_TYPE_BLUB;
				break;

			case UI_TYPE_COLOR:
				mUIColor.setVisibility(View.VISIBLE);
				mCurrentUIType = UIType.UI_TYPE_COLOR;
				break;

			case UI_TYPE_POLICE:
				mUIPolice.setVisibility(View.VISIBLE);
				mCurrentUIType = UIType.UI_TYPE_POLICE;
				break;
			case UI_TYPE_SETTINGS:
				mUISetting.setVisibility(View.VISIBLE);
				mCurrentUIType = UIType.UI_TYPE_SETTINGS;
				break;

			default:
				break;
			}
		}

	}

}
