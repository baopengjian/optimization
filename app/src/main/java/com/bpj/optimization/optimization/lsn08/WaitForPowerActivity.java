package com.bpj.optimization.optimization.lsn08;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bpj.optimization.optimization.R;

/**
 * Created by Ray on 2020-1-21.
 */
public class WaitForPowerActivity extends AppCompatActivity {

    TextView mPowerMsg;
    ImageView mCheyennePic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power);

        mPowerMsg = (TextView) findViewById(R.id.cheyenne_txt);
        mCheyennePic = (ImageView) findViewById(R.id.cheyenne_img);
        Button theButtonThatTakesPhotos = (Button) findViewById(R.id.power_take_photo);
        theButtonThatTakesPhotos.setText(R.string.take_photo_button);

        final Button theButtonThatFiltersThePhoto = (Button) findViewById(R.id.power_apply_filter);
        theButtonThatFiltersThePhoto.setText(R.string.filter_photo_button);

        theButtonThatTakesPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
                // After we take the photo, we should display the filter option.
                theButtonThatFiltersThePhoto.setVisibility(View.VISIBLE);
            }
        });


        theButtonThatFiltersThePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyFilter();
            }
        });
    }

    /**
     * These are placeholder methods for where your app might do something interesting! Try not to
     * confuse them with functional code.
     * <p>
     * In this case, we are showing how your app might want to manipulate a photo a user has
     * uploaded--perhaps by performing facial detection, applying filters, generating thumbnails,
     * or backing up the image. In many instances, these actions might not be immediately necessary,
     * and may even be better done in batch. In this sample, we allow the user to "take" a photo,
     * and then "apply" a simple magenta filter to the photo. For brevity, the photos are already
     * included in the sample.
     */
    private void takePhoto() {
        // Make photo of Cheyenne appear.
        mPowerMsg.setText(R.string.photo_taken);
        mCheyennePic.setImageResource(R.drawable.cheyenne);
    }

    private void applyFilter() {
        //是否在充电
        if (!checkForPower()) {
            mPowerMsg.setText("请充上电，再处理！");
            return;
        }
        mCheyennePic.setImageResource(R.drawable.pink_cheyenne);
        mPowerMsg.setText(R.string.photo_filter);
    }

    private boolean checkForPower() {
        //获取电池的充电状态
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent intent = registerReceiver(null, filter);
        if (intent == null) {
            return false;
        }
        //BatteryManager
        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usb = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean ac = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
        //无线充电---API>=17
        boolean wireless = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wireless = chargePlug == BatteryManager.BATTERY_PLUGGED_WIRELESS;
        }
        return (usb || ac || wireless);
    }
}
