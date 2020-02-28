package com.bpj.optimization.optimization.lsn17.onepixel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
/**
 * Created by Ray on 2020-2-28.
 */
public class KeepLiveActivity extends AppCompatActivity {


    private static final String TAG = "Ray";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "KeepLiveActivity----onCreate!!!");

        Window window = getWindow();
        window.setGravity(Gravity.LEFT|Gravity.TOP);
        LayoutParams params = window.getAttributes();
        params.height = 1;
        params.width = 1;
        params.x = 0;
        params.y = 0;

        window.setAttributes(params);

        KeepLiveActivityManager.getInstance(this).setKeepLiveActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "KeepLiveActivity----onDestroy!!!");
    }
}
