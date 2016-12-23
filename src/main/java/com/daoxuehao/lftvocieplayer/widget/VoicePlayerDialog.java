package com.daoxuehao.lftvocieplayer.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;

import com.daoxuehao.lftvocieplayer.controller.PlayerController;
import com.daoxuehao.lftvocieplayer.view.DefalutPlayerView;

/**
 * @ClassName: VoicePlayerDialog
 * @Description: TODO
 * @author Yale Ren
 * @date 2015-12-2 上午10:32:20 Company Xian BoChuang Soft
 */

public class VoicePlayerDialog extends Dialog {

	public PlayerController mController;

	public VoicePlayerDialog(Context context) {
		super(context);
	}

	public VoicePlayerDialog(Context context, int theme) {
		super(context, theme);
	}

	public void onResume() {
		if (mController != null) {
			mController.mPlayer.onResume();
		}
	}

	public void onPause() {
		if (mController != null) {
			mController.mPlayer.onPause();
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DefalutPlayerView dfv = new DefalutPlayerView(getContext());
		mController = new PlayerController(dfv);

		Window dlgWindow = getWindow();

		dlgWindow.setGravity(Gravity.BOTTOM);

		setContentView(dfv);

	}

}
