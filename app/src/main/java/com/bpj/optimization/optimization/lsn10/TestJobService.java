package com.bpj.optimization.optimization.lsn10;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.LinkedList;

/**
 * Created by Ray on 2020-1-23.
 */

/**
 * Service to handle callbacks from the JobScheduler. Requests scheduled with the JobScheduler
 * ultimately land on this service's "onStartJob" method. Currently all this does is post a message
 * to the app's main activity to change the state of the UI.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class TestJobService extends JobService {

    private static final String TAG = "SyncService";
    private final LinkedList<JobParameters> jobParamsMap = new LinkedList<JobParameters>();
    Lsn10Activity mActivity;

    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service created");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service destroyed");
    }

    /**
     * When the app's Activity is created, it starts this service. This is so that the
     * activity and this service can communicate back and forth. See "setUiCalback()"
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Messenger callback = intent.getParcelableExtra("messenger");
        Message m = Message.obtain();
        m.what = Lsn10Activity.MSG_SERVICE_OBJ;
        m.obj = this;
        try {
            callback.send(m);
        } catch (RemoteException e) {
            Log.e(TAG, "Error passing service object back to activity.");
        }
        return START_NOT_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        // We don't do any real 'work' in this sample app. All we'll
        // do is track which jobs have landed on our service, and
        // update the UI accordingly.
        jobParamsMap.add(params);
        if (mActivity != null) {
            mActivity.onReceivedStartJob(params);
            //执行任务
        }

        Log.i(TAG, "on start job: " + params.getJobId());
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        // Stop tracking these job parameters, as we've 'finished' executing.
        jobParamsMap.remove(params);
        if (mActivity != null) {
            mActivity.onReceivedStopJob();
        }

        Log.i(TAG, "on stop job: " + params.getJobId());
        return true;
    }

    public void setUiCallback(Lsn10Activity activity) {
        mActivity = activity;
    }

    /**
     * Send job to the JobScheduler.
     */
    public void scheduleJob(JobInfo info) {
        Log.d(TAG, "Scheduling job");
        JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.schedule(info);//jobscheduleService
    }

    /**
     * Not currently used, but as an exercise you can hook this
     * up to a button in the UI to finish a job that has landed
     * in onStartJob().
     */
    public boolean callJobFinished() {
        JobParameters params = jobParamsMap.poll();
        if (params == null) {
            return false;
        } else {
            jobFinished(params, false);
            return true;
        }
    }
}
