package com.bpj.optimization.optimization.lsn17.onepixel;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Ray on 2020-2-28.
 */
public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ScreenListener listener = new ScreenListener(this);
        listener.begin(new ScreenListener.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                // 开屏---finish这个一个像素的Activity
                KeepLiveActivityManager.getInstance(MyService.this).finishKeepLiveActivity();
            }

            @Override
            public void onScreenOff() {
                // 锁屏---启动一个像素的Activity
//				startActivity(intent);
                KeepLiveActivityManager.getInstance(MyService.this).startKeepLiveActivity();
            }

            @Override
            public void onUserPresent() {

            }
        });
    }
}
