package com.bpj.optimization.optimization;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bpj.optimization.optimization.lsn01.LeakageActivity;
import com.bpj.optimization.optimization.lsn02.ListenerActivity;

public class MainActivity extends AppCompatActivity {

    static Class[] TARGETS = {LeakageActivity.class,
            ListenerActivity.class};
    static String[] TARGETS_DESC = {"性能优化01_内存泄漏",
        "性能优化02_监听导致的泄漏"};

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
