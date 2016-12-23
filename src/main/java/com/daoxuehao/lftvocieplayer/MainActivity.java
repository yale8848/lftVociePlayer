package com.daoxuehao.lftvocieplayer;

import com.alibaba.fastjson.JSON;
import com.daoxuehao.lftvocieplayer.data.bean.VoiceParams;
import com.daoxuehao.lftvocieplayer.widget.VoicePlayerDialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity {


	private VoicePlayerDialog mDialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		viewPlay();
		//dialogPlay();

	}

	private void viewPlay() {
		Intent intent = new Intent(this,ViewTestActivity.class);
		startActivity(intent);
		this.finish();
		
	}

	private void dialogPlay() {
		VoiceParams vp = new VoiceParams();
		vp.setTitle("的发生发生飞洒发发ffffz反反");
		vp.setFast("http://pc.daoxuehao.com/DXHFiles/mp3/ifnoyou.mp3");
		vp.setNormal("http://pc.da oxuehao.com/DXHFiles/mp3/k.mp3");
		vp.setSlow("http://pc.daoxuehao.com/DXHFiles/mp3/ceng.mp3");
		
		Intent intent = new Intent(this,VoicePlayerDialogActivity.class);
		
		intent.putExtra(VoicePlayerDialogActivity.KEY_PARAMS, JSON.toJSONString(vp));
		
		startActivity(intent);
		this.finish();
		
	}
	
	
	

	
	
	
}
