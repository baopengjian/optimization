package com.bpj.optimization.optimization.lsn14;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


/**
 * Created by Ray on 2020-2-19.
 */
public class Lsn14Activity extends AppCompatActivity {
    private MyTask task;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //使用的默认线程池
        task = new MyTask();
        task.execute();

        //线程池扩容
        //自定义线程池
      /*  Executor executor = Executors.newScheduledThreadPool(25);
        for (int i = 0; i < 200; i++) {
            new MyTask().executeOnExecutor(executor);
        }*/

    }

    class MyTask extends AsyncTask<Void, Integer, Void> {
        int i = 0;

        @Override
        protected Void doInBackground(Void... voids) {
            while (!isCancelled()) {
                Log.i("Ray", String.valueOf(i++));
                SystemClock.sleep(1000);
            }
            return null;
        }
    }
}
