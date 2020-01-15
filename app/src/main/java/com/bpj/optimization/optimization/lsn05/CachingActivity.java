package com.bpj.optimization.optimization.lsn05;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.bpj.optimization.optimization.R;

/**
 * Created by Ray on 2020-1-15.
 */
public class CachingActivity extends AppCompatActivity {
    public static final String LOG_TAG = "CachingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caching_exercise);
        Button theButtonThatDoesFibonacciStuff = findViewById(R.id.caching_do_fib_stuff);
        theButtonThatDoesFibonacciStuff.setText("计算斐波那契数列：递归");

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

    public int computeFibonacci(int positionInFibSequence) {
        //0 1 1 2 3 5 8
        if(positionInFibSequence <=2){
            return 1;
        }else{
            return computeFibonacci(positionInFibSequence-1)+computeFibonacci(positionInFibSequence-2);
        }
    }
}
