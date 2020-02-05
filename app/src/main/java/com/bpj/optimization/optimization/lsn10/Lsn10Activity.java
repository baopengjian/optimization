package com.bpj.optimization.optimization.lsn10;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bpj.optimization.optimization.R;

/**
 * Created by Ray on 2020-1-23.
 */
public class Lsn10Activity extends AppCompatActivity {

    private static final String TAG = "Lsn10Activity";

    public static final int MSG_UNCOLOUR_START = 0;
    public static final int MSG_UNCOLOUR_STOP = 1;
    public static final int MSG_SERVICE_OBJ = 2;


    // UI fields.
    int defaultColor;
    int startJobColor;
    int stopJobColor;

    private TextView mShowStartView;
    private TextView mShowStopView;
    private TextView mParamsTextView;
    private EditText mDelayEditText;
    private EditText mDeadlineEditText;
    private RadioButton mWiFiConnectivityRadioButton;
    private RadioButton mAnyConnectivityRadioButton;
    private CheckBox mRequiresChargingCheckBox;
    private CheckBox mRequiresIdleCheckbox;

    ComponentName mServiceComponent;
    /**
     * Service object to interact scheduled jobs.
     */
    TestJobService mTestService;

    private static int kJobId = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lsn10_activity);


        Resources res = getResources();
        defaultColor = res.getColor(R.color.none_received);
        startJobColor = res.getColor(R.color.start_received);
        stopJobColor = res.getColor(R.color.stop_received);

        // Set up UI.
        mShowStartView = findViewById(R.id.onstart_textview);
        mShowStopView = findViewById(R.id.onstop_textview);
        mParamsTextView = findViewById(R.id.task_params);
        mDelayEditText = findViewById(R.id.delay_time);
        mDeadlineEditText = findViewById(R.id.deadline_time);
        mWiFiConnectivityRadioButton = findViewById(R.id.checkbox_unmetered);
        mAnyConnectivityRadioButton = findViewById(R.id.checkbox_any);
        mRequiresChargingCheckBox = findViewById(R.id.checkbox_charging);
        mRequiresIdleCheckbox = findViewById(R.id.checkbox_idle);
        mServiceComponent = new ComponentName(this, TestJobService.class);
        // Start service and provide it a way to communicate with us.
        Intent startServiceIntent = new Intent(this, TestJobService.class);
        startServiceIntent.putExtra("messenger", new Messenger(mHandler));
        startService(startServiceIntent);
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UNCOLOUR_START:
                    mShowStartView.setBackgroundColor(defaultColor);
                    break;
                case MSG_UNCOLOUR_STOP:
                    mShowStopView.setBackgroundColor(defaultColor);
                    break;
                case MSG_SERVICE_OBJ:
                    mTestService = (TestJobService) msg.obj;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mTestService.setUiCallback(Lsn10Activity.this);
                    }
                    break;
            }
        }
    };

    private boolean ensureTestService() {
        if (mTestService == null) {
            if (mTestService == null) {
                Toast.makeText(Lsn10Activity.this, "Service null, never got callback?",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    /**
     * UI onclick listener to schedule a job. What this job is is defined in
     * TestJobService#scheduleJob().
     */
    @SuppressLint("NewApi")
    public void scheduleJob(View v) {
        if (!ensureTestService()) {
            return;
        }
        JobInfo.Builder builder = new JobInfo.Builder(kJobId++, mServiceComponent);

        String delay = mDelayEditText.getText().toString();
        if (!TextUtils.isEmpty(delay)) {
            builder.setMinimumLatency(Long.valueOf(delay) * 1000);
        }

        String deadline = mDeadlineEditText.getText().toString();
        if (!TextUtils.isEmpty(deadline)) {
            builder.setOverrideDeadline(Long.valueOf(deadline) * 1000);
        }
        boolean requiresUnmetered = mWiFiConnectivityRadioButton.isChecked();
        boolean requiresAnyConnectivity = mAnyConnectivityRadioButton.isChecked();
        if (requiresUnmetered) {
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
        } else if (requiresAnyConnectivity) {
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        }

        builder.setRequiresDeviceIdle(mRequiresIdleCheckbox.isChecked());//设置为空闲状态触发
        builder.setRequiresCharging(mRequiresChargingCheckBox.isChecked());// 手机是否处于充电状态
        mTestService.scheduleJob(builder.build());
    }

    /**
     * cancel All jobs
     *
     * @param v
     */
    @SuppressLint("NewApi")
    public void cancelAllJobs(View v) {
        JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.cancelAll();
    }

    /**
     * UI onclick listener to call jobFinished() in our service.
     */
    @SuppressLint("NewApi")
    public void finishJob(View v) {
        if (!ensureTestService()) {
            return;
        }
        mTestService.callJobFinished();
        mParamsTextView.setText("");
    }

    /**
     * Receives callback from the service when a job has landed
     * on the app. Colours the UI and post a message to
     * uncolour it after a second.
     */
    @SuppressLint("NewApi")
    public void onReceivedStartJob(JobParameters params) {
        KeyboardView keyboardView;
        mShowStartView.setBackgroundColor(startJobColor);
        Message m = Message.obtain(mHandler, MSG_UNCOLOUR_START);
        mHandler.sendMessageDelayed(m, 1000L);// uncolour in 1 second.
        mParamsTextView.setText("Executing: " + params.getJobId() + " " + params.getExtras());
    }

    /**
     * Receives callback from the service when a job that
     * previously landed on the app must stop executing.
     * Colours the UI and post a message to uncolour it after a
     * second.
     */
    public void onReceivedStopJob() {
        mShowStopView.setBackgroundColor(stopJobColor);
        Message m = Message.obtain(mHandler, MSG_UNCOLOUR_START);
        mHandler.sendMessageDelayed(m, 2000L); // uncolour in 1 second.
        mParamsTextView.setText("");
    }
}
