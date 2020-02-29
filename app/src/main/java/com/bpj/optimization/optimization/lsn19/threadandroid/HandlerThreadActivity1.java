package com.bpj.optimization.optimization.lsn19.threadandroid;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.widget.TextView;

import com.bpj.optimization.optimization.R;


public class HandlerThreadActivity1 extends Activity {
	
	
	HandlerThread fetchThread = new HandlerThread("fetching_thread");
	Handler fetchHandler;
	private TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_handler_thread);
		tv = (TextView) findViewById(R.id.tv);
		
		//启动线程
		fetchThread.start();
		//通过fetchHandler发送的消息，会被fetchThread线程创建的轮询器拉取到
		fetchHandler = new Handler(fetchThread.getLooper()){
			@Override
			public void handleMessage(Message msg) {
				//模拟访问网络延迟
				SystemClock.sleep(1000);
				
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						tv.setText("泰铢汇率："+new Random().nextInt(10));
					}
				});
				
				//循环执行
				fetchHandler.sendEmptyMessage(1);
			}
		};
		
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		fetchHandler.sendEmptyMessage(1);
	}
	
	
	@Override
	protected void onStop() {
		super.onStop();
		fetchThread.quit(); //取消
	}
}
