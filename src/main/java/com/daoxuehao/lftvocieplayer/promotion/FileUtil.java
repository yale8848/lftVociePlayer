
package com.daoxuehao.lftvocieplayer.promotion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
  * @ClassName: FileUtil
  * @Description: TODO
  * @author Yale Ren
  * @date 2015-12-9 上午10:29:21
  * Company Xian BoChuang Soft
  */

public class FileUtil {

    /** 
    * @Title: getFileFromUrl 
    * @Description: TODO
    * @param @param url
    * @param @param path
    * @param @param 相同文件是否覆盖
    * @param @return
    * @return boolean
    * @author Yale Ren 2015-12-9
    * @throws 
    */
    public static boolean getFileFromUrl(String url, String path,boolean overide) {
    	
    	File file = new File(path);
    	if (file.exists()&&!overide) {
			return true;
		}
    	try {
    	  	
            URL remote = new URL(url);  
            HttpURLConnection conn = (HttpURLConnection) remote.openConnection();  
            conn.setConnectTimeout(5000);  
            conn.setRequestMethod("GET");  
            conn.setDoInput(true);  
            if (conn.getResponseCode() == 200) {  
  
                InputStream is = conn.getInputStream();  
                FileOutputStream fos = new FileOutputStream(file);  
                byte[] buffer = new byte[1024];  
                int len = 0;  
                while ((len = is.read(buffer)) != -1) {  
                    fos.write(buffer, 0, len);  
                }  
                is.close();  
                fos.close();  
                return true;
            } 
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return false;
   
    }  
}
