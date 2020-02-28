package com.bpj.optimization.optimization.lsn17.onepixel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;

/**
 * Created by Ray on 2020-2-28.
 */
public class ScreenListener {

    private Context mContext;
    private ScreenBroadcastReceiver mScreenReceiver;
    private ScreenStateListener mScreenStateListener;


    public ScreenListener(Context context) {
        mContext = context;
        mScreenReceiver = new ScreenBroadcastReceiver();
    }


    public void begin(ScreenStateListener listener) {
        mScreenStateListener = listener;
        registerListener();
        getScreenState();
    }

    /**
     * 获取screen状态
     */
    private void getScreenState() {
        PowerManager manager = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        if (manager.isScreenOn()) {
            if (mScreenStateListener != null) {
                mScreenStateListener.onScreenOn();
            }
        } else {
            if (mScreenStateListener != null) {
                mScreenStateListener.onScreenOff();
            }
        }
    }

    /**
     * 启动screen状态广播接收器
     */
    private void registerListener() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        mContext.registerReceiver(mScreenReceiver, filter);
    }

    /**
     * 停止screen状态监听
     */
    public void unregisterListener() {
        mContext.unregisterReceiver(mScreenReceiver);
    }

    public interface ScreenStateListener {
        // 返回给调用者屏幕状态信息
        public void onScreenOn();

        public void onScreenOff();

        public void onUserPresent();
    }

    private class ScreenBroadcastReceiver extends BroadcastReceiver {

        private String action = null;

        @Override
        public void onReceive(Context context, Intent intent) {
            action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) {
                mScreenStateListener.onScreenOn();
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                mScreenStateListener.onScreenOff();
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                mScreenStateListener.onUserPresent();
            }
        }
    }
}
