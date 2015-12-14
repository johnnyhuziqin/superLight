package com.example.superlight;

import java.util.HashMap;
import java.util.Map;

import android.R.string;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class morse extends warnning {
	private final int MORSE_DOT = 200;
	private final int MORSE_LINE = MORSE_DOT*3;
	private final int MORSE_DOT_LINE = MORSE_DOT;
	private final int MORSE_CHAR_CHAR = MORSE_DOT*3;
	private final int MORSE_WORD_WORD = MORSE_DOT*7;
	
	private String morseCode;
	
	private Map<Character, String> mMorseCodeMap = new HashMap<Character, String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mMorseCodeMap.put('a', ".-");
		mMorseCodeMap.put('b', "-...");
		mMorseCodeMap.put('c', "-.-.");
		mMorseCodeMap.put('d', "-..");
		mMorseCodeMap.put('e', ".");
		mMorseCodeMap.put('f', "..-.");
		mMorseCodeMap.put('g', "--.");
		mMorseCodeMap.put('h', "....");
		mMorseCodeMap.put('i', "..");
		mMorseCodeMap.put('j', ".---");
		mMorseCodeMap.put('k', "-.-");
		mMorseCodeMap.put('l', ".-..");
		mMorseCodeMap.put('m', "--");
		mMorseCodeMap.put('n', "-.");
		mMorseCodeMap.put('o', "---");
		mMorseCodeMap.put('p', ".--.");
		mMorseCodeMap.put('q', "--.-");
		mMorseCodeMap.put('r', ".-.");
		mMorseCodeMap.put('s', "...");
		mMorseCodeMap.put('t', "-");
		mMorseCodeMap.put('u', "..-");
		mMorseCodeMap.put('v', "...-");
		mMorseCodeMap.put('w', ".--");
		mMorseCodeMap.put('x', "-..-");
		mMorseCodeMap.put('y', "-.--");
		mMorseCodeMap.put('z', "--..");

		mMorseCodeMap.put('0', "-----");
		mMorseCodeMap.put('1', ".----");
		mMorseCodeMap.put('2', "..---");
		mMorseCodeMap.put('3', "...--");
		mMorseCodeMap.put('4', "....-");
		mMorseCodeMap.put('5', ".....");
		mMorseCodeMap.put('6', "-....");
		mMorseCodeMap.put('7', "--...");
		mMorseCodeMap.put('8', "---..");
		mMorseCodeMap.put('9', "----.");
	}

	private boolean verifyCode(){
		morseCode = mEditText_morse.getText().toString().toLowerCase();
		
		//先判断是否为空
		if(morseCode.length()==0)
		{
			Toast.makeText(this, "没有内容可发", Toast.LENGTH_LONG).show();
			return false;
		}
		
		for(int i=0;i<morseCode.length();i++){
			char c = morseCode.charAt(i);
			if(((c>='a')&&(c<='z'))||((c>='0')&&(c<='9'))||(c==' ')){
				//return true;
			}
			else
			{
				Toast.makeText(this, "内容包含不可发送到字符", Toast.LENGTH_LONG).show();
				return false;
			}
		}
		return true;
	}
	
	public void	onClick_send_morse(View view)
	{
		//see if hardware support
		if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH))
		{
			if(verifyCode())
			{
				sendSentense(morseCode);
			}
		}
		else
		{
			Toast.makeText(this, "硬件不支持", Toast.LENGTH_LONG).show();
			return;
		}
		
	}
	
	protected void sleepExt(int n)
	{
		try{
			Thread.sleep(n);
		}
		catch(Exception e){
			
		}
		
	}
	
	private void sendDot(){
		openFlashlight();
		sleepExt(MORSE_DOT);
		closeFlashlight();
	}
	
	private void sendLine(){
		openFlashlight();
		sleepExt(MORSE_LINE);
		closeFlashlight();
	}
	
	
	private void sendChar(char c)
	{
		String tem = mMorseCodeMap.get(c);
		if(tem != null){
			for(int i=0;i<tem.length();i++){
				if(tem.charAt(i)=='.'){
					sendDot();
				}
				else if(tem.charAt(i)=='-'){
					sendLine();
				}
				sleepExt(MORSE_DOT_LINE);
			}
		}
	}
	
	private void sendWord(String s)
	{
		for(int i=0;i<s.length();i++)
		{
			sendChar(s.charAt(i));
			sleepExt(MORSE_CHAR_CHAR);
		}
	}
	
	private void sendSentense(String str)
	{
		String[] word = str.split(" +");
		for(int i=0;i<word.length;i++)
		{
			sendWord(word[i]);
			sleepExt(MORSE_WORD_WORD);
		}
		Toast.makeText(this, "发送完成，收工", Toast.LENGTH_LONG).show();
	}
	
	
}
