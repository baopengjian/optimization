package com.bpj.optimization.optimization.lsn14;

import android.support.annotation.NonNull;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Ray on 2020-2-19.
 */
public class AsyncTaskTest {


    public static void main(String[] args) {
        int CPU_COUNT = Runtime.getRuntime().availableProcessors();
        int CORE_POOL_SIZE = CPU_COUNT + 1;//5
        int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1; //9
        int KEEP_ALIVE = 1;

        //任务队列（128）
        final BlockingDeque<Runnable> sPoolWorkQueue = new LinkedBlockingDeque<>(128);

        //线程工厂
        ThreadFactory sThreaFactory = new ThreadFactory() {

            private final AtomicInteger mCount = new AtomicInteger(1);

            @Override
            public Thread newThread(@NonNull Runnable r) {
                String name = "Thread #" + mCount.getAndIncrement();
                System.out.println(name);
                return new Thread(r, name);
            }
        };
        //线程池
        Executor THREAD_POOL_EXECUTOR
                = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
                TimeUnit.SECONDS, sPoolWorkQueue, sThreaFactory);
        //执行异步任务
        //如果当前线程池中的数量大于corePoolSize，缓冲队列workQueue已满，
        //并且线程池中的数量等于maximumPoolSize，新提交任务由Handler处理。
        //RejectedExecutionException
        for (int i = 0; i < 200; i++) {
            //相当于new AsyncTask().execute();
            THREAD_POOL_EXECUTOR.execute(new MyTask());
        }

    }


    static class MyTask implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
			/*while(true){
				try {
					System.out.println(Thread.currentThread().getName());
					//Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}*/
        }

    }
}
