
package com.daoxuehao.lftvocieplayer.promotion;

import java.io.File;

import com.alibaba.fastjson.JSON;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**
  * @ClassName: PromotionManager
  * @Description: TODO
  * @author Yale Ren
  * @date 2015-12-9 下午12:05:27
  * Company Xian BoChuang Soft
  */

public class PromotionManager {

	
	private static final String SP_PROMATEION="promotion";
	private static final String KEY_PROMATEION="key_promotion";
	
	private Context mContext;
	public PromotionManager(Context context){
		mContext = context;
	}
	
	private void saveMessage(String value){
		SharedPreferences sp = mContext.getSharedPreferences(SP_PROMATEION, Activity.MODE_PRIVATE);
		Editor ed  = sp.edit();
		ed.putString(KEY_PROMATEION, value);
		ed.commit();
	}
	private void clearMessage(){
		
		SharedPreferences sp = mContext.getSharedPreferences(SP_PROMATEION, Activity.MODE_PRIVATE);
		Editor ed  = sp.edit();
		ed.clear();
	}
	public void clearData(){
		clearImageFile();
		clearMessage();
	}
	private void clearImageFile(){
		
		String value = getMessage();
		JPMessage jpm = JSON.parseObject(value,JPMessage.class);
		final String imgUrl = jpm.getImgUrl();
		final String imgPath = DirManager.getPromotionImagePath(mContext, imgUrl);
		File f  = new File(imgPath);
		if (f.exists()) {
			f.delete();
		}		
	}
	private String getMessage(){
		SharedPreferences sp = mContext.getSharedPreferences(SP_PROMATEION, Activity.MODE_PRIVATE);
		return sp.getString(KEY_PROMATEION, "");
	}
	
	public JPMessage getPromotionObj(){
		
		JPMessage jpm = JSON.parseObject(getMessage(),JPMessage.class);
		return jpm;
	}

	public void handleMessage(final String value){
	
		JPMessage jpm = JSON.parseObject(value,JPMessage.class);
		final String imgUrl = jpm.getImgUrl();
		final String imgPath = DirManager.getPromotionImagePath(mContext, imgUrl);
		File f  = new File(imgPath);
		
		if (f.exists()) {
			return;
		}
		
		downloadFiles(imgPath,imgUrl,value);

		
	}
	private void downloadFiles(final String imgPath,final String imgUrl,final String value){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				boolean ret = FileUtil.getFileFromUrl(imgUrl,imgPath,true);
				if (ret) {
					saveMessage(value);
				}
			}
		}).start(); 
	}
	
	
}
