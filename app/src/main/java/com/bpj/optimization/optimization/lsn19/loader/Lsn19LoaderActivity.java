package com.bpj.optimization.optimization.lsn19.loader;


import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;
import android.widget.ListView;

import com.bpj.optimization.optimization.R;

/**
 * 使用加载器加载通话记录
		 * @author Jason
		* QQ: 1476949583
		* @date 2016年12月29日
		* @version 1.0
		*/
public class Lsn19LoaderActivity extends Activity {

	private static final String TAG = "Ray";

	// 查询指定的条目
	private static final String[] CALLLOG_PROJECTION = new String[] { CallLog.Calls._ID, CallLog.Calls.NUMBER,
			CallLog.Calls.CACHED_NAME, CallLog.Calls.TYPE, CallLog.Calls.DATE };
	private ListView mListView;
	private MyLoaderCallback mLoaderCallback = new MyLoaderCallback();
	private MyCursorAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loader);

		mListView = (ListView) findViewById(R.id.lv_list);

		mAdapter = new MyCursorAdapter(Lsn19LoaderActivity.this, null);
		mListView.setAdapter(mAdapter);

		//执行Loader的回调
		getLoaderManager().initLoader(0, null, mLoaderCallback);
	}


	private class MyLoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {

		//创建Loader
		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			//加载的过程在子线程中进行
			CursorLoader loader = new CursorLoader(Lsn19LoaderActivity.this, CallLog.Calls.CONTENT_URI, CALLLOG_PROJECTION,
					null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
			Log.d(TAG, "onCreateLoader");
			return loader;
		}

		//Loader检测底层数据，当检测到改变时，自动执行新的载入获取最新数据
		//Activity/Fragment所需要做的就是初始化Loader，并且对任何反馈回来的数据进行响应。
		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
			if (data == null)
				return;
			mAdapter.swapCursor(data);
			Log.d(TAG, "onLoadFinished data count = " + data.getCount());
		}

		//OnDestroy，自动停止load
		@Override
		public void onLoaderReset(Loader<Cursor> loader) {
			Log.d(TAG, "onLoaderReset");
			mAdapter.swapCursor(null);
		}
	}

}
