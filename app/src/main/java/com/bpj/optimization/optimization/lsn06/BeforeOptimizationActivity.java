package com.bpj.optimization.optimization.lsn06;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bpj.optimization.optimization.R;
import com.bpj.optimization.optimization.lsn06.ChatsFragment;

/**
 * Created by Ray on 2020-1-22.
 */
public class BeforeOptimizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.before_optimization_activity);
        boolean optimization = false;
        if (getIntent() != null) {
            optimization = getIntent().getBooleanExtra(ChatsFragment.OPTIMIZATION, false);
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_chatum_latinum_container, ChatsFragment.newInstance(optimization))
                    .commit();
        }
    }
}
