
package com.daoxuehao.lftvocieplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.daoxuehao.lftvocieplayer.R;
import com.daoxuehao.lftvocieplayer.intrfc.ClickControllerListener;


/**
  * @ClassName: DefalutPlayerView
  * @Description: TODO
  * @author Yale Ren
  * @date 2015-12-2 上午9:53:11
  * Company Xian BoChuang Soft
  */

public class DefalutPlayerView extends LinearLayout implements View.OnClickListener {


	private Button btnPause, btnPlayUrl, btnStop,btnReplay;
	private Button btnFast,btnNormal,btnSlow,btnClose;
	private TextView tvDownload;
	public  SeekBar skbProgress;
	public TextView tvTotalTime,tvPlayedTime,tvTitle,tvStatus;
	private ClickControllerListener mClickControllerListener;
	private LinearLayout mLayoutSpeed;

	private static final int TITLE_MAX_LEN = 30 ;



	public DefalutPlayerView(Context context) {
		super(context);
		init(context);
	}
	public DefalutPlayerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context){
		LayoutInflater lInflater = LayoutInflater.from(context);
		View child = lInflater.inflate(R.layout.lft_voice_player_view_defalut, null);

		tvTotalTime = (TextView) child.findViewById(R.id.tv_time_total);
		tvPlayedTime = (TextView) child.findViewById(R.id.tv_time_played);
		tvTitle = (TextView) child.findViewById(R.id.tv_title);
		tvStatus=(TextView) child.findViewById(R.id.tv_media_status);

        btnPlayUrl = (Button) child.findViewById(R.id.btnPlayUrl);
        btnPlayUrl.setOnClickListener(this);

        btnPause = (Button) child.findViewById(R.id.btnPause);
        btnPause.setOnClickListener(this);

        btnStop = (Button) child.findViewById(R.id.btnStop);
        btnStop.setOnClickListener(this);

        btnReplay = (Button) child.findViewById(R.id.btnReplay);
        btnReplay.setOnClickListener(this);

        btnFast = (Button) child.findViewById(R.id.btn_fast);
        btnFast.setOnClickListener(this);
        btnNormal = (Button) child.findViewById(R.id.btn_normal);
        btnNormal.setOnClickListener(this);
        btnSlow = (Button) child.findViewById(R.id.btn_slow);
        btnSlow.setOnClickListener(this);

        btnClose =(Button) child.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(this);

        tvDownload =  (TextView) child.findViewById(R.id.tv_download);
        tvDownload.setOnClickListener(this);

        skbProgress = (SeekBar) child.findViewById(R.id.skbProgress);


        mLayoutSpeed = (LinearLayout) child.findViewById(R.id.ll_speed);

        int w = getContext().getResources().getDisplayMetrics().widthPixels;
        LayoutParams lp = new LayoutParams(w,
        		LayoutParams.WRAP_CONTENT);
        addView(child,lp);

	}

	public void showSpeedChoice(boolean bShow){
		mLayoutSpeed.setVisibility(!bShow ? View.GONE : View.VISIBLE);
	}

	public void showSpeedButtons(boolean fast,boolean normal,boolean slow){
		btnFast.setVisibility(!fast ? View.GONE : View.VISIBLE);
		btnNormal.setVisibility(!normal ? View.GONE : View.VISIBLE);
		btnSlow.setVisibility(!slow ? View.GONE : View.VISIBLE);

	}

	public void setTitle(String title){
		if (title.length()>TITLE_MAX_LEN) {
		//	title = title.substring(0, TITLE_MAX_LEN)+"...";
		}
		tvTitle.setText(title);
	}

	private void focusSpeedBtn(Button btn,boolean focus,int resFoucs,int resNormal,int textNormalColor){
		if (focus) {
			btn.setBackgroundResource(resFoucs);
			btn.setTextColor(getResources().getColor(R.color.bk_player_view));
		}else{
			btn.setBackgroundResource(resNormal);
			btn.setTextColor(textNormalColor);
		}
	}
	public void focusSpeedBtns(boolean fast,boolean normal,boolean slow){
		focusSpeedBtn(btnFast,fast,R.drawable.lft_voice_sel_speed_fast_focus,
				R.drawable.lft_voice_sel_speed_fast_normal,getResources().getColor(R.color.speed_btn_fast));
		focusSpeedBtn(btnNormal,normal,R.drawable.lft_voice_sel_speed_normal_focus,
				R.drawable.lft_voice_sel_speed_normal_normal,getResources().getColor(R.color.speed_btn_normal));
		focusSpeedBtn(btnSlow,slow,R.drawable.lft_voice_sel_speed_slow_focus,
				R.drawable.lft_voice_sel_speed_slow_normal,getResources().getColor(R.color.speed_btn_slow));
	}

	public void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener onListener){
		skbProgress.setOnSeekBarChangeListener(onListener);
	}

	public void setOnClickListener(ClickControllerListener listener){
		mClickControllerListener = listener;
	}
	public void init(){


        btnPause.setVisibility(View.GONE);
        btnPlayUrl.setVisibility(View.VISIBLE);
        btnPause.setBackgroundResource(R.drawable.lft_voice_sel_btn_pause);
	}

	public void play(){
    	mClickControllerListener.onPlay();
        initStauts();
	}

	private void initStauts(){

		tvTotalTime.setText("00:00");
		tvPlayedTime.setText("00:00");
        btnPause.setVisibility(View.VISIBLE);
        btnPause.setBackgroundResource(R.drawable.lft_voice_sel_btn_pause);
        btnPlayUrl.setVisibility(View.GONE);
        skbProgress.setProgress(0);
        skbProgress.setSecondaryProgress(0);
	}

	public void onPrepared(boolean bPrepared){

		tvStatus.setText(bPrepared?"播放中":"缓冲中...");
		if (bPrepared) {
			this.postDelayed(new Runnable() {

				@Override
				public void run() {
					tvStatus.setText("");
				}
			}, 3000);
		}

	}

	@Override
	public void onClick(View arg0) {

        if (arg0 == btnPause) {
        	boolean pause = mClickControllerListener.pause();
            if (pause) {
            	btnPause.setBackgroundResource(R.drawable.lft_voice_sel_btn_play);
			}else{
				btnPause.setBackgroundResource(R.drawable.lft_voice_sel_btn_pause);
			}
        } else if (arg0 == btnPlayUrl) {
        	mClickControllerListener.onPlay();
            btnPause.setVisibility(View.VISIBLE);
            btnPlayUrl.setVisibility(View.GONE);
        } else if (arg0 == btnStop) {
        	mClickControllerListener.onStop();
            btnPause.setVisibility(View.GONE);
            btnPlayUrl.setVisibility(View.VISIBLE);
        } else if (arg0==btnReplay) {
        	mClickControllerListener.onRePlay();
		} else if (arg0 == btnFast) {

			initStauts();
			mClickControllerListener.onFast();
		} else if (arg0 == btnNormal) {

			initStauts();
            mClickControllerListener.onNormal();
		} else if (arg0 == btnSlow) {

			initStauts();
			mClickControllerListener.onSlow();
		}else if (arg0 == btnClose) {
			initStauts();
			mClickControllerListener.onClose();

		}else if(arg0 == tvDownload){
			mClickControllerListener.onDownload();
		}
	}
}
