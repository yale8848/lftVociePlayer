
package com.daoxuehao.lftvocieplayer.intrfc;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;


/**
  * @ClassName: PlayerListener
  * @Description: TODO
  * @author Yale Ren
  * @date 2015-12-1 下午6:18:07
  * Company Xian BoChuang Soft
  */

public interface PlayerListener extends OnBufferingUpdateListener, OnCompletionListener{

	public void onProgress(int position,int duration);
	public void onPrepared(boolean bPrepared);
	public void onCompletion();
}
