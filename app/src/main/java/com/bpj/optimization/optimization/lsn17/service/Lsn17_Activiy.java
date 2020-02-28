package com.bpj.optimization.optimization.lsn17.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bpj.optimization.optimization.R;

/**
 * Created by Ray on 2020-2-28.
 */
public class Lsn17_Activiy extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, LocalService.class));
        startService(new Intent(this, RemoteService.class));
        startService(new Intent(this, JobHandleService.class));
    }
}
