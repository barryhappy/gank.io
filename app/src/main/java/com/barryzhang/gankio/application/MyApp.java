package com.barryzhang.gankio.application;

import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.orm.SugarApp;

public class MyApp extends SugarApp {

	private static Context _instance;

	public static Context getInstance() {
		return _instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		_instance = getApplicationContext();
		Fresco.initialize(this);
		initCrashHandler();

	}

	private void initCrashHandler() {
		AppCrashHandler crashHandler = AppCrashHandler.getInstance();
		crashHandler.init(this);
	}
}
