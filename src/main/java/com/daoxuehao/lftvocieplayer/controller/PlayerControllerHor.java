
package com.daoxuehao.lftvocieplayer.controller;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.daoxuehao.lftvocieplayer.data.bean.VoiceParams;
import com.daoxuehao.lftvocieplayer.intrfc.ClickControllerListener;
import com.daoxuehao.lftvocieplayer.intrfc.PlayerListener;
import com.daoxuehao.lftvocieplayer.util.Utils;
import com.daoxuehao.lftvocieplayer.view.DefalutPlayerViewHor;
import com.daoxuehao.lftvocieplayer.widget.Player;


/**
  * @ClassName: PlayerController
  * @Description: TODO
  * @author Yale Ren
  * @date 2015-12-1 下午6:18:01
  * Company Xian BoChuang Soft
  */

public class PlayerControllerHor implements PlayerListener,ClickControllerListener,SeekBar.OnSeekBarChangeListener{

	public Player mPlayer;
	private DefalutPlayerViewHor mDefalutPlayerView;
	private VoiceParams mParams=null;
	int progress;    
	public PlayerControllerHor(DefalutPlayerViewHor view){
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
				//* mPlayer.mediaPlayer.getCurrentPosition() / mPlayer.mediaPlayer.getDuration();
		//Log.e(currentProgress + "% play", bufferingProgress + "% buffer");
	}
	@Override
	public void onCompletion(MediaPlayer mp) {

	}
	
	public void setVoiceParams(VoiceParams voiceParams){
		mParams = voiceParams;
		if (Utils.getVoiceParamsNum(mParams)>1) {
			mDefalutPlayerView.showSpeedChoice(true);
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
		
		mDefalutPlayerView.tvPlayedTime.setText(Utils.formatPlayerdTime(position));
		mDefalutPlayerView.tvTotalTime.setText(Utils.formatPlayerdTime(duration));
		long pos = mDefalutPlayerView.skbProgress.getMax() * position / duration;
		mDefalutPlayerView.skbProgress.setProgress((int) pos);
		
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
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		
        this.progress = progress * mPlayer.mediaPlayer.getDuration()  
                / seekBar.getMax(); 
        
        if (progress>=100) {
			mPlayer.stop();
			mDefalutPlayerView.init();
		}
        
        Log.e("progerss", ""+progress);
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
		// TODO Auto-generated method stub
	}
	@Override
	public void onCompletion() {
		// TODO Auto-generated method stub
		
		
	}
	@Override
	public void onDownload() {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void onResume() {

	}

	@Override
	public boolean pause() {
		return false;
	}

}
