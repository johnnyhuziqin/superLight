package com.example.superlight.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class autoHideText extends TextView {

	public autoHideText(Context context, AttributeSet attrs) {
		super(context,attrs);  //必须要用这种构造函数参数，不能只用一个构造函数，否则xml会出错
		// TODO Auto-generated constructor stub
	}

	protected Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			if(msg.what == 0){
				setVisibility(View.GONE);
			}
			else{
				setVisibility(View.VISIBLE);
			}
		}
	};
	
	class autoHideTextThread extends Thread{
		
		public void run(){
			mHandler.sendEmptyMessage(1);
			try{
				sleep(3000);
				mHandler.sendEmptyMessage(0);
			}catch(Exception e){
				
			}
			
		}
	}
	
	public void hide(){
		new autoHideTextThread().start();
	}
}
