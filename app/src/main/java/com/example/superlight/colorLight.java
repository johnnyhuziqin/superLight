package com.example.superlight;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import com.example.superlight.colorPickerDialog.OnColorChangedListener;

import com.example.superlight.widget.autoHideText;

public class colorLight extends bulb implements OnColorChangedListener{  //这个实现的接口上怎么回事？
	protected int mCurrentColor = Color.BLUE;
	protected autoHideText mText_color;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mText_color = (autoHideText)findViewById(R.id.aotoHideText_color);
	}
	
	@Override
	public void colorChanged(int color){
		mCurrentColor = color;
		mUIColor.setBackgroundColor(color);
	}
	
	public void onClick_corlorPicker(View view){
		new colorPickerDialog(this, this, mCurrentColor).show();
	}
}
