package com.bpj.optimization.optimization.lsn17.floatwindow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bpj.optimization.optimization.R;

/**
 * Created by Ray on 2020-2-28.
 */
public class Lsn17_FloatWindowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_window);

        Button startFloatWindow = (Button) findViewById(R.id.start_float_window);
        startFloatWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Lsn17_FloatWindowActivity.this, FloatWindowService.class);
                startService(intent);
                finish();
            }
        });
    }
}
