package com.bpj.optimization.optimization.lsn02;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Ray on 2020-1-9.
 */
public class MyView extends View {
    public MyView(Context context) {
        super(context);
        init();
    }


    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        ListenerCollector collector = new ListenerCollector();
        collector.setListener(this,myListener);
    }

    public interface MyListener {
        void mylistenerCallback();
    }

    private MyListener myListener = new MyListener() {
        @Override
        public void mylistenerCallback() {
            System.out.print("被调用");
        }
    };
}
