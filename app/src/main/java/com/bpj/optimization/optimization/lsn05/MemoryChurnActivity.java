package com.bpj.optimization.optimization.lsn05;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.bpj.optimization.optimization.R;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Ray on 2020-1-15.
 */
public class MemoryChurnActivity extends AppCompatActivity {

    public static final String LOG_TAG = "MemoryChurnActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_caching_exercise);

        Button theButtonThatDoesFibonacciStuff = findViewById(R.id.caching_do_fib_stuff);
        theButtonThatDoesFibonacciStuff.setText("排序并打印二位数组");

        theButtonThatDoesFibonacciStuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imPrettySureSortingIsFree();
            }
        });
        WebView webView = findViewById(R.id.webview);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadUrl("file:///android_asset/shiver_me_timbers.gif");
    }

    /**
     * 　排序后打印二维数组，一行行打印
     */
    public void imPrettySureSortingIsFree() {
        int dimension = 300;
        int[][] lotsOfInts = new int[dimension][dimension];

        Random random = new Random();
        for (int i = 0; i < lotsOfInts.length; i++) {
            for (int j = 0; j < lotsOfInts[i].length; j++) {
                lotsOfInts[i][j] = random.nextInt();
            }
        }

        for (int i = 0; i < lotsOfInts.length; i++) {
            String rowAsStr = "";
            //排序
            int[] sorted = getSorted(lotsOfInts[i]);
            //拼接打印
            for (int j = 0; j < lotsOfInts[i].length; j++) {
                rowAsStr += sorted[j];
                if (j < (lotsOfInts[i].length - 1)) {
                    rowAsStr += ",";
                }
            }
            Log.i("ricky", "Row " + i + ": " + rowAsStr);
        }

        //优化以后
        StringBuilder sb = new StringBuilder();
        String rowAsStr = "";
        for (int i = 0; i < lotsOfInts.length; i++) {
            //清除上一行
            sb.delete(0, rowAsStr.length());
            //排序
            int[] sorted = getSorted(lotsOfInts[i]);
            //拼接打印
            for (int j = 0; j < lotsOfInts[i].length; j++) {
                rowAsStr += sorted[j];
                sb.append(sorted[j]);
                if(j < (lotsOfInts[i].length - 1)){
                    rowAsStr += ", ";
                    sb.append(", ");
                }
            }
            rowAsStr = sb.toString();
            Log.i("ricky", "Row " + i + ": " + rowAsStr);
        }
    }

    public int[] getSorted(int[] input) {
        int[] clone = input.clone();
        Arrays.sort(clone);
        return clone;
    }
}
