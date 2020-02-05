package com.bpj.optimization.optimization.lsn11;

import android.graphics.BitmapFactory;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.bpj.optimization.optimization.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ray on 2020-1-27.
 */
public class CacheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cache_activity);
    }

    public void openCache(View v) {
        try {
            //Android系统默认的HttpResponseCache(网络请求响应缓存)是关闭的
            //这样开启，开启缓存之后会在cache目录下面创建http的文件夹，HttpResponseCache会缓存所有的返回信息
            File cacheDir = new File(getCacheDir(), "http");//缓存目录
            long maxSize = 10 * 1024 * 1024;//缓存大小，单位byte
            HttpResponseCache.install(cacheDir, maxSize);
            Log.d("CacheActivity", "打开缓存");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void request(View v) {
        Log.d("CacheActivity", "~~~~~~~~~~~~~");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL("").openConnection();
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    int responseCode = conn.getResponseCode();
                    if(responseCode== 200){
                        InputStream is = conn.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        Log.d("CacheActivity", br.readLine());
                    }else{
                        Log.d("CacheActivity",responseCode+"" );
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("CacheActivity","请求异常："+e.getMessage());
                }
            }
        }).start();

    }

    public void request2(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BitmapFactory.decodeStream((InputStream) new URL("").getContent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void deleteCache(View view){
        HttpResponseCache cache = HttpResponseCache.getInstalled();
        if(cache != null){
            try {
                cache.delete();
                Log.d("CacheActivity","清除缓存");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
