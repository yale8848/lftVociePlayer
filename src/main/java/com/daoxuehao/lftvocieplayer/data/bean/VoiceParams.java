
package com.daoxuehao.lftvocieplayer.data.bean;


public class VoiceParams {

	public String title="";
	public String fast="";
	public String normal="";
	public String slow="";
	public String type="";
	
	
	
	public String getType() {
		return type==null?"":type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title==null?"":title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFast() {
		return fast==null?"":fast;
	}
	public void setFast(String fast) {
		this.fast = fast;
	}
	public String getNormal() {
		return normal==null?"":normal;
	}
	public void setNormal(String normal) {
		this.normal = normal;
	}
	public String getSlow() {
		return slow==null?"":slow;
	}
	public void setSlow(String slow) {
		this.slow = slow;
	}
	
	
	
	
}
