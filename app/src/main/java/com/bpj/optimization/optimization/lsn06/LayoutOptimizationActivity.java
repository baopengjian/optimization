package com.bpj.optimization.optimization.lsn06;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bpj.optimization.optimization.R;

/**
 * Created by Ray on 2020-1-22.
 */
public class LayoutOptimizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_optimization_activity);

        ViewGroup activityContainer = findViewById(R.id.activity_main_container);


        Intent intent = new Intent(this, BeforeOptimizationActivity.class);
        intent.putExtra(ChatsFragment.OPTIMIZATION, false);
        addButton(intent, "布局优化前:布局嵌套层级多", activityContainer);

        Intent intent1 = new Intent(this, BeforeOptimizationActivity.class);
        intent1.putExtra(ChatsFragment.OPTIMIZATION, true);
        addButton(intent1, "布局优化后：RelativeLayout减少布局层级", activityContainer);

        Intent intent2 = new Intent(this, CardsActivity.class);
        intent2.putExtra(ChatsFragment.OPTIMIZATION, false);
        addButton(intent2, "CardsView优化前：重复绘制", activityContainer);

        Intent intent3 = new Intent(this, CardsActivity.class);
        intent3.putExtra(ChatsFragment.OPTIMIZATION, true);
        addButton(intent3, "CardsView：canvas裁剪减少重复绘制", activityContainer);
    }

    private void addButton(final Intent intent, String description, ViewGroup parent) {
        Button button = new Button(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        button.setText(description);
        parent.addView(button);
    }
}
