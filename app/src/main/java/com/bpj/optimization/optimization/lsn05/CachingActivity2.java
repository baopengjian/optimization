package com.bpj.optimization.optimization.lsn05;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.bpj.optimization.optimization.R;

/**
 * Created by Ray on 2020-1-15.
 */
public class CachingActivity2 extends AppCompatActivity {

    public static final String LOG_TAG = "CachingActivity2";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caching_exercise);

        Button theButtonThatDoesFibonacciStuff = (Button) findViewById(R.id.caching_do_fib_stuff);
        theButtonThatDoesFibonacciStuff.setText("计算斐波那契数列：缓存优化");

        theButtonThatDoesFibonacciStuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, String.valueOf(computeFibonacci(40)));
            }
        });

        WebView webView = findViewById(R.id.webview);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadUrl("file:///android_asset/shiver_me_timbers.gif");
    }

    //优化后的斐波那契数列的非递归算法 caching缓存+批处理思想
    public int computeFibonacci(int positionInFibSequence) {
        int pre = 0;
        int current = 1;
        int newValue;
        for (int i = 1; i < positionInFibSequence; i++) {
            newValue = current + pre;
            pre = current;
            current = newValue;
        }
        return current;

    }
}
