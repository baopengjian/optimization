package com.bpj.optimization.optimization;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bpj.optimization.optimization.lsn01.LeakageActivity;
import com.bpj.optimization.optimization.lsn02.ListenerActivity;
import com.bpj.optimization.optimization.lsn03.CommonLeakageCaseActivity;
import com.bpj.optimization.optimization.lsn05.Lsn05Activity;
import com.bpj.optimization.optimization.lsn06.LayoutOptimizationActivity;
import com.bpj.optimization.optimization.lsn08.Lsn08Activity;
import com.bpj.optimization.optimization.lsn09.WakeLockActivity;
import com.bpj.optimization.optimization.lsn10.JobSchedulerActivity;
import com.bpj.optimization.optimization.lsn10.JobSchedulerSettingActivity;
import com.bpj.optimization.optimization.lsn11.BitmapActivity;
import com.bpj.optimization.optimization.lsn11.CacheActivity;
import com.bpj.optimization.optimization.lsn12.Lsn12Activity;
import com.bpj.optimization.optimization.lsn13.Lsn13Activity;
import com.bpj.optimization.optimization.lsn14.Lsn14Activity;
import com.bpj.optimization.optimization.lsn16.Lsn16Activity;
import com.bpj.optimization.optimization.lsn17.floatwindow.Lsn17_FloatWindowActivity;
import com.bpj.optimization.optimization.lsn17.onepixel.Lsn17StartServiceActivity;
import com.bpj.optimization.optimization.lsn17.service.Lsn17_Activiy;
import com.bpj.optimization.optimization.lsn18.start.Lsn18Activity01;
import com.bpj.optimization.optimization.lsn19.loader.Lsn19LoaderActivity;
import com.bpj.optimization.optimization.lsn19.threadandroid.HandlerThreadActivity1;

public class MainActivity extends AppCompatActivity {

    static Class[] TARGETS = {LeakageActivity.class,
            ListenerActivity.class,
            CommonLeakageCaseActivity.class,
            Lsn05Activity.class,
            LayoutOptimizationActivity.class,
            Lsn08Activity.class,
            WakeLockActivity.class,
            JobSchedulerActivity.class,
            JobSchedulerSettingActivity.class,
            CacheActivity.class,
            BitmapActivity.class,
            Lsn12Activity.class,
            Lsn13Activity.class,
            Lsn14Activity.class,
            Lsn16Activity.class,
            Lsn17StartServiceActivity.class,
            Lsn17_Activiy.class,
            Lsn17_FloatWindowActivity.class,
            Lsn18Activity01.class,
            HandlerThreadActivity1.class,
            Lsn19LoaderActivity.class
    };
    static String[] TARGETS_DESC = {"性能优化01_内存泄漏",
            "性能优化02_监听导致的泄漏",
            "性能优化03_常见内存泄露分析",
            "性能优化05_UI卡顿分析之内存抖动和计算性能优化",
            "性能优化06_渲染优化：布局优化",
            "性能优化08_电量优化：监控电量状态",
            "性能优化09_WakeLock在下载任务中的简单使用",
            "性能优化10_JobScheduler的简单使用",
            "性能优化10_JobScheduler的任务监听",
            "性能优化11_Http请求缓存",
            "性能优化11_Bitmap压缩",
            "性能优化12_Android高清显示图片：哈夫曼算法(ExternalCacheDir查看对比效果)",
            "性能优化13_数据传输效率优化",
            "性能优化14_多线程优化",
            "性能优化16_热修复",
            "性能优化17_1像素保活",
            "性能优化17_双进程守护+JobService保活",
            "性能优化17_悬浮框",
            "性能优化18_启动优化",
            "性能优化19_HandlerThread",
            "性能优化19_Loader"
    };

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
