package com.bpj.optimization.optimization.lsn06;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bpj.optimization.optimization.R;

/**
 * Created by Ray on 2020-1-22.
 */
public class CardsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean optimization = false;
        if (getIntent() != null) {
            optimization = getIntent().getBooleanExtra(ChatsFragment.OPTIMIZATION, false);
        }

        if (optimization) {
            getWindow().setBackgroundDrawable(null);
            setContentView(R.layout.activity_droid_cards_opt);
        } else {
            setContentView(R.layout.activity_droid_cards);
        }

    }
}
