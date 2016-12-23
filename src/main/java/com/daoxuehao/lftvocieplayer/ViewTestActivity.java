
package com.daoxuehao.lftvocieplayer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.daoxuehao.lftvocieplayer.controller.PlayerController;
import com.daoxuehao.lftvocieplayer.data.bean.VoiceParams;
import com.daoxuehao.lftvocieplayer.view.DefalutPlayerView;


public class ViewTestActivity extends Activity {
	
	public PlayerController mController;
	DefalutPlayerView mDefalutPlayerView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.lft_voice_viewtestactivity);
		mDefalutPlayerView = (DefalutPlayerView) findViewById(R.id.voice_palayer_view);
		
		
		mController = new PlayerController(mDefalutPlayerView);
		
		VoiceParams vp = new VoiceParams();
		vp.setTitle("的发生发生飞洒发发ffffz反反顶顶顶顶顶顶顶顶顶顶顶顶当时分手分手的的说法萨芬士大夫");
		vp.setFast("http://lftresource.oss-cn-qingdao.aliyuncs.com/tiku%2Fresource%2F1687%2F95960130%2Fc3489d9cabd9c02f17bca79a3033ba22.mp3");
		//vp.setNormal("http://pc.da oxuehao.com/DXHFiles/mp3/k.mp3");
		//vp.setSlow("http://pc.daoxuehao.com/DXHFiles/mp3/ceng.mp3");
		mController.setVoiceParams(vp);
		 
	}
	
	public void onClick(View v){
		int id = v.getId();
		if (id == R.id.btn_test1) {
			
			VoiceParams vp = new VoiceParams();
			vp.setTitle("test1");
			vp.setFast("http://pc.daoxuehao.com/DXHFiles/mp3/ifnoyou.mp3");
			mController.setVoiceParams(vp);
			
		}else if (id == R.id.btn_test2) {
			VoiceParams vp = new VoiceParams();
			vp.setTitle("test2");
			vp.setSlow("http://pc.daoxuehao.com/DXHFiles/mp3/ceng.mp3");
			mController.setVoiceParams(vp);			
		}
	}

}
