package applaction;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApp extends Application {

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
