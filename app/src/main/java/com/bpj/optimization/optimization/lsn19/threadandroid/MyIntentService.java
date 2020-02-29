package com.bpj.optimization.optimization.lsn19.threadandroid;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MyIntentService extends IntentService {

	//至少要有一个空的构造方法
	public MyIntentService() {
		super("MyIntentService");
	}
	
	public MyIntentService(String name) {
		super(name);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.d("jason", Thread.currentThread().getName() + "_onStart");
	}
	
	
	//UI线程发送Intent，会在子线程中执行
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d("jason", Thread.currentThread().getName() + "_onHandleIntent");
	}

}
