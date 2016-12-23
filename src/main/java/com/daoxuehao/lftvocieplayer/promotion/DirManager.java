
package com.daoxuehao.lftvocieplayer.promotion;

import java.io.File;
import java.net.URI;
import java.net.URL;

import android.content.Context;
import android.os.Environment;


public class DirManager {

	
	private static final String LFT_TMP_ROOT="/a_lft_dxh_tmp";
	private static final String PROMOTION="/promotion";
	
	
	public static String createDir(Context context , String dir){
		String path="";
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			path = Environment.getExternalStorageDirectory().getAbsolutePath();
		}else{
			path = context.getFilesDir().getAbsolutePath();
		}
		path+=dir;
		
		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}
		return f.getAbsolutePath();
	}
	
	
	public static String getPromotionImagePath(Context context,String url){
		String dir=createDir(context,LFT_TMP_ROOT+PROMOTION);
		return dir+"/"+getPromotionImageNameByUrl(url);
	}
	public static String getPromotionImageNameByUrl(String url){
		String u = url.toLowerCase();
		String name = new MD5().getMD5ofStr(url);
		String type="";
		if (u.indexOf(".jpg")!=-1) {
			type=".jpg";
		}else if (u.indexOf(".png")!=-1) {
			type=".png";
		}else if (u.indexOf(".gif")!=-1) {
			type=".gif";
		} 
		if (type.length()>0) {
			return name+type;
		}
		return "";
	}
	
	
}
