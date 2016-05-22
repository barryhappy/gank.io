package com.barryzhang.gankio.utils;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.barryzhang.gankio.application.MyApp;

public class D {
	private static String TAG = "test";
	public static boolean DEBUG = true;//TODO 发布时改为false

	public static void e(Object msg) {
		if (DEBUG) {
			Log.e(TAG, msg == null ? "NULL": msg.toString());
		}
	}

	public static void d(Object msg) {
		if (DEBUG && msg!=null){
			Log.d(TAG, msg == null ? "NULL": msg.toString());
		}
	}
	public static void d(String tag,String msg) {
		if (DEBUG && tag != null && msg!=null){
			Log.d(tag, msg);
		}
	}
	public static void e(String tag,String msg) {
		if (DEBUG && tag != null && msg!=null){
			Log.e(tag, msg);
		}
	}

	public static void i(String msg) {
		if (DEBUG)
			Log.i(TAG, msg);
	}


	public static void toastWhileDebug(Context context ,String content){
		if (DEBUG){
			Toast toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}



	public static void toastWhileDebug(String content){
		toastWhileDebug(MyApp.getInstance(),content);
	}




	public static void toast(String content){
		Toast toast = Toast.makeText(MyApp.getInstance(), content, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}


	/***
	 * 打印长数据
	 */
	public static void largeLog(String content) {
		if(!DEBUG){
			return;
		}
		int maxLogSize = 1000;
		if (content == null) {
			Log.v("test", "null");
			return;
		}
		for (int i = 0; i <= content.length() / maxLogSize; i++) {
			int start = i * maxLogSize;
			int end = (i + 1) * maxLogSize;
			end = end > content.length() ? content.length() : end;
			Log.v("test", content.substring(start, end));
		}
	}
}
