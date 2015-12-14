package com.example.superlight;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class setting extends policeLight implements OnSeekBarChangeListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mSeekbar_police.setOnSeekBarChangeListener(this);
		mSeekbar_warning.setOnSeekBarChangeListener(this);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		switch (seekBar.getId()) {
		case R.id.SeekBar1:
			mCurrentWarningLightInterval = progress + 100;
			break;

		case R.id.seekBar2:
			mCurrentPoliceLightInterval = progress + 100;
			break;
		default:
			break;
		}

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	private boolean isHaveShortCut() {
		Cursor cursor = getContentResolver()
				.query(Uri
						.parse("content://com.android.launcher2.settings/favorites"),
						null,
						"intent like ?",
						new String[] { "%component=com.superlight/.MainActivity%" },
						null);
		
		if(cursor.getCount()>0){
			return true;
		}
		else{
			return false;
		}
	}

	public void onClick_addShortCut(View view) {
		try{
		if (!isHaveShortCut()) {
			Intent installShortcut = new Intent(
					"com.android.launcher.action.INSTALL_SHORTCUT");
			installShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "超级手电筒");
			Parcelable icon = Intent.ShortcutIconResource.fromContext(this,
					R.drawable.icon);

			Intent flashlightIntent = new Intent();
			flashlightIntent.setClassName("com.example.superlight",
					"com.example.superlight.MainActivity");
			flashlightIntent.setAction(Intent.ACTION_MAIN);
			flashlightIntent.addCategory(Intent.CATEGORY_LAUNCHER);
			installShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT,
					flashlightIntent);

			sendBroadcast(installShortcut);
			Toast.makeText(this, "已成功将快捷方式添加到桌面！", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "快捷方式已经添加到桌面上，无法再添加快捷方式!", Toast.LENGTH_LONG)
					.show();
		}
		}
		catch(Exception e){
			
		}
	}

	public void onClick_delShortCut(View view) {
		try{
		if (isHaveShortCut()) {
			Intent uninstallShortcut = new Intent(
					"com.android.launcher.action.UNINSTALL_SHORTCUT");
			uninstallShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "超级手电筒");

			Intent flashlightIntent = new Intent();
			flashlightIntent.setClassName("com.example.superlight",
					"com.example.superlight.MainActivity");

			uninstallShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT,
					flashlightIntent);

			flashlightIntent.setAction(Intent.ACTION_MAIN);
			flashlightIntent.addCategory(Intent.CATEGORY_LAUNCHER);

			sendBroadcast(uninstallShortcut);
		} else {
			Toast.makeText(this, "没有快捷方式，无法删除", Toast.LENGTH_LONG).show();
		}
	
	}
	catch(Exception e){
		
	}
	
}
}
