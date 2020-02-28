package com.bpj.optimization.optimization.lsn17.onepixel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bpj.optimization.optimization.R;

/**
 * Created by Ray on 2020-2-28.
 */
public class Lsn17StartServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lsn17_start_service_activity);
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }


    public void jump(View v){
        Intent intent = new Intent(this, KeepLiveActivity.class);
        startActivity(intent);
        finish();
    }
}
