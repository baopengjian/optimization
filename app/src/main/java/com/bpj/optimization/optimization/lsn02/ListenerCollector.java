package com.bpj.optimization.optimization.lsn02;

import android.view.View;

import java.util.WeakHashMap;

/**
 * Created by Ray on 2020-1-9.
 */
public class ListenerCollector {

    static private WeakHashMap<View, MyView.MyListener> slistener = new WeakHashMap<>();

    public void setListener(View view, MyView.MyListener listener) {
        slistener.put(view, listener);
    }

    public static void clearListeners() {
        //移除所有监听
        slistener.clear();
    }
}
