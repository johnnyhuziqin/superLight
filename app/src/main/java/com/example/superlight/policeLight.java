package com.example.superlight;

import android.R.color;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;

public class policeLight extends colorLight {
	protected boolean mPoliceLightState;
	
	class policeLightThread extends Thread{
		public void run(){
			mPoliceLightState = true;
			while(mPoliceLightState){
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
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			//super.handleMessage(msg);
			int color = msg.what;
			mUIPolice.setBackgroundColor(color);
		}
		
	};
}
