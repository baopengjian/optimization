package com.bpj.optimization.optimization.lsn19.loader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bpj.optimization.optimization.R;

class MyCursorAdapter extends CursorAdapter {

	static final int DAY = 1440; // 一天的分钟值

	private static final String TAG = "jason";
	private final Context mContext;

	public MyCursorAdapter(Context context, Cursor c) {
		this(context, c, true);
	}

	public MyCursorAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		mContext = context;
	}

	public MyCursorAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		mContext = context;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		return inflater.inflate(R.layout.listview_item, parent, false);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		if (cursor == null)
			return;
		final String id = cursor.getString(0);
		String number = cursor.getString(1);
		String name = cursor.getString(2);
		int type = cursor.getInt(3);
		String date = cursor.getString(4);
		ImageView TypeView = (ImageView) view.findViewById(R.id.bt_icon);
		TextView nameCtrl = (TextView) view.findViewById(R.id.tv_name);
		if (name == null) {
			nameCtrl.setText(mContext.getString(R.string.name_unknown));
		} else {
			nameCtrl.setText(name);
		}
		TextView numberCtrl = (TextView) view.findViewById(R.id.tv_number);
		numberCtrl.setText(number);
		String value = ComputeDate(date);
		TextView dateCtrl = (TextView) view.findViewById(R.id.tv_date);
		dateCtrl.setText(value);
		switch (type) {
			case CallLog.Calls.INCOMING_TYPE:
				TypeView.setImageResource(R.drawable.calllog_incoming);
				break;
			case CallLog.Calls.OUTGOING_TYPE:
				TypeView.setImageResource(R.drawable.calllog_outcoming);
				break;
			case CallLog.Calls.MISSED_TYPE:
				TypeView.setImageResource(R.drawable.calllog_missed);
				break;
			case 4: // CallLog.Calls.VOICEMAIL_TYPE

				break;
			default:
				break;
		}

		ImageButton dailBtn = (ImageButton) view.findViewById(R.id.btn_call);
		dailBtn.setTag(number);
		dailBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Intent.ACTION_CALL_PRIVILEGED 由于Intent中隐藏了，只能用字符串代替
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts(
						"tel", (String) v.getTag(), null));
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(intent);
			}
		});

		ImageButton deleteBtn = (ImageButton) view
				.findViewById(R.id.btn_delete);
		deleteBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 根据ID进行记录删除
				String where = CallLog.Calls._ID + "=?";
				String[] selectionArgs = new String[] { id };
				int result = mContext.getContentResolver().delete(
						CallLog.Calls.CONTENT_URI, where, selectionArgs);
				Log.d(TAG, "11result = " + result);
			}
		});
	}

	private String ComputeDate(String date) {
		long callTime = Long.parseLong(date);
		long newTime = new Date().getTime();
		long duration = (newTime - callTime) / (1000 * 60);
		String value;
		// SimpleDateFormat sfd = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
		// Locale.getDefault());
		// String time = sfd.format(callTime);
		// Log.d(TAG, "[MyCursorAdapter--ComputeDate] time = " + time);
		// 进行判断拨打电话的距离现在的时间，然后进行显示说明
		if (duration < 60) {
			value = duration + "分钟前";
		} else if (duration >= 60 && duration < DAY) {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",
					Locale.getDefault());
			value = sdf.format(new Date(callTime));

			// value = (duration / 60) + "小时前";
		} else if (duration >= DAY
				&& duration < DAY * 2) {
			value = "昨天";
		} else if (duration >= DAY * 2
				&& duration < DAY * 3) {
			value = "前天";
		} else if (duration >= DAY * 7) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd",
					Locale.getDefault());
			value = sdf.format(new Date(callTime));
		} else {
			value = (duration / DAY) + "天前";
		}
		return value;
	}
}