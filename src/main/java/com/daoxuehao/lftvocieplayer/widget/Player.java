
package com.daoxuehao.lftvocieplayer.widget;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;

import com.daoxuehao.lftvocieplayer.intrfc.PlayerListener;
import com.daoxuehao.lftvocieplayer.util.LogVocie;

import java.util.Timer;
import java.util.TimerTask;

public class Player implements MediaPlayer.OnCompletionListener{
	public MediaPlayer mediaPlayer;
	private Timer mTimer = null;
	private String videoUrl;
	private boolean pause;
	private int playPosition;
	
	private PlayerListener mListener;
	
	private static final int TIME_GAP = 300;

	public Player(PlayerListener playerListener) {
		try {
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnBufferingUpdateListener(playerListener);
			
			mListener = playerListener;
		} catch (Exception e) {
			LogVocie.d(e.toString());
		}

		initTimer();
	}
	
	public void setPlayUrl(String url){
		videoUrl = url;
	}

	public String getPlayUrl(){
		return videoUrl ;
	}
	Handler handleProgress = new Handler() {
		public void handleMessage(Message msg) {
			if (mediaPlayer != null) {
				int position = mediaPlayer.getCurrentPosition();
				int duration = mediaPlayer.getDuration();
				
				
				if (duration > 0) {
					mListener.onProgress(position, duration);
				}				
			}
			
		};
	};


	public boolean onPause() {
		if (mediaPlayer!=null&&mediaPlayer.isPlaying()) {
			playPosition = mediaPlayer.getCurrentPosition();
			mediaPlayer.stop();
		}
		return true;
	}


	public void onResume() {
		if (mediaPlayer!=null){
			if (playPosition > 0&&!pause) {
				playNet(playPosition);
				playPosition = 0;
			}			
		}

	}


	public void play() {
		playNet(0);
	}


	public void replay() {
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.seekTo(0);// 从开始位置开始播放音乐
		} else {
			playNet(0);
		}
	}


	public boolean pause() {
		
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
			pause = true;
		} else {
			if (pause) {
				mediaPlayer.start();
				pause = false;
			}
		}
		return pause;
	}

	private void initTimer(){
		if (mTimer == null) {
			mTimer = new Timer();
			TimerTask task = new TimerTask() {
					@Override
					public void run() {
						if (mediaPlayer == null)
							return;
						if (mediaPlayer.isPlaying() /*&& skbProgress.isPressed() == false*/) {
							handleProgress.sendEmptyMessage(0);
						}
						
					}
				};
			mTimer.schedule(task, 0, TIME_GAP);
		}
		
	}
	
	public void release(){
		if (mediaPlayer != null ) {
			this.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}		
	}
	/**
	 * 停止
	 */
	public void stop() {
		
		pause = false; 
		playPosition = 0;
		if (mTimer!=null) {
			mTimer.cancel();
			mTimer = null;
		}
		
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
			
		}
	
	}


	private void playNet(int playPosition) {
		try {
			
			initTimer();
			
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.stop();
			}
			
			mediaPlayer.reset();// 把各项参数恢复到初始状态
			/**
			 * 通过MediaPlayer.setDataSource()
			 * 的方法,将URL或文件路径以字符串的方式传入.使用setDataSource ()方法时,要注意以下三点:
			 * 1.构建完成的MediaPlayer 必须实现Null 对像的检查.
			 * 2.必须实现接收IllegalArgumentException 与IOException
			 * 等异常,在很多情况下,你所用的文件当下并不存在. 3.若使用URL 来播放在线媒体文件,该文件应该要能支持pragressive
			 * 下载.
			 */
			mediaPlayer.setDataSource(videoUrl);
			mediaPlayer.prepareAsync();// 进行缓冲
			mediaPlayer.setOnPreparedListener(new MyPreparedListener(
					playPosition));
			
			//mediaPlayer.setOnCompletionListener(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final class MyPreparedListener implements
			android.media.MediaPlayer.OnPreparedListener {
		private int playPosition;

		public MyPreparedListener(int playPosition) {
			this.playPosition = playPosition;
			mListener.onPrepared(false);
		}

		@Override
		public void onPrepared(MediaPlayer mp) {
			mediaPlayer.start();
			if (playPosition > 0) {
				mediaPlayer.seekTo(playPosition);
			}
			mListener.onPrepared(true);
		}
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		mListener.onCompletion();
		
	}

}
