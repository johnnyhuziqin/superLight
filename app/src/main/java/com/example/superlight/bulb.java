package com.example.superlight;

import com.example.superlight.widget.autoHideText;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;

public class bulb extends morse {
	protected boolean bulbFlag;
	protected TransitionDrawable mTransition_bulb;
	protected autoHideText mText_bulb;
	
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mTransition_bulb = (TransitionDrawable)mImageView_bulb.getDrawable();
		bulbFlag = true;
		mText_bulb = (autoHideText)findViewById(R.id.aotoHideText_bulb);
	}


	public void onClick_bulb(View view){
		if(bulbFlag){
			mTransition_bulb.startTransition(500);
			bulbFlag = false;
			screenBrightness(1f);
		}else{
			mTransition_bulb.reverseTransition(500);
			bulbFlag = true;
			screenBrightness(0f);
		}
	}
}
