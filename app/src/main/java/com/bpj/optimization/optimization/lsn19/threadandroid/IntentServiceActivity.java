package com.bpj.optimization.optimization.lsn19.threadandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bpj.optimization.optimization.R;

public class IntentServiceActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intent_service);
	}
	
	//发送意图给IntentService，启动子线程执行任务
	public void mClick(View btn){
		Intent intent = new Intent(this,MyIntentService.class);
		startService(intent);
	}
	
	
}
