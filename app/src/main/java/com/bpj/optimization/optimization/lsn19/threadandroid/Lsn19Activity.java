package com.bpj.optimization.optimization.lsn19.threadandroid;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;

import com.bpj.optimization.optimization.R;

public class Lsn19Activity extends Activity {

	private MyTask task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lsn19);
		
		//使用的默认线程池
		task = new MyTask();
		task.execute();
		//线程池扩容
		//自定义线程池
		/*Executor exec = Executors.newScheduledThreadPool(25);
		for (int i = 0; i < 200; i++) {
			new MyTask().executeOnExecutor(exec);
		}*/
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		task.cancel(true); //取消任务
	}

	class MyTask extends AsyncTask<Void, Integer, Void>{
		int i = 0;

		@Override
		protected Void doInBackground(Void... params) {
			while(!isCancelled()){
				//Log.d("jason", String.valueOf(i++));
				SystemClock.sleep(1000);
			}
			return null;
		}
		
	}
	
}
