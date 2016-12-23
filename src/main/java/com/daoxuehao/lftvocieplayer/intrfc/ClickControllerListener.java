
package com.daoxuehao.lftvocieplayer.intrfc;


/**
  * @ClassName: ClickControllerListener
  * @Description: TODO
  * @author Yale Ren
  * @date 2015-12-2 上午10:32:11
  * Company Xian BoChuang Soft
  */

public interface ClickControllerListener {

	public void onPlay();
	public boolean onPause();
	public void onStop();
	public void onRePlay();
	
	public void onFast();
	public void onNormal();
	public void onSlow();
	
	public void onClose();
	public void onDownload();
	public void onResume();

	public boolean pause();
	
}
