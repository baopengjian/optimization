package com.bpj.optimization.optimization.lsn05;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bpj.optimization.optimization.R;


/**
 * Created by Ray on 2020-1-15.
 */
public class Lsn05Activity extends AppCompatActivity {

    static Class[] TARGETS = {BusyUIThreadActivity.class,
            DataStructuresActivity.class,
            CachingActivity.class,
            CachingActivity2.class,
            MemoryChurnActivity.class,
            MemoryChurnActivity2.class,
    };
    static String[] TARGETS_DESC = {"BusyUIThread:Display an image.(图片RGB色值变换加载)",
            "DataStructures:Dump popular numbers to lo",
            "Caching(计算斐波那契数列：递归)",
            "Caching2(计算斐波那契数列：缓存优化)",
            "MemoryChurn(排序并打印二位数组)",
            "MemoryChurn2(排序并打印二位数组：优化后)"
    };

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        ListView lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, TARGETS_DESC));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(context, TARGETS[position]));
            }
        });
    }

    public void nonstop(View view) {

    }
}
