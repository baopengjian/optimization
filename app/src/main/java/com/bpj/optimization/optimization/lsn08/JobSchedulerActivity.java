package com.bpj.optimization.optimization.lsn08;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bpj.optimization.optimization.R;
import com.bpj.optimization.optimization.lsn09.MyJobService;

/**
 * Created by Ray on 2020-1-21.
 */
public class JobSchedulerActivity extends AppCompatActivity {

    TextView wakelock_text;
    PowerManager pw;
    PowerManager.WakeLock mWakelock;
    private ComponentName serviceComponent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wake_lock_activity);

        wakelock_text = findViewById(R.id.wakelock_text);
        pw = (PowerManager) getSystemService(POWER_SERVICE);
        mWakelock = pw.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "mywakelock");
        serviceComponent = new ComponentName(this, MyJobService.class);
    }


    public void execut(View view) {
        wakelock_text.setText("正在下载....");
        //优化
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            for (int i = 0; i < 500; i++) {
                JobInfo jobInfo = new JobInfo.Builder(i, serviceComponent)
                        .setMinimumLatency(5000)//5秒 最小延时、
                        .setOverrideDeadline(60000)//maximum最多执行时间
//                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)//免费的网络---wifi 蓝牙 USB
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)//任意网络---wifi
                        .build();
                jobScheduler.schedule(jobInfo);
            }

        }

    }
}
