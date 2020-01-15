package com.bpj.optimization.optimization.lsn05;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.bpj.optimization.optimization.R;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by Ray on 2020-1-14.
 */
public class DataStructuresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_structure_activity);

        Button dumpCountriesButton = findViewById(R.id.ds_button_dostuff);
        dumpCountriesButton.setText("Dump popular numbers to log");

        dumpCountriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dumpPopularRandomNumbersByRank();
            }
        });

        WebView webView = findViewById(R.id.webview);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadUrl("file:///android_asset/shiver_me_timbers.gif");
    }

    /**
     * 打印数字在数组中的索引位置
     */
    private void dumpPopularRandomNumbersByRank() {
        //随机数组中包含“受欢迎程度”，对数组进行排序
        Integer[] sortedNumbers = SampleData.coolestRandomNumbers.clone();
        Arrays.sort(sortedNumbers);
        //排序之后获取数值在原数组中的索引位置
        for (int i = 0; i < sortedNumbers.length; i++) {
            Integer currentNumber = sortedNumbers[i];
            for (int j = 0; j < SampleData.coolestRandomNumbers.length; j++) {
                if (currentNumber.compareTo(SampleData.coolestRandomNumbers[j]) == 0) {
                    Log.i("Popularity Dump", currentNumber + ": #" + j);
                }
            }
        }
    }
}
