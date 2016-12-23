
package com.daoxuehao.lftvocieplayer.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.daoxuehao.lftvocieplayer.data.bean.VoiceParams;


/**
  * @ClassName: Utils
  * @Description: TODO
  * @author Yale Ren
  * @date 2015-12-2 上午11:34:02
  * Company Xian BoChuang Soft
  */
public class Utils {

	public static String formatPlayerdTime(int duration){
		DateFormat formatter = new SimpleDateFormat("mm:ss");
		return formatter.format(new Date(duration));
	}
	
	public static int getVoiceParamsNum(VoiceParams vp){
		
		return (vp.getFast().length()!=0?1:0)+(vp.getNormal().length()!=0?1:0)+(vp.getSlow().length()!=0?1:0);
		
	}
	
	public static void toast(Context context ,String msg){
		
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	public static Bitmap getBitmapFromRes(Context context,int resId){
		return BitmapFactory.decodeResource(context.getResources(), resId);
		
	}
}
