package com.bpj.optimization.optimization.lsn03;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bpj.optimization.optimization.MainActivity;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ray on 2020-1-10.
 */
public class CommonLeakageCaseActivity extends AppCompatActivity {
    int a=10;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TextView(CommonLeakageCaseActivity.this));
   
    //    loadData();
       // schedule();
       // handle();
    }



    //1 Thread 引起:隐式持有MainActivity实例。MainActivity.this.a
    private  void loadData() {
     /*   new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                       int b = a;
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();*/
        // 解决方法：加上static，里面的匿名内部类就变成了静态匿名内部类
    }
    //2 Timer
    private void schedule() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    int b = a;
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 20000);//延迟执行导致泄漏
        // 解决方法：activity onDestroy把timer.cancel掉然后赋空
    }

    //3 mHandler是匿名内部类的实例，会引用外部对象MainActivity.this。如果Handler在Activity退出的时候，它可能还活着，这时候就会一直持有Activity。
//    private Handler mHandler = new Handler(){
////        @Override
////        public void handleMessage(Message msg) {
////            super.handleMessage(msg);
////            switch (msg.what){
////                case 0:
////                    //加载数据
//////                    break;
////            }
////        }
////    };
////    private void handle(){
//////        mHandler.sendMessageAtTime(msg,10000);//atTime
////    }
    //解决方案： 软引用+静态防止泄漏并实现功能

    private static class MyHandler extends Handler{

        //        private MainActivity mainActivity;//直接持有了一个外部类的强引用，会内存泄露
        private WeakReference<CommonLeakageCaseActivity> mainActivity;//设置软引用保存，当内存一发生GC的时候就会回收。

        public MyHandler(CommonLeakageCaseActivity mainActivity) {
            this.mainActivity = new WeakReference<>(mainActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CommonLeakageCaseActivity main =  mainActivity.get();
            if(main==null||main.isFinishing()){
                return;
            }
            switch (msg.what){
                case 0:
                    //加载数据
//                    MainActivity.this.a;//有时候确实会有这样的需求：需要引用外部类的资源。怎么办？
                    int b = main.a;
                    break;

            }
        }

    }
}
