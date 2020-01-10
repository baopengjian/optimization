package com.bpj.optimization.optimization.lsn01;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bpj.optimization.optimization.R;

/**
 * Created by Ray on 2020-1-7.
 * 单例在横屏时出现的内存泄漏
 */
public class LeakageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // CommonUtil commonUtil = CommonUtil.getInstance(getApplicationContext());
       //导致LeakageActivity泄漏：
         CommonUtil commonUtil = CommonUtil.getInstance(this);
    }
}
