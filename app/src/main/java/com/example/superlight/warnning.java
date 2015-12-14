package com.example.superlight;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class warnning extends flashLight {
	protected boolean mWarnningFlicker;
	protected boolean mWarnningState;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mWarnningFlicker = true;
		
	}

	class warnningThread extends Thread {
		public void run() {
			mWarnningFlicker = true;
			while (mWarnningFlicker) {
				try {
					Thread.sleep(mCurrentWarningLightInterval);
					warnningHandler.sendEmptyMessage(0);
				} catch (Exception e) {

				}
			}
		}
	}

	private Handler warnningHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if(mWarnningState)
			{
				mImageView_warnning_on.setImageResource(R.drawable.warning_light_off);
				mImageView_warnning_off.setImageResource(R.drawable.warning_light_on);
				mWarnningState = false;
			}
			else
			{
				mImageView_warnning_on.setImageResource(R.drawable.warning_light_on);
				mImageView_warnning_off.setImageResource(R.drawable.warning_light_off);
				mWarnningState = true;
			}
		}

	};

}
