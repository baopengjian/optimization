package com.bpj.optimization.optimization.lsn08;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bpj.optimization.optimization.R;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ray on 2020-1-21.
 */
public class WakeLockActivity extends AppCompatActivity {

    TextView wakelock_text;
    PowerManager pw;
    PowerManager.WakeLock mWakelock;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wake_lock_activity);

        wakelock_text = findViewById(R.id.wakelock_text);
        pw = (PowerManager) getSystemService(POWER_SERVICE);
        mWakelock = pw.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "mywakelock");

    }

    public void execut(View view){
        wakelock_text.setText("正在下载....");
        for(int i=0;i<10;i++){
            mWakelock.acquire();//唤醒CPU
            wakelock_text.append("连接中……");
            //下载
            if(isNetWorkConnected()) {
                new SimpleDownloadTask().execute();
            }else{
                wakelock_text.append("没有网络连接。");
            }
        }

    }

    private boolean isNetWorkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =  connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null&&networkInfo.isConnected());
    }

    /**
     *  Uses AsyncTask to create a task away from the main UI thread. This task creates a
     *  HTTPUrlConnection, and then downloads the contents of the webpage as an InputStream.
     *  The InputStream is then converted to a String, which is displayed in the UI by the
     *  onPostExecute() method.
     */
    private class SimpleDownloadTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            int len = 50;
            try {
                URL url = new URL("https://www.baidu.com");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.connect();
                int response = conn.getResponseCode();
                Log.d("SimpleDownloadTask", "The response is: " + response);
                InputStream is = conn.getInputStream();

                // Convert the input stream to a string
                Reader reader = new InputStreamReader(is,"UTF-8");
                char[] buffer = new char[len];
                reader.read(buffer);
                return  new String(buffer);
            } catch (Exception e) {
                e.printStackTrace();
                return "Unable to retrieve web page.";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            wakelock_text.append("\n" + s + "\n");
            releaseWakeLock();
        }
    }

    private void releaseWakeLock() {
        if(mWakelock.isHeld()){
            mWakelock.release();
            wakelock_text.append("释放锁！");
        }
    }
}
