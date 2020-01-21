package com.bpj.optimization.optimization.lsn08;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bpj.optimization.optimization.R;
import com.bpj.optimization.optimization.lsn09.JobSchedulerActivity;

/**
 * Created by Ray on 2020-1-21.
 */
public class Lsn08Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsn_8);
        ViewGroup rootView = findViewById(R.id.main_rootview);

        addButton(WaitForPowerActivity.class, "Battery-heavy is no good", rootView);
        addButton(WakeLockActivity.class, "下载时使用WakeLock", rootView);
        addButton(JobSchedulerActivity.class, "JobScheduler代替执行下载任务", rootView);
    }

    public void addButton(final Class destination, String description, ViewGroup parent) {
        Button button = new Button(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent problemIntent = new Intent(Lsn08Activity.this, destination);
                startActivity(problemIntent);
            }
        });

        button.setText(description);
        parent.addView(button);
    }

}
