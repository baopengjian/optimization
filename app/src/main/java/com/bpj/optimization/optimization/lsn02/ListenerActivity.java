package com.bpj.optimization.optimization.lsn02;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ray on 2020-1-9.
 */
public class ListenerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView myView = new MyView(this);
        setContentView(myView);
    }

  /*  @Override
    protected void onStop() {
        super.onStop();
        ListenerCollector.clearListeners();
    }*/
}
