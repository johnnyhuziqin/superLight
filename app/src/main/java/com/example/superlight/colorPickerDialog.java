package com.example.superlight;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

public class colorPickerDialog extends Dialog {
	private final int COLOR_DIALOG_WIDTH = 500;
	private final int COLOR_DIALOG_HEIGHT = 500;
	private final int CENTER_X = COLOR_DIALOG_WIDTH / 2;
	private final int CENTER_Y = COLOR_DIALOG_HEIGHT / 2;
	private final int CENTER_RADIUS = 80;
	private final int STROKE_W = 50;

	private OnColorChangedListener mListener;  //�����ýӿ�������һ���û��Ľӿ�
	private int mInitialColor;

	//���ﶨ��ӵ�����ṹ
	public interface OnColorChangedListener {
		boolean anything = true;
		void colorChanged(int color);
	}

	public colorPickerDialog(Context context, OnColorChangedListener listener,
			int initialColor) {
		super(context);
		mListener = listener;  //�ڹ��캯�����mListener��ʼ����Ҳ�����ڴ���������ʱ��������ӿڽ��г�ʼ��
		mInitialColor = initialColor;
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		OnColorChangedListener listener = new OnColorChangedListener() {
			//�����ǵ�����Ի��౻����ʱִ�еĳ�ʼ��
			@Override
			public void colorChanged(int color) {
				// TODO Auto-generated method stub
				mListener.colorChanged(color);
				dismiss();
			}
		};
		
		//�ԶԻ���Ĳ��������趨�����ǻ����Ի���
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(new colorPickerView(getContext(), listener, mInitialColor));
		ColorDrawable colorDrawable = new ColorDrawable();
		colorDrawable.setColor(Color.BLACK);
		getWindow().setBackgroundDrawable(colorDrawable);
		
		getWindow().setAttributes(new LayoutParams(COLOR_DIALOG_WIDTH,COLOR_DIALOG_HEIGHT, 0,0,0));
	}
	

	private class colorPickerView extends View {
		private Paint mPaint;
		private Paint mCenterPaint;
		private final int[] mColors; // ��ɫ������ʼ��ɫ
		private OnColorChangedListener mListener2; //������ڲ����õ���һ������������Ȼ��Ӱ����룬���������ָ�һ�»�����һ��  
		private boolean mTrackingCenter;
		private boolean mHightlightCenter;
		
		private static final float  PI = 3.1415926f;

		public colorPickerView(Context context,
				OnColorChangedListener listener, int color) {
			super(context);
			mColors = new int[] { 0xFFFF0000, 0xFFFF00FF, 0xFF0000FF,
					0xFF00FFFF, 0xFF00FF00, 0xFFFFFF00, 0xFFFF0000 };
			mListener2 = listener;
			Shader s = new SweepGradient(0, 0, mColors, null);
			
			mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mPaint.setShader(s);
			mPaint.setStyle(Paint.Style.STROKE);
			mPaint.setStrokeWidth(STROKE_W);
			
			mCenterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mCenterPaint.setColor(color);
			mCenterPaint.setStrokeWidth(5);
			
			
 
		}

		@Override
		protected void onDraw(Canvas canvas) {
			// TODO Auto-generated method stub
			//super.onDraw(canvas);
			float r = CENTER_X - mPaint.getStrokeWidth()/2-20;
			canvas.translate(CENTER_X, CENTER_Y);
			canvas.drawCircle(0, 0, r, mPaint);
			canvas.drawCircle(0,0,CENTER_RADIUS,mCenterPaint);
			
			if(mTrackingCenter){
				int c = mCenterPaint.getColor();
				mCenterPaint.setStyle(Paint.Style.STROKE);
				if(mHightlightCenter){
					mCenterPaint.setAlpha(0xff);
				}else{
					mCenterPaint.setAlpha(0x00);
				}
				canvas.drawCircle(0, 0, CENTER_RADIUS+mCenterPaint.getStrokeWidth(), mCenterPaint);
				mCenterPaint.setStyle(Paint.Style.FILL);
				mCenterPaint.setColor(c);
			}
			
		}
		
		private int ave(int s, int d, float p)
		{
			return s + Math.round(p * (d - s));
		}
		
		private int calculateColor(int colors[],float unit){

			if(unit <= 0)
				return colors[0];
			if(unit >=1)
				return colors[colors.length - 1];
			float p = unit * (colors.length - 1);
			int i =  (int)p;
			p-=i;
			int c0 = colors[i];
			int c1 = colors[i+1];
			int a = ave(Color.alpha(c0), Color.alpha(c1), p);
			int r = ave(Color.red(c0), Color.red(c1), p);
			int g = ave(Color.green(c0), Color.green(c1), p);
			int b = ave(Color.blue(c0), Color.blue(c1), p);
			return Color.argb(a,r,g,b);
		
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			// TODO Auto-generated method stub
			//return super.onTouchEvent(event);
			float x = event.getX()-CENTER_X;
			float y = event.getY()-CENTER_Y;
			boolean inCenter = Math.sqrt(x*x+y*y)<=CENTER_RADIUS;
			
			switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				mTrackingCenter = inCenter;
				if(inCenter){
					mHightlightCenter = inCenter;
					invalidate();
				}
				break;
			case MotionEvent.ACTION_MOVE:
				float angle = (float)Math.atan2(y, x);
				float unit = angle/(2*PI);            //�ѻ��ȹ�һ��
				if(unit < 0)
					unit += 1;
				mCenterPaint.setColor(calculateColor(mColors,unit));  //�����ɫҪ�úõ���һ��
				invalidate();
				break;
			case MotionEvent.ACTION_UP:
				if(mTrackingCenter){
					if(inCenter){
						mListener2.colorChanged(mCenterPaint.getColor());
					}
					mTrackingCenter = false;
					invalidate(); //�ػ�
				}
				break;
			default:
				break;
			}
			return true;
		}
		
		

	}

	
}
