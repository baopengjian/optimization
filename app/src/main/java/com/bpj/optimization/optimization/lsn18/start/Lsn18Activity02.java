package com.bpj.optimization.optimization.lsn18.start;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewStub;

import com.bpj.optimization.optimization.R;

public class Lsn18Activity02 extends FragmentActivity {

	private Handler mHandler = new Handler();
	private ViewStub viewStub;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);

		final SplashFragment splashFragment = new SplashFragment();
		final FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.frame, splashFragment);
		transaction.commit();
		viewStub = (ViewStub) findViewById(R.id.content_viewstub);
		// 1.等整个界面加载完毕后，立马再加载真正的布局进来
		getWindow().getDecorView().post(new Runnable() {

			@Override
			public void run() {
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						viewStub.inflate();
					}
				});
			}
		});
		// 2.延迟一段时间做动画，然后把splashfragment移除
		getWindow().getDecorView().post(new Runnable() {
			@Override
			public void run() {
				mHandler.postDelayed(new DelayRunnable(Lsn18Activity02.this,
						splashFragment), 2500);
			}
		});
		//3.同时异步预加载数据。
		//....
	}

	static class DelayRunnable implements Runnable {
		private WeakReference<Context> contextRef;
		private WeakReference<SplashFragment> fragmentRef;

		public DelayRunnable(Context context, SplashFragment splashFragment) {
			contextRef = new WeakReference<Context>(context);
			fragmentRef = new WeakReference<SplashFragment>(splashFragment);
		}

		@Override
		public void run() {
			FragmentActivity context = (FragmentActivity) contextRef.get();
			if (context != null) {
				SplashFragment splashFragment = fragmentRef.get();
				if (splashFragment == null)
					return;
				final FragmentTransaction transaction = context
						.getSupportFragmentManager().beginTransaction();
				transaction.remove(splashFragment);
				transaction.commit();
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mHandler.removeCallbacksAndMessages(null);
	}

}
