
package com.daoxuehao.lftvocieplayer;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.daoxuehao.lftvocieplayer.data.bean.VoiceParams;
import com.daoxuehao.lftvocieplayer.widget.VoicePlayerDialog;


/**
  * @ClassName: VoicePlayerDialogActivity
  * @Description: TODO
  * @author Yale Ren
  * @date 2015-12-2 下午3:54:37
  * Company Xian BoChuang Soft
  */

public class VoicePlayerDialogActivity extends Activity {
	
	
	public static final String KEY_PARAMS = "KEY_VoicePlayerDialogActivity";
	
	private VoicePlayerDialog mDialog;
	private Handler mHandler =  new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		if (intent==null) {
			return;
		}
		String params = intent.getStringExtra(KEY_PARAMS);
		
		final VoiceParams vp = JSON.parseObject(params, VoiceParams.class);
		mDialog = new VoicePlayerDialog(this,R.style.VoicePlayerDialog);
		mDialog.setCanceledOnTouchOutside(false);
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				mDialog.show();
				mDialog.mController.setVoiceParams(vp);
				
				mDialog.setOnDismissListener(new OnDismissListener() {
					
					@Override
					public void onDismiss(DialogInterface dialog) {
						mDialog.mController.release();
						mHandler.postDelayed(new Runnable() {
							
							@Override
							public void run() {
								VoicePlayerDialogActivity.this.finish();		
							}
						}, 300);
						
					}
				}); 
			}
		},300);

	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mDialog.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mDialog.onPause();
	}
	

}
