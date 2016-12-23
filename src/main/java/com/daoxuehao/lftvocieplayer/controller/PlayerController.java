
package com.daoxuehao.lftvocieplayer.controller;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.SeekBar;

import com.daoxuehao.lftvocieplayer.data.bean.VoiceParams;
import com.daoxuehao.lftvocieplayer.intrfc.ClickControllerListener;
import com.daoxuehao.lftvocieplayer.intrfc.PlayerListener;
import com.daoxuehao.lftvocieplayer.util.LogVocie;
import com.daoxuehao.lftvocieplayer.util.Utils;
import com.daoxuehao.lftvocieplayer.view.DefalutPlayerView;
import com.daoxuehao.lftvocieplayer.widget.Player;


/**
  * @ClassName: PlayerController
  * @Description: TODO
  * @author Yale Ren
  * @date 2015-12-1 下午6:18:01
  * Company Xian BoChuang Soft
  */

public class PlayerController implements PlayerListener,ClickControllerListener,SeekBar.OnSeekBarChangeListener{

	public Player mPlayer;
	private DefalutPlayerView mDefalutPlayerView;
	private VoiceParams mParams=null;
	int progress;    
	public PlayerController(DefalutPlayerView view){
		mPlayer = new Player(this);
		mDefalutPlayerView = view;
		view.setOnClickListener(this);
		view.setOnSeekBarChangeListener(this);
		
	}
	public void setPlayUrl(String url){
		mPlayer.setPlayUrl(url);
	}
	@Override
	public void onBufferingUpdate(MediaPlayer mp, int bufferingProgress) {
		
		mDefalutPlayerView.skbProgress.setSecondaryProgress(bufferingProgress);
		//int currentProgress = mDefalutPlayerView.skbProgress.getMax()
		//		* mPlayer.mediaPlayer.getCurrentPosition() / mPlayer.mediaPlayer.getDuration();
		//Log.e(currentProgress + "% play", bufferingProgress + "% buffer");
	}

	
	public void setVoiceParams(VoiceParams voiceParams){
		mParams = voiceParams;
		if (Utils.getVoiceParamsNum(mParams)>1) {
			mDefalutPlayerView.showSpeedChoice(true);
		}else{
			mDefalutPlayerView.showSpeedChoice(false);
		}
		mDefalutPlayerView.showSpeedButtons(mParams.getFast().length()!=0,
				mParams.getNormal().length()!=0, 
				mParams.getSlow().length()!=0);
		String url="";
		if (mParams.getNormal().length()!=0) {
			url = mParams.getNormal();
			mDefalutPlayerView.focusSpeedBtns(false, true, false);
		}else if (mParams.getSlow().length()!=0) {
			url = mParams.getSlow();
			mDefalutPlayerView.focusSpeedBtns(false, false, true);
		}else  {
			url = mParams.getFast();
			mDefalutPlayerView.focusSpeedBtns(true, false, false);
		}
		mDefalutPlayerView.setTitle(mParams.getTitle());
		mPlayer.setPlayUrl(url);
		mDefalutPlayerView.play();
		mDefalutPlayerView.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void onProgress(int position, int duration) {
		
		try {
			mDefalutPlayerView.tvPlayedTime.setText(Utils.formatPlayerdTime(position));
			mDefalutPlayerView.tvTotalTime.setText(Utils.formatPlayerdTime(duration));
			long pos = mDefalutPlayerView.skbProgress.getMax() * position / duration;
			mDefalutPlayerView.skbProgress.setProgress((int) pos);
			
			
			
			if (duration>1&&position/1000 == duration/1000) {
				playFinish();
			}	
		} catch (Exception e) {
		}
		
	}
	@Override
	public void onPlay() {

		mPlayer.play();
		
	}
	public boolean isOpened(){
		return mParams!=null;
	}
	private boolean isShow(){
		return mDefalutPlayerView.getVisibility() == View.VISIBLE;
	}
	public void show(boolean bShow){
		if (bShow!=isShow()) {
			mDefalutPlayerView.setVisibility(bShow?View.VISIBLE:View.GONE);
		}
		
	}
	@Override
	public boolean onPause() {

		return mPlayer.onPause();
	}
	@Override
	public boolean pause() {

		return mPlayer.pause();
	}
	@Override
	public void onStop() {
		mPlayer.stop();
		
	}
	@Override
	public void onRePlay() {
		mPlayer.replay();
		
	}
	
	
	private void playFinish(){
			mPlayer.stop();
			mDefalutPlayerView.init();
			mDefalutPlayerView.skbProgress.setProgress(mDefalutPlayerView.skbProgress.getMax());
	}
	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		
        this.progress = progress * mPlayer.mediaPlayer.getDuration()  
                / seekBar.getMax(); 
        LogVocie.d("p="+progress);
        if (progress>=100) {
        	playFinish();
		}
	}
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		
		
	}
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		mPlayer.mediaPlayer.seekTo(progress);
	}
	
	public void release(){
		mPlayer.release();
	}
	@Override
	public void onFast() {
		mPlayer.stop();
		mPlayer.setPlayUrl(mParams.getFast());
		mPlayer.play();
		mDefalutPlayerView.focusSpeedBtns(true, false, false);
		
		
	}
	@Override
	public void onNormal() {
		mPlayer.stop();
		mPlayer.setPlayUrl(mParams.getNormal());
		mPlayer.play();
		mDefalutPlayerView.focusSpeedBtns(false, true, false);
		
	}
	@Override
	public void onSlow() {
		mPlayer.stop();
		mPlayer.setPlayUrl(mParams.getSlow());
		mPlayer.play();
		mDefalutPlayerView.focusSpeedBtns(false, false, true);
		
	}
	@Override
	public void onClose() {
		onStop();
		mParams = null;
		if (mDefalutPlayerView!=null) {
			mDefalutPlayerView.setVisibility(View.GONE);
		}
	}
	@Override
	public void onPrepared(boolean bPrepared) {
		
		if (mDefalutPlayerView!=null) {
			mDefalutPlayerView.onPrepared(bPrepared);
		}
	}
	@Override
	public void onCompletion() {
		playFinish();
	}
	@Override
	public void onCompletion(MediaPlayer mp) {
		
		
	}
	@Override
	public void onDownload() {
		//String testUrl = "http://lftresource.daoxuehao.com/tiku/resource/1687/95960130/c3489d9cabd9c02f17bca79a3033ba22.mp3";
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		Uri content_url = Uri.parse(mPlayer.getPlayUrl());
		//Uri content_url = Uri.parse(testUrl);
		
		intent.setData(content_url);
		mDefalutPlayerView.getContext().startActivity(Intent.createChooser(intent, "请选择浏览器下载"));
		
	}

	@Override
	public void onResume() {
		mPlayer.onResume();
	}

}
