package com.bpj.optimization.optimization.lsn11;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bpj.optimization.optimization.R;
import com.bpj.optimization.optimization.lsn08.WaitForPowerActivity;

/**
 * Created by Ray on 2020-1-21.
 */
public class Lsn10Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsn_8);
        ViewGroup rootView = findViewById(R.id.main_rootview);

        addButton(WaitForPowerActivity.class, "Battery-heavy is no good", rootView);
    }

    public void addButton(final Class destination, String description, ViewGroup parent) {
        Button button = new Button(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent problemIntent = new Intent(Lsn10Activity.this, destination);
                startActivity(problemIntent);
            }
        });

        button.setText(description);
        parent.addView(button);
    }

}
