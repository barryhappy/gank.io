package com.barryzhang.gankio.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;

import com.barryzhang.gankio.R;


public class LoadingDialog extends Dialog{

	private static LoadingDialog dialog = null;

	public LoadingDialog(Context context) {
		super(context);
	}

	public LoadingDialog(Context context, int theme) {
		super(context, theme);
	}

	public static LoadingDialog createDialog(Context context, String text) {
		dialog = new LoadingDialog(context, R.style.myprogressdialog);
		dialog.setContentView(R.layout.layout_progress);
		dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		dialog.setCancelable(true);
		return dialog;
	}
	
	public static LoadingDialog createDialog(Context context) {
		return createDialog(context,null);
	}
	


}
